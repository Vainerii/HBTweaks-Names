package vai.hbtweaks.names;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import vai.hbtweaks.names.playeritem.ItemViewerCommand;
import vai.hbtweaks.names.playername.CustomEndTickEvent;

import net.minecraft.client.KeyMapping;

public class HBTweakNamesClient implements ClientModInitializer {

	static final CustomEndTickEvent cet = new CustomEndTickEvent();

	public static final String NAME = "HB Tweaks - Names";

	public static KeyMapping HOLD_KEY = new KeyMapping(
			"HB - Afficher nom (vide = permanent)",
			InputConstants.Type.KEYSYM,
			InputConstants.UNKNOWN.getValue(),
			KeyMapping.Category.MISC);

	@Override
	public void onInitializeClient() {

		KeyMappingHelper.registerKeyMapping(HOLD_KEY);

		ClientTickEvents.END_CLIENT_TICK.register(cet);

		ItemViewerCommand.init();
	}


}