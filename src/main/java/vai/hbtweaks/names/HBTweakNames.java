package vai.hbtweaks.names;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HBTweakNames implements ModInitializer {
	public static final String MOD_ID = "herobrine-companion";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean DEBUG_MODE = false;

	private static HBTweakNames instance;

	public static HBTweakNames getInstance() {
		return instance;
	}

	public File getConfigDir() {
		return FabricLoader.getInstance().getConfigDir().toFile();
	}

	@Override
	public void onInitialize() {
		HBTweakNames.instance = this;
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}
}