package com.example.antikevin;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemIslanderIngot extends Item {

    public ItemIslanderIngot() {
        this.setUnlocalizedName("islander_ingot");
        this.setTextureName("antikevin:islander_ingot");
        this.setCreativeTab(CreativeTabs.tabMaterials); // Appears under Materials tab
    }
}
