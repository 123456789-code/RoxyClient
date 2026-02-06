package com.Wang125510.roxy_client.mixin.client.keepMyScroll;

import com.Wang125510.roxy_client.Rules;
import com.Wang125510.roxy_client.data.DataManager;
import com.terraformersmc.modmenu.gui.ModsScreen;
import com.terraformersmc.modmenu.gui.widget.ModListWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModsScreen.class)
public abstract class ModScreenMixin extends Screen {
	@Shadow private ModListWidget modList;

	protected ModScreenMixin(Component title) {
		super(title);
	}

	@Override
	public void removed() {
		DataManager.modScroll.setScrollY(this.modList.scrollAmount());
		super.removed();
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void initTail(CallbackInfo ci) {
		if (Rules.mainSwitch && Rules.keepMyScroll){
			this.modList.setScrollAmount(DataManager.modScroll.getScrollY());
		}
	}
}