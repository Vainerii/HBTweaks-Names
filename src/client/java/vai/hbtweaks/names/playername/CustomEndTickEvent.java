package vai.hbtweaks.names.playername;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import vai.hbtweaks.names.HBTweakNames;
import vai.hbtweaks.names.HBTweakNamesClient;

public class CustomEndTickEvent implements ClientTickEvents.EndTick {

    private static PlayerComponent targetPlayer = null;
    private static Identifier ID = Identifier.fromNamespaceAndPath(HBTweakNames.MOD_ID, "before_chat");

    public CustomEndTickEvent() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                CustomEndTickEvent.ID,
                CustomEndTickEvent::render);
    }

    private static void render(GuiGraphicsExtractor context, DeltaTracker tickCounter) {

        if (CustomEndTickEvent.targetPlayer != null) {
            KeyMapping k = HBTweakNamesClient.HOLD_KEY;
            if (k.isDefault() || k.isDown())
                CustomEndTickEvent.targetPlayer.draw(context);
        }
    }

    @Override
    public void onEndTick(Minecraft client) {
        try {
            CustomEndTickEvent.targetPlayer = PlayerComponent.getTargetedPlayerComponent(client.getCameraEntity(), PlayerComponent.hasPerm());
        } catch (Exception ignored) {}
    }

}