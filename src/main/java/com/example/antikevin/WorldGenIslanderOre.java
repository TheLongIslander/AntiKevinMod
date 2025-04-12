package com.example.antikevin;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGenIslanderOre implements IWorldGenerator {
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkProvider, IChunkProvider chunkProvider2) {

        if (world.provider.dimensionId == 0) { // Overworld only
            generateOreInBiome(world, rand, chunkX * 16, chunkZ * 16);
        }
    }

    private void generateOreInBiome(World world, Random rand, int blockX, int blockZ) {
        BiomeGenBase biome = world.getBiomeGenForCoords(blockX, blockZ);
        if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleEdge || biome == BiomeGenBase.jungleHills) {
            for (int i = 0; i < 1; i++) { // Back to 1 per chunk
                int x = blockX + rand.nextInt(16);
                int y = rand.nextInt(16); // Y=0–15
                int z = blockZ + rand.nextInt(16);

                Block targetBlock = world.getBlock(x, y, z);
                if (targetBlock == Blocks.stone) {
                    world.setBlock(x, y, z, AntiKevinMod.islanderOre);
                }
            }
        }
    }
}
