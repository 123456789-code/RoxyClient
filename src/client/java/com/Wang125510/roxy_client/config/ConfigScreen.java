package com.Wang125510.roxy_client.config;

import com.Wang125510.roxy_client.RoxyClient;
import com.Wang125510.roxy_client.Rules;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ConfigScreen extends Screen {
	private final Screen parent;
	private final Map<Field, Checkbox> ruleCheckboxes = new LinkedHashMap<>();

	// UI控件
	private Button saveButton;
	private List<AbstractWidget> dynamicWidgets = new ArrayList<>();

	public ConfigScreen(Screen parent) {
		super(Component.translatable("roxy_client.config.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		// 清除之前动态创建的控件
		for (AbstractWidget widget : dynamicWidgets) {
			this.removeWidget(widget);
		}
		dynamicWidgets.clear();
		ruleCheckboxes.clear();

		try {
			List<Field> ruleFields = getRuleFields();
			int startX = 23;
			int spacingX = this.width / 4;
			int startY = 10;
			int spacingY = 30;

			for (int i = 0; i < ruleFields.size(); i++) {
				Field field = ruleFields.get(i);
				field.setAccessible(true);

				// 获取注解信息
				Rule annotation = field.getAnnotation(Rule.class);
				if (annotation == null) continue;

				// 获取当前值
				boolean currentValue = field.getBoolean(null);

				// 创建复选框

				Checkbox checkbox = Checkbox.builder(
								Component.translatable("roxy_client.config." + annotation.name()),
								this.font
						)
						.pos(startX + spacingX * (i % 4), startY + spacingY * (int) (double) (i / 4))
						.selected(currentValue)
						.onValueChange((checkboxWidget, value) -> {
							try {
								// 更新规则值
								field.setBoolean(null, value);
							} catch (IllegalAccessException e) {
								RoxyClient.LOGGER.error("无法设置规则字段 {}: {}", field.getName(), e.getMessage());
							}
						})
						.build();

				// 添加到屏幕和集合中
				this.addRenderableWidget(checkbox);
				dynamicWidgets.add(checkbox);
				ruleCheckboxes.put(field, checkbox);
			}
		} catch (Exception e) {
			RoxyClient.LOGGER.error("创建配置界面时出错: {}", e.getMessage(), e);
		}

		setupBottomButtons();
	}

	/**
	 * 获取所有带有@Rule注解的字段
	 */
	private List<Field> getRuleFields() {
		List<Field> ruleFields = new ArrayList<>();
		try {
			Field[] fields = Rules.class.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Rule.class)) {
					// 只处理boolean类型的规则
					if (field.getType() == boolean.class || field.getType() == Boolean.class) {
						ruleFields.add(field);
					} else {
						RoxyClient.LOGGER.warn("规则 {} 不是boolean类型，跳过", field.getName());
					}
				}
			}
		} catch (Exception e) {
			RoxyClient.LOGGER.error("收集规则字段时出错: {}", e.getMessage(), e);
		}
		return ruleFields;
	}

	private void setupBottomButtons() {
		int bottomY = this.height - 35;
		int buttonWidth = 80;
		int buttonSpacing = 10;
		int totalButtonsWidth = (buttonWidth * 2) + buttonSpacing;
		int startX = (this.width - totalButtonsWidth) / 2;

		// 取消按钮
		Button cancelButton = Button.builder(
						CommonComponents.GUI_CANCEL,
						button -> cancelAndClose()
				)
				.pos(startX, bottomY)
				.size(buttonWidth, 20)
				.build();
		this.addRenderableWidget(cancelButton);

		// 保存按钮
		saveButton = Button.builder(
						Component.translatable("roxy_client.config.save"),
						button -> saveAndClose()
				)
				.pos(startX + buttonWidth + buttonSpacing, bottomY)
				.size(buttonWidth, 20)
				.build();
		this.addRenderableWidget(saveButton);
	}

	private void saveAndClose() {
		ConfigManager.saveConfig();
		this.minecraft.setScreen(parent);
	}

	private void cancelAndClose() {
		// 重新加载配置以恢复修改前的值
		ConfigManager.reloadConfig();
		this.minecraft.setScreen(parent);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		// 使用非模糊的背景来避免崩溃
		this.renderTransparentBackground(guiGraphics);

		// 绘制标题
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

		// 渲染所有控件
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		// 当用户直接关闭界面时，我们选择保存配置
		saveAndClose();
	}
}