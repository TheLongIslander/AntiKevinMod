package com.example.antikevin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerZoneStorage {

    private static final Gson GSON = new Gson();

    private static File getZoneFile() {
        // Get the main overworld directory
        World world = MinecraftServer.getServer().worldServers[0];
        File worldDir = world.getSaveHandler().getWorldDirectory();
        return new File(worldDir, "player_zones.json");
    }

    public static void save(Map<UUID, PlayerZone> zones) {
        File file = getZoneFile();
        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(zones, writer);
        } catch (IOException e) {
            System.err.println("[AntiKevin] Failed to save player zones: " + e.getMessage());
        }
    }

    public static Map<UUID, PlayerZone> load() {
        File file = getZoneFile();
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<UUID, PlayerZone>>() {}.getType();
            return GSON.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("[AntiKevin] Failed to load player zones: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
