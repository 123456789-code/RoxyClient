package com.Wang125510.roxy_client;

public class Config {
	public Config() {}
	private static final boolean isDebugMode = RoxyClient.DEBUG_MODE;

	private boolean betterBeacon = isDebugMode;
	public boolean getBetterBeacon() { return betterBeacon; }

	private boolean keepMyScroll = isDebugMode;
	public boolean getKeepMyScroll() { return keepMyScroll; }

	private boolean highlightItemEntity = isDebugMode;
	public boolean getHighlightItemEntity() { return highlightItemEntity; }
}
