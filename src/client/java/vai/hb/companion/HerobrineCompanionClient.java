package vai.hb.companion;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import vai.hb.companion.playername.CustomEndTickEvent;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;

import static vai.hb.companion.HerobrineCompanion.LOGGER;


public class HerobrineCompanionClient implements ClientModInitializer {

	static final CustomEndTickEvent cet = new CustomEndTickEvent();

	public static final KeyMapping HOLD_KEY = new KeyMapping(
			"Afficher nom (reset = constant)",
			InputConstants.Type.KEYSYM,
			InputConstants.UNKNOWN.getValue(),
			"Herobrine Companion");


	@Override
	public void onInitializeClient() {
		LOGGER.info("Initialisation du companion d'Herobrine.fr ! <3");

		ClientTickEvents.END_CLIENT_TICK.register(cet);

		KeyBindingHelper.registerKeyBinding(HOLD_KEY);

	}


}