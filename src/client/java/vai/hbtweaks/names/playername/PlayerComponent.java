package vai.hbtweaks.names.playername;

import com.mojang.authlib.properties.Property;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vai.hbtweaks.names.HBTweakNames;

import java.util.UUID;
import java.util.function.Predicate;

public class PlayerComponent {
    private final Component customName;
    private final Component mcName;
    private final Font font;
    private final int width;
    private final UUID uuid;
    private static final int X_START = 5;
    private static final int Y_START = 5;
    private static final int BG_COLOR = 0xD0000000;
    private static PlayerComponent lastPlayer = null;

    public PlayerComponent(Player p) throws Exception {
        Minecraft mc = Minecraft.getInstance();
        // The RP name is not stored in the Player object anywhere. So we take it from the Tab menu
        PlayerInfo pi = mc.player.connection.getPlayerInfo(p.getUUID());
        // minecraft_name property is a fake minecraft name shows when hovering a name in the chat
        Component fakeName = null;
        try {
            fakeName = Component.literal(((Property) pi.getProfile().getProperties().get("minecraft_name").toArray()[0]).value())
                    .withStyle(ChatFormatting.DARK_GRAY);
        } catch (Exception ignored) {}
        Component offMcName = Component.literal(pi.getProfile().getName())
                .withStyle(ChatFormatting.DARK_GRAY);

        // If player is a GM, show both fake and real mc name
        if (mc.player.isCreative() || mc.player.isSpectator() || HBTweakNames.DEBUG_MODE) {
            this.mcName = fakeName == null ? offMcName : fakeName.copy().withStyle(ChatFormatting.YELLOW).append(" ").append(offMcName);
        } else {
            this.mcName = fakeName == null ? offMcName : fakeName;
        }
        this.customName = pi.getTabListDisplayName();
        this.font = mc.font;
        int cnw = this.font.width(this.customName.getString());
        int mnw = this.font.width(this.mcName.getString());
        this.width = Math.max(cnw, mnw);
        this.uuid = p.getUUID();
        if (mnw * cnw == 0)
            throw new Exception();
    }

    public void draw(GuiGraphics context) {
        context.fill(X_START - 2, Y_START - 2, X_START + width + 2, Y_START + 20, BG_COLOR);
        context.drawString(this.font, this.customName, X_START, Y_START, -1, true);
        context.drawString(this.font, this.mcName, X_START, Y_START + 10, -1, true);
    }

    public static PlayerComponent getTargetedPlayerComponent(Entity e, boolean seeThroughWall) {
        try {
            Player player = getTargetedPlayer(e, seeThroughWall);
            if (player == null)
                return null;
            if (PlayerComponent.lastPlayer == null || player.getUUID() != PlayerComponent.lastPlayer.uuid)
                PlayerComponent.lastPlayer = new PlayerComponent(player);
            return PlayerComponent.lastPlayer;
        } catch (Exception ignored) {}
        return null;
    }

    public static Player getTargetedPlayer(Entity e, boolean seeThroughWall) {
        try {
            Predicate<Entity> isVisible =
                entity -> !entity.isSpectator() && entity.isPickable() && !entity.isInvisible();
            // Maybe there is a better way to do this ? It's weird that I had to do this manually
            Vec3 ep = e.getEyePosition();
            Vec3 vv = e.getViewVector(1.0f);
            Vec3 ray = ep.add(vv.multiply(100f, 100f, 100f));
            AABB searchBox =
                    e.getBoundingBox().expandTowards(vv.scale(100f)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult result = ProjectileUtil.getEntityHitResult(e, ep, ray, searchBox,
                    isVisible, 10000f);
            if (!seeThroughWall) {
                HitResult hit = e.pick(100, 0, false);
                if (hit.distanceTo(e) < result.distanceTo(e))
                    return null;
            }
            return (Player) result.getEntity();

        } catch (Exception exception) {
            return null;
        }
    }
}
