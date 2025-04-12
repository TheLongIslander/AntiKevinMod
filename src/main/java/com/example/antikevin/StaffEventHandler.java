/*
package com.example.antikevin;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class StaffEventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (Keyboard.getEventKey() == Keyboard.KEY_F12 && Keyboard.getEventKeyState()) {
            if (mc.thePlayer != null && mc.thePlayer.getHeldItem() != null &&
                    mc.thePlayer.getHeldItem().getItem() instanceof ItemStaffOfAntiKevin) {

                mc.thePlayer.addChatMessage(new ChatComponentText("🪟 GUI not implemented yet (F12 triggered)."));
                // Replace this with: mc.displayGuiScreen(new GuiTeleportConfig()); once your GUI is implemented
            }
        }
    }
}
*/
