package vai.hbtweaks.names.playername;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import vai.hbtweaks.names.HBTweakNames;
import vai.hbtweaks.names.HBTweakNamesClient;

public class CustomEndTickEvent implements ClientTickEvents.EndTick {

    private static PlayerComponent targetPlayer = null;

    public CustomEndTickEvent() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                ResourceLocation.fromNamespaceAndPath(HBTweakNames.MOD_ID, "before_chat"),
                CustomEndTickEvent::render);
    }

    private static void render(GuiGraphics context, DeltaTracker tickCounter) {

        if (CustomEndTickEvent.targetPlayer != null) {
            KeyMapping k = HBTweakNamesClient.HOLD_KEY;
            if (k.isDefault() || k.isDown())
                CustomEndTickEvent.targetPlayer.draw(context);
        }
    }

    @Override
    public void onEndTick(Minecraft client) {
        try {
            boolean is_mj = client.player.isCreative() || client.player.isSpectator() || HBTweakNames.DEBUG_MODE;
            CustomEndTickEvent.targetPlayer = PlayerComponent.getTargetedPlayerComponent(client.getCameraEntity(), is_mj);
        } catch (Exception ignored) {}
    }

}