package com.Wang125510.roxy_client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class RoxyClientClient implements ClientModInitializer {
	private static final String MOD_ID = RoxyClient.MOD_ID;
	private static final Logger LOGGER = RoxyClient.LOGGER;

	@Override
	public void onInitializeClient() {
		ConfigManager.initialize();

		LOGGER.info("Roxy Client Initialized!");
	}
}