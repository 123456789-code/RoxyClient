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

public class ConfigScreen extends Screen {
	private final Screen parent;

	// UI控件
	private Checkbox betterBeaconCheckbox;
	private Checkbox keepMyScrollCheckbox;
	private Checkbox highlightItemEntityCheckbox;

	private Button saveButton;

	public ConfigScreen(Screen parent) {
		super(Component.translatable("roxy_client.config.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		// 配置选项区域
		int startY = 60;
		int spacing = 30;
		int checkboxWidth = 200;
		int checkboxX = (this.width - checkboxWidth) / 2;

		// Better Beacon 选项
		betterBeaconCheckbox = Checkbox.builder(
						Component.translatable("roxy_client.config.better_beacon"),
						this.font
				)
				.pos(checkboxX, startY)
				.selected(Rules.betterBeacon)
				.onValueChange((checkbox, value) -> {
					Rules.betterBeacon = value;
				})
				.build();
		this.addRenderableWidget(betterBeaconCheckbox);

		// Keep My Scroll 选项
		keepMyScrollCheckbox = Checkbox.builder(
						Component.translatable("roxy_client.config.keep_my_scroll"),
						this.font
				)
				.pos(checkboxX, startY + spacing)
				.selected(Rules.keepMyScroll)
				.onValueChange((checkbox, value) -> {
					Rules.keepMyScroll = value;
				})
				.build();
		this.addRenderableWidget(keepMyScrollCheckbox);

		// Highlight Item Entity 选项
		highlightItemEntityCheckbox = Checkbox.builder(
						Component.translatable("roxy_client.config.highlight_item_entity"),
						this.font
				)
				.pos(checkboxX, startY + spacing * 2)
				.selected(Rules.highlightItemEntity)
				.onValueChange((checkbox, value) -> {
					Rules.highlightItemEntity = value;
				})
				.build();
		this.addRenderableWidget(highlightItemEntityCheckbox);

		// 底部按钮区域
		setupBottomButtons();
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
		saveAndClose();
	}
}