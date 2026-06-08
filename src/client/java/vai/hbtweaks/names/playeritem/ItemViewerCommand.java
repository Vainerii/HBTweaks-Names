package vai.hbtweaks.names.playeritem;

import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import vai.hbtweaks.names.HBTweakNames;
import vai.hbtweaks.names.playername.PlayerComponent;

public class ItemViewerCommand {
    private static final ItemViewerCommand instance = new ItemViewerCommand();

    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("seeitems").executes(context -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player.isCreative() || minecraft.player.isSpectator() || HBTweakNames.DEBUG_MODE) {
                    return instance.runCommand();
                } else {
                    minecraft.player.sendSystemMessage(Component.literal("Vous n'avez pas la permission de lancer cette commande"));
                }
                return 1;
            }));
        });
    }

    private MutableComponent itemToComponent(ItemStack i) {
        try {
            if (!i.isEmpty()) {
                HoverEvent he = new HoverEvent.ShowItem(ItemStackTemplate.fromNonEmptyStack(i));
                MutableComponent cn = i.getStyledHoverName().copy();
                return cn.setStyle(cn.getStyle().applyFormat(ChatFormatting.RESET).withHoverEvent(he).withColor(7251171));
            }
        } catch (Exception ignored) {}
        return Component.literal("[Vide]").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
    }

    public int runCommand() {
        Minecraft minecraft = Minecraft.getInstance();
        try {
            Player target = minecraft.player;
            Component tabName = Minecraft.getInstance().player.connection.getPlayerInfo(target.getUUID()).getTabListDisplayName();
            Component targetName = tabName != null ? tabName : target.getName();

            MutableComponent message = Component.literal("Items de ")
                    .append(targetName)
                    .append(" : ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.MAINHAND)))
                    .append(", ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.OFFHAND)))
                    .append(", ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.HEAD)))
                    .append(", ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.CHEST)))
                    .append(", ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.LEGS)))
                    .append(", ")
                    .append(itemToComponent(target.getItemBySlot(EquipmentSlot.FEET)))
                    .append(".");

            minecraft.player.sendSystemMessage(message);
            return 0;
        } catch (Exception ignored) {
            // TODO test
            minecraft.player.sendSystemMessage(Component.literal("Pas d'item à récupérer"));
        }
        return 1;
    }

}
