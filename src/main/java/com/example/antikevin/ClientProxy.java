package com.example.antikevin;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class ClientProxy implements IProxy {

    @Override
    public void registerKeybinds() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() == Keyboard.KEY_F12 && Keyboard.getEventKeyState()) {
            Minecraft mc = Minecraft.getMinecraft();

            if (mc.thePlayer != null &&
                    mc.thePlayer.getHeldItem() != null &&
                    mc.thePlayer.getHeldItem().getItem() instanceof ItemStaffOfAntiKevin) {

                mc.displayGuiScreen(new GuiTeleportDestination());
            }
        }
    }
}
