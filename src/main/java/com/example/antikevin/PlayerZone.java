package com.example.antikevin;

import net.minecraft.util.AxisAlignedBB;

public class PlayerZone {
    public int x1 = -1, y1 = -1, z1 = -1;
    public int x2 = -1, y2 = -1, z2 = -1;

    public double destX = 0, destY = 0, destZ = 0;


    public PlayerZone() {
        // Default constructor for computeIfAbsent and manual zone editing
    }

    public PlayerZone(AxisAlignedBB box, double destX, double destY, double destZ) {
        this.x1 = (int) box.minX;
        this.y1 = (int) box.minY;
        this.z1 = (int) box.minZ;
        this.x2 = (int) box.maxX - 1;
        this.y2 = (int) box.maxY - 1;
        this.z2 = (int) box.maxZ - 1;
        this.destX = destX;
        this.destY = destY;
        this.destZ = destZ;
    }

    public AxisAlignedBB getZone() {
        if (x1 == -1 || x2 == -1) return null;

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2) + 1;
        int maxY = Math.max(y1, y2) + 1;
        int maxZ = Math.max(z1, z2) + 1;

        return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
