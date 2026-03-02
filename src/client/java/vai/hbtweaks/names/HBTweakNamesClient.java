package vai.hbtweaks.names;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import vai.hbtweaks.names.playeritem.ItemViewerCommand;
import vai.hbtweaks.names.playername.CustomEndTickEvent;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;

public class HBTweakNamesClient implements ClientModInitializer {

	static final CustomEndTickEvent cet = new CustomEndTickEvent();

	public static final String NAME = "HB Tweaks - Names";

	public static final KeyMapping HOLD_KEY = new KeyMapping(
			"Afficher nom (reset = permanent)",
			InputConstants.Type.KEYSYM,
			InputConstants.UNKNOWN.getValue(),
			NAME);

	@Override
	public void onInitializeClient() {

		KeyBindingHelper.registerKeyBinding(HOLD_KEY);

		ClientTickEvents.END_CLIENT_TICK.register(cet);

		ItemViewerCommand.init();
	}


}