/*
package com.example.antikevin;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockBangladeshiLeaves extends BlockLeaves {

    private IIcon icon;

    public BlockBangladeshiLeaves() {
        super();
        this.setBlockName("bangladeshi_leaves");
        this.setBlockTextureName("antikevin:bangladeshi_leaves");
        this.setHardness(0.2F);
        this.setStepSound(soundTypeGrass);
        this.setLightOpacity(1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 0; // Fancy leaves rendering
    }

    @Override
    public String[] func_150125_e() {
        return new String[] { "default" };
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.icon = reg.registerIcon("antikevin:bangladeshi_leaves");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.icon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.icon;
    }
}
*/
