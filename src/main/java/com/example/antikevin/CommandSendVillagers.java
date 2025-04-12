package com.example.antikevin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.UUID;

public class CommandSendVillagers extends CommandBase {

    @Override
    public String getCommandName() {
        return "sendVillagers";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sendVillagers x1 y1 z1 x2 y2 z2 xDest yDest zDest OR /sendVillagers clear";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("Only players can use this command."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        UUID uuid = player.getUniqueID();
        World world = player.getEntityWorld();

        if (uuid.equals(AntiKevinMod.KEVIN_UUID)) {
            player.addChatMessage(new ChatComponentText("Nice try Kevin, enjoy the void :)"));
            int duration = 6000;
            player.addPotionEffect(new PotionEffect(Potion.blindness.id, duration, 1));
            player.addPotionEffect(new PotionEffect(Potion.confusion.id, duration, 1));
            return;
        }

        // Handle clearing the zone
        if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            if (AntiKevinMod.playerZones.remove(uuid) != null) {
                PlayerZoneStorage.save(AntiKevinMod.playerZones);
                player.addChatMessage(new ChatComponentText("Your villager teleport zone has been cleared."));
                System.out.println("[AntiKevin] Zone cleared for " + player.getCommandSenderName());
            } else {
                player.addChatMessage(new ChatComponentText("You don't have a zone set."));
            }
            return;
        }

        // Handle setting the zone
        if (args.length != 9) {
            player.addChatMessage(new ChatComponentText("Usage: /sendVillagers x1 y1 z1 x2 y2 z2 xDest yDest zDest OR /sendVillagers clear"));
            return;
        }

        try {
            int x1 = parseInt(sender, args[0]);
            int y1 = parseInt(sender, args[1]);
            int z1 = parseInt(sender, args[2]);
            int x2 = parseInt(sender, args[3]);
            int y2 = parseInt(sender, args[4]);
            int z2 = parseInt(sender, args[5]);
            double xDest = Double.parseDouble(args[6]);
            double yDest = Double.parseDouble(args[7]);
            double zDest = Double.parseDouble(args[8]);

            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                    Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                    Math.max(x1, x2) + 1, Math.max(y1, y2) + 1, Math.max(z1, z2) + 1
            );

            PlayerZone zone = new PlayerZone(box, xDest, yDest, zDest);
            AntiKevinMod.playerZones.put(uuid, zone);
            PlayerZoneStorage.save(AntiKevinMod.playerZones);

            player.addChatMessage(new ChatComponentText("Your villager teleport zone has been set."));
            System.out.println("[AntiKevin] Zone set for " + player.getCommandSenderName() + ": from (" + x1 + "," + y1 + "," + z1 + ") to (" + x2 + "," + y2 + "," + z2 + ")");
            System.out.println("[AntiKevin] Destination: " + xDest + ", " + yDest + ", " + zDest);

        } catch (NumberFormatException e) {
            player.addChatMessage(new ChatComponentText("Invalid coordinates!"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
