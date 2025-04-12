package com.example.antikevin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.UUID;

public class ItemStaffOfAntiKevin extends Item {

    public ItemStaffOfAntiKevin() {
        this.setUnlocalizedName("staff_of_anti_kevin");
        this.setTextureName("antikevin:staff_of_anti_kevin");
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             int x, int y, int z, int side,
                             float hitX, float hitY, float hitZ) {

        if (world.isRemote) return true;

        UUID uuid = player.getUniqueID();

        if (uuid.equals(AntiKevinMod.KEVIN_UUID)) {
            player.addChatMessage(new ChatComponentText("You're not allowed to use this, Kevin."));
            int duration = 20 * 60 * 20; // 20 minutes in ticks
            player.addPotionEffect(new PotionEffect(Potion.poison.id, duration, 1));
            return true;
        }

        PlayerZone zone = AntiKevinMod.playerZones.computeIfAbsent(uuid, k -> new PlayerZone());

        if (player.isSneaking()) {
            zone.x1 = x;
            zone.y1 = y;
            zone.z1 = z;
            player.addChatMessage(new ChatComponentText("Corner 1 set to (" + x + ", " + y + ", " + z + ")"));
        } else {
            zone.x2 = x;
            zone.y2 = y;
            zone.z2 = z;
            player.addChatMessage(new ChatComponentText("Corner 2 set to (" + x + ", " + y + ", " + z + ")"));
        }

        if (zone.getZone() != null) {
            player.addChatMessage(new ChatComponentText("Teleport zone defined."));
        }

        return true;
    }
}
