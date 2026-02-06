package com.Wang125510.roxy_client.mixin.client.highlightEntity;

import com.Wang125510.roxy_client.Rules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
	private void alwaysGlowing(CallbackInfoReturnable<Boolean> cir) {
		if (Rules.mainSwitch && Rules.highlightEntity) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}
