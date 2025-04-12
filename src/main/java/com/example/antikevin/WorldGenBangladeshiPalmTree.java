/*
package com.example.antikevin;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBangladeshiPalmTree extends WorldGenerator {

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        Block log = AntiKevinMod.bangladeshiLog;
        Block leaves = AntiKevinMod.bangladeshiLeaves;

        // Ground check
        Block ground = world.getBlock(x, y - 1, z);
        if (ground != Blocks.grass && ground != Blocks.dirt) return false;

        // Height of the trunk
        int height = 7 + rand.nextInt(4); // 7 to 10 blocks tall
        int topY = y + height;

        // Check if space is clear (7x12x7 box)
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                for (int dy = 0; dy <= height + 4; dy++) {
                    Block b = world.getBlock(x + dx, y + dy, z + dz);
                    if (!world.isAirBlock(x + dx, y + dy, z + dz) && b != Blocks.air && b != Blocks.leaves) {
                        return false;
                    }
                }
            }
        }

        // Build trunk
        for (int i = 0; i < height; i++) {
            world.setBlock(x, y + i, z, log);
        }

        // Generate palm fronds (in 4 main directions and diagonals)
        int leafLen = 3 + rand.nextInt(2); // 3–4 block fronds

        int[][] dirs = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {-1, -1}, {-1, 1}, {1, -1}
        };

        for (int[] dir : dirs) {
            for (int i = 1; i <= leafLen; i++) {
                int dx = dir[0] * i;
                int dz = dir[1] * i;
                placeLeaf(world, x + dx, topY,     z + dz, leaves);
                if (i == leafLen || i == 1) placeLeaf(world, x + dx, topY - 1, z + dz, leaves); // adds underfrond
                if (i == 1) placeLeaf(world, x + dx, topY + 1, z + dz, leaves); // adds height
            }
        }

        // Top leaf cluster
        placeLeaf(world, x, topY, z, leaves);
        placeLeaf(world, x, topY + 1, z, leaves);
        placeLeaf(world, x, topY + 2, z, leaves);

        return true;
    }

    private void placeLeaf(World world, int x, int y, int z, Block leaf) {
        if (world.isAirBlock(x, y, z)) {
            world.setBlock(x, y, z, leaf);
        }
    }
}
*/
