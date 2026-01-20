package com.Wang125510.roxy_client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
	private static final String MOD_ID = RoxyClient.MOD_ID;
	private static final Logger LOGGER = RoxyClient.LOGGER;
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void initialize() {
		loadConfig();
	}

	// 配置类实例和数据类定义
	private static Config config;
	private static final Path CONFIG_PATH = FabricLoader.getInstance()
			.getConfigDir()
			.resolve(MOD_ID + ".json");


	// 加载配置文件
	private static void loadConfig() {
		try {
			if (Files.exists(CONFIG_PATH)) {
				// 读取现有配置文件
				String content = new String(Files.readAllBytes(CONFIG_PATH));
				config = GSON.fromJson(content, Config.class);
				LOGGER.info("Positron Config Loaded: {}", CONFIG_PATH);
			} else {
				// 创建默认配置
				config = new Config();
				saveConfig();
				LOGGER.info("Positron Config Created: {}", CONFIG_PATH);
			}
		} catch (JsonSyntaxException e) {
			LOGGER.error("Malformed configuration file, reverting to default settings", e);
			config = new Config();
		} catch (IOException e) {
			LOGGER.error("Failed to read configuration file", e);
			config = new Config();
		}
	}

	// 获取配置文件
	public static Config getConfig() {
		return config;
	}

	// 保存配置文件
	private static void saveConfig() {
		try {
			// 确保配置目录存在
			Files.createDirectories(CONFIG_PATH.getParent());

			// 写入配置文件
			String json = GSON.toJson(config);
			Files.writeString(CONFIG_PATH, json);
			LOGGER.info("Config Saved: {}", CONFIG_PATH);
		} catch (IOException e) {
			LOGGER.error("Failed to save configuration file", e);
		}
	}

	// 重新加载配置（可用于游戏内命令）
	public static void reloadConfig() {
		loadConfig();
	}
}