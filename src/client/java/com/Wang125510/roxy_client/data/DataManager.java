package com.Wang125510.roxy_client.data;

import com.Wang125510.roxy_client.data.keepMyScroll.ModScroll;

public class DataManager {
	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	public static ModScroll modScroll = new ModScroll();
}
