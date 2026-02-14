package vai.hbtweaks.names.playername;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PlayerComponent {
    private final Component customName;
    private final Component mcName;
    private final Font font;
    private final int width;
    private static final int X_START = 5;
    private static final int Y_START = 5;
    private static final int BG_COLOR = 0xD0000000;

    public PlayerComponent(Player p) throws Exception {
        // The RP name is not stored in the Player object anywhere. So we take it from the Tab menu
        PlayerInfo pi = Minecraft.getInstance().player.connection.getPlayerInfo(p.getUUID());
        this.mcName = Component.literal(pi.getProfile().getName())
                .withStyle(ChatFormatting.DARK_GRAY);
        this.customName = pi.getTabListDisplayName();
        this.font = Minecraft.getInstance().font;
        this.width = Math.max(
                this.font.width(this.customName.getString()),
                this.font.width(this.mcName.getString()));

        try {
            // Checking this to avoid showing getting the name of fake players and NPCs
            assert !this.mcName.getString().isEmpty();
            assert !this.customName.getString().isEmpty();
        } catch (AssertionError e) {
            throw new Exception(e);
        }
    }

    public void draw(GuiGraphics context) {
        context.fill(X_START - 2, Y_START - 2, X_START + width + 2, Y_START + 18 + 2, BG_COLOR);
        context.drawString(this.font, this.customName, X_START, Y_START, -1, true);
        context.drawString(this.font, this.mcName, X_START, Y_START + 10, -1, true);
    }
}
