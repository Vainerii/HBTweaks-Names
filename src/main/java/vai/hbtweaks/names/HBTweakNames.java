package vai.hbtweaks.names;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HBTweakNames implements ModInitializer {
	public static final String MOD_ID = "hbtweaks-names";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean DEBUG_MODE = new File(".hbtweaks_debug").exists();

	public static HBTweakNames instance;

	@Override
	public void onInitialize() {
		HBTweakNames.instance = this;
		LOGGER.info("Initialisation de " + MOD_ID);
    }
}