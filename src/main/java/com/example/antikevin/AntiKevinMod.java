package com.example.antikevin;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod(modid = AntiKevinMod.MODID, version = AntiKevinMod.VERSION)
public class AntiKevinMod {
    public static final String MODID = "antikevin";
    public static final String VERSION = "1.0";

    public static final UUID KEVIN_UUID = UUID.fromString("3f56d3af-e493-43ec-81a9-d643c7caf956");

    public static final Map<UUID, PlayerZone> playerZones = new HashMap<>();

    public static Block islanderOre;
    public static Item islanderIngot;
    public static Item staffOfAntiKevin;

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = "com.example.antikevin.ClientProxy", serverSide = "com.example.antikevin.ServerProxy")
    public static IProxy proxy;

    private int tickCounter = 0;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Anti-Kevin mod loaded. Villagers beware.");

        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(PacketSetDestination.Handler.class, PacketSetDestination.class, 0, Side.SERVER);
        network.registerMessage(PacketClearZone.Handler.class, PacketClearZone.class, 1, Side.SERVER);

        islanderOre = new BlockIslanderOre();
        GameRegistry.registerBlock(islanderOre, "islander_ore");

        islanderIngot = new ItemIslanderIngot();
        GameRegistry.registerItem(islanderIngot, "islander_ingot");
        GameRegistry.addSmelting(islanderOre, new ItemStack(islanderIngot), 0.7F);

        staffOfAntiKevin = new ItemStaffOfAntiKevin();
        GameRegistry.registerItem(staffOfAntiKevin, "staff_of_anti_kevin");

        GameRegistry.addRecipe(new ItemStack(staffOfAntiKevin),
                "II",
                " S",
                " S",
                'I', islanderIngot,
                'S', Items.stick);

        GameRegistry.registerWorldGenerator(new WorldGenIslanderOre(), 0);

        FMLCommonHandler.instance().bus().register(this);

        proxy.registerKeybinds();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        playerZones.clear();
        playerZones.putAll(PlayerZoneStorage.load());
        event.registerServerCommand(new CommandSendVillagers());
        event.registerServerCommand(new CommandKillVillagers());
    }

    @Mod.EventHandler
    public void onServerStopping(cpw.mods.fml.common.event.FMLServerStoppingEvent event) {
        PlayerZoneStorage.save(playerZones);
        System.out.println("[AntiKevin] Saved player teleport zones to disk.");
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter >= 20) {
            tickCounter = 0;

            for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                if (!(obj instanceof EntityPlayerMP)) continue;
                EntityPlayerMP player = (EntityPlayerMP) obj;
                UUID uuid = player.getUniqueID();

                // Kevin check: remove staff if present
                if (uuid.equals(KEVIN_UUID)) {
                    for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                        ItemStack stack = player.inventory.mainInventory[i];
                        if (stack != null && stack.getItem() == staffOfAntiKevin) {
                            ItemStack replacement = new ItemStack(Items.diamond_hoe);
                            replacement.setStackDisplayName("hahaha you tried bro");
                            player.inventory.mainInventory[i] = replacement;
                            player.addChatMessage(new ChatComponentText("You are unworthy of the Staff of Anti Kevin."));
                            System.out.println("[AntiKevin] Confiscated staff from Kevin.");
                        }
                    }
                }

                PlayerZone zone = playerZones.get(uuid);
                if (zone == null || zone.getZone() == null) continue;

                World world = player.worldObj;
                List<EntityVillager> villagers = world.getEntitiesWithinAABB(EntityVillager.class, zone.getZone());

                for (EntityVillager villager : villagers) {
                    double dx = zone.destX + 0.5;
                    double dy = zone.destY;
                    double dz = zone.destZ + 0.5;

                    if (villager.getDistanceSq(dx, dy, dz) > 1.0) {
                        villager.setPositionAndUpdate(dx, dy, dz);
                    }
                }
            }

            int tickCount = MinecraftServer.getServer().getTickCounter();
            if ((tickCount % 600) == 0) {
                PlayerZoneStorage.save(playerZones);
                System.out.println("[AntiKevin] Auto-saved player zones.");
            }
        }
    }
}
