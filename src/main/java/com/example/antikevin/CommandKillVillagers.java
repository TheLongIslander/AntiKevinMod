package com.example.antikevin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class CommandKillVillagers extends CommandBase {

    @Override
    public String getCommandName() {
        return "killVillager";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/killVillager x1 y1 z1 x2 y2 z2";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("Only players can use this command."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        UUID uuid = player.getUniqueID();

        // Kevin gets punished instead of access
        if (uuid.equals(AntiKevinMod.KEVIN_UUID)) {
            player.addChatMessage(new ChatComponentText("Nice try Kevin. You’re not allowed to use this command."));
            int duration = 20 * 60 * 20; // 20 minutes in ticks
            player.addPotionEffect(new PotionEffect(9, duration, 1)); // 9 = nausea
            return;
        }

        if (args.length != 6) {
            player.addChatMessage(new ChatComponentText("Usage: /killVillager x1 y1 z1 x2 y2 z2"));
            return;
        }

        try {
            int x1 = parseInt(sender, args[0]);
            int y1 = parseInt(sender, args[1]);
            int z1 = parseInt(sender, args[2]);
            int x2 = parseInt(sender, args[3]);
            int y2 = parseInt(sender, args[4]);
            int z2 = parseInt(sender, args[5]);

            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                    Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                    Math.max(x1, x2) + 1, Math.max(y1, y2) + 1, Math.max(z1, z2) + 1
            );

            World world = player.getEntityWorld();
            List<EntityVillager> villagers = world.getEntitiesWithinAABB(EntityVillager.class, box);

            if (villagers.isEmpty()) {
                player.addChatMessage(new ChatComponentText("No villagers found in the specified area."));
            } else {
                for (EntityVillager villager : villagers) {
                    villager.setDead();
                }
                player.addChatMessage(new ChatComponentText("Killed " + villagers.size() + " villager(s)."));
                System.out.println("[AntiKevin] " + player.getCommandSenderName() + " killed " + villagers.size() + " villagers.");
            }

        } catch (NumberFormatException e) {
            player.addChatMessage(new ChatComponentText("Invalid coordinates!"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
