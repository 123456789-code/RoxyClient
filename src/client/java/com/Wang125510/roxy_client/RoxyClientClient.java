package com.Wang125510.roxy_client;

import com.Wang125510.roxy_client.config.ConfigManager;
import com.Wang125510.roxy_client.config.ConfigScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.KeyMapping.Category.MISC;

public class RoxyClientClient implements ClientModInitializer {
	private static final String MOD_ID = RoxyClient.MOD_ID;
	private static final Logger LOGGER = RoxyClient.LOGGER;

	public static KeyMapping openConfigKey;

	@Override
	public void onInitializeClient() {
		ConfigManager.initialize();

		openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"roxy_client.keybinding.openconfig",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_O,
				MISC
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openConfigKey.consumeClick()) {
				client.setScreen(new ConfigScreen(null));
			}
		});

		LOGGER.info("Roxy Client Initialized!");
	}
}