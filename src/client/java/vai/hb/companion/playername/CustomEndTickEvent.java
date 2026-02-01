package vai.hb.companion.playername;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vai.hb.companion.HerobrineCompanion;
import vai.hb.companion.HerobrineCompanionClient;

import java.util.UUID;
import java.util.function.Predicate;

public class CustomEndTickEvent implements ClientTickEvents.EndTick {

    private static PlayerComponent targetPlayer = null;

    private UUID lastUUID = null;

    public CustomEndTickEvent() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                ResourceLocation.fromNamespaceAndPath(HerobrineCompanion.MOD_ID, "before_chat"),
                CustomEndTickEvent::render);
    }

    private static void render(GuiGraphics context, DeltaTracker tickCounter) {

        if (CustomEndTickEvent.targetPlayer != null) {
            KeyMapping k = HerobrineCompanionClient.HOLD_KEY;
            if (k.isDefault() || k.isDown())
                CustomEndTickEvent.targetPlayer.draw(context);
        }
    }


    @Override
    public void onEndTick(Minecraft client) {
        try {
            Predicate<Entity> isVisible =
                    entity -> !entity.isSpectator() && entity.isPickable() && !entity.isInvisible();
            // Maybe there is a better way to do this ? It's weird that I had to do this manually
            Entity ce = client.getCameraEntity();
            Vec3 ep = ce.getEyePosition();
            Vec3 vv = ce.getViewVector(1.0f);
            Vec3 ray = ep.add(vv.multiply(100f, 100f, 100f));
            AABB searchBox =
                    ce.getBoundingBox().expandTowards(vv.scale(100f)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult result = ProjectileUtil.getEntityHitResult(ce, ep, ray, searchBox,
                    isVisible, 10000f);
            HitResult hit = client.getCameraEntity().pick(100, 0, false);
            if (hit.distanceTo(client.cameraEntity) < result.distanceTo(client.cameraEntity))
                throw new Exception();

            if (result.getEntity().getUUID() != this.lastUUID) {

                this.lastUUID = result.getEntity().getUUID();
                if (result.getEntity().getType() == EntityType.PLAYER) {
                    this.changeFocus((Player) result.getEntity());
                }
                else {
                    resetFocus();
                }
            }
        } catch (Exception e) {
            if (this.lastUUID != null) {
                this.lastUUID = null;
                this.resetFocus();
            }
        }
    }

    public void changeFocus(Player p) {
        try {
            CustomEndTickEvent.targetPlayer = new PlayerComponent(p);
        } catch (Exception e) {
            this.resetFocus();
        }
    }

    public void resetFocus() {
        CustomEndTickEvent.targetPlayer = null;
    }
}