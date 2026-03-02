package vai.hbtweaks.names;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBTweakNames implements ModInitializer {
	public static final String MOD_ID = "herobrine-companion";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean DEBUG_MODE = false;

	private static HBTweakNames instance;

	public static HBTweakNames getInstance() {
		return instance;
	}

	@Override
	public void onInitialize() {
		HBTweakNames.instance = this;
	}
}