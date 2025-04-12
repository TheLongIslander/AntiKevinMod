package com.example.antikevin;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.UUID;

public class PacketSetDestination implements IMessage {
    public int x, y, z;

    public PacketSetDestination() {}

    public PacketSetDestination(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketSetDestination, IMessage> {
        @Override
        public IMessage onMessage(PacketSetDestination msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            UUID uuid = player.getUniqueID();

            if (uuid.equals(AntiKevinMod.KEVIN_UUID)) {
                int duration = 20 * 60 * 20; // 20 minutes in ticks
                player.addPotionEffect(new PotionEffect(Potion.poison.id, duration, 1));
                player.addChatMessage(new net.minecraft.util.ChatComponentText("Kevin, you are not allowed to set destinations."));
                return null;
            }

            PlayerZone zone = AntiKevinMod.playerZones.computeIfAbsent(uuid, k -> new PlayerZone());
            zone.destX = msg.x;
            zone.destY = msg.y;
            zone.destZ = msg.z;

            return null;
        }
    }
}
