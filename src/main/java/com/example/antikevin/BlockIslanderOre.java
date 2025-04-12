package com.example.antikevin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockIslanderOre extends Block {

    public BlockIslanderOre() {
        super(Material.rock);
        this.setBlockName("islander_ore");
        this.setBlockTextureName("antikevin:islander_ore");
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 2); // Iron pickaxe or better
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
