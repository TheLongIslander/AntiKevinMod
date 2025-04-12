/*
package com.example.antikevin;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGenBangladeshiTrees implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkProvider, IChunkProvider chunkProvider2) {

        if (world.provider.dimensionId != 0) return; // Overworld only

        int x = chunkX * 16 + rand.nextInt(16);
        int z = chunkZ * 16 + rand.nextInt(16);
        int y = world.getTopSolidOrLiquidBlock(x, z);

        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleEdge || biome == BiomeGenBase.jungleHills) {
            if (rand.nextInt(4) == 0) {
                boolean success = new WorldGenBangladeshiPalmTree().generate(world, rand, x, y, z);
                if (success) {
                    System.out.println("[TreeGen] Bangladeshi Palm Tree spawned at (" + x + ", " + y + ", " + z + ") in Jungle biome");
                }
            }
        } else if (biome == BiomeGenBase.forest || biome == BiomeGenBase.forestHills) {
            if (rand.nextInt(1) == 0) { // Always true — spawn every time
                boolean success = new WorldGenBangladeshiPalmTree().generate(world, rand, x, y, z);
                if (success) {
                    System.out.println("[TreeGen] Bangladeshi Palm Tree spawned at (" + x + ", " + y + ", " + z + ") in Forest biome");
                }
            }
        } else if (biome == BiomeGenBase.plains) {
            if (rand.nextInt(25) == 0) {
                boolean success = new WorldGenBangladeshiPalmTree().generate(world, rand, x, y, z);
                if (success) {
                    System.out.println("[TreeGen] Bangladeshi Palm Tree spawned at (" + x + ", " + y + ", " + z + ") in Plains biome");
                }
            }
        }
    }
}
*/
