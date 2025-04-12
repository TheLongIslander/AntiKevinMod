package com.example.antikevin;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class PacketClearZone implements IMessage {
    @Override public void fromBytes(ByteBuf buf) {}
    @Override public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketClearZone, IMessage> {
        @Override
        public IMessage onMessage(PacketClearZone msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            UUID uuid = player.getUniqueID();
            AntiKevinMod.playerZones.remove(uuid);
            PlayerZoneStorage.save(AntiKevinMod.playerZones);
            return null;
        }
    }
}
