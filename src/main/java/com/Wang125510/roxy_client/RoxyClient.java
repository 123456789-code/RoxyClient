package com.Wang125510.roxy_client;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoxyClient implements ModInitializer {
	public static final String MOD_ID = "roxy_client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean DEBUG_MODE = true;

	@Override
	public void onInitialize() {
		LOGGER.info("Roxy Client Initialized");
	}
}