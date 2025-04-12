package com.example.antikevin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.UUID;

public class GuiTeleportDestination extends GuiScreen {

    private GuiTextField xField, yField, zField;
    private GuiButton confirmButton;
    private GuiButton currentCoordsButton;
    private GuiButton clearZoneButton;

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.buttonList.clear();

        UUID uuid = Minecraft.getMinecraft().thePlayer.getUniqueID();
        PlayerZone zone = AntiKevinMod.playerZones.get(uuid);

        int dx = 0, dy = 0, dz = 0;
        if (zone != null) {
            dx = (int) zone.destX;
            dy = (int) zone.destY;
            dz = (int) zone.destZ;
        }

        xField = new GuiTextField(this.fontRendererObj, centerX - 50, centerY - 40, 100, 20);
        xField.setText(Integer.toString(dx));
        xField.setFocused(true);

        yField = new GuiTextField(this.fontRendererObj, centerX - 50, centerY - 10, 100, 20);
        yField.setText(Integer.toString(dy));

        zField = new GuiTextField(this.fontRendererObj, centerX - 50, centerY + 20, 100, 20);
        zField.setText(Integer.toString(dz));

        confirmButton = new GuiButton(0, centerX - 80, centerY + 55, 60, 20, "Set");
        currentCoordsButton = new GuiButton(1, centerX - 10, centerY + 55, 120, 20, "Current Coordinates");
        clearZoneButton = new GuiButton(2, centerX - 80, centerY + 80, 120, 20, "Clear Zone");

        this.buttonList.add(confirmButton);
        this.buttonList.add(currentCoordsButton);
        this.buttonList.add(clearZoneButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int z = Integer.parseInt(zField.getText());

                AntiKevinMod.network.sendToServer(new PacketSetDestination(x, y, z));
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Teleport destination set to (" + x + ", " + y + ", " + z + ")"));
                Minecraft.getMinecraft().displayGuiScreen(null);
            } catch (NumberFormatException e) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Invalid coordinates."));
            }
        } else if (button.id == 1) {
            double x = Minecraft.getMinecraft().thePlayer.posX;
            double y = Minecraft.getMinecraft().thePlayer.posY;
            double z = Minecraft.getMinecraft().thePlayer.posZ;

            xField.setText(Integer.toString((int) Math.floor(x)));
            yField.setText(Integer.toString((int) Math.floor(y)));
            zField.setText(Integer.toString((int) Math.floor(z)));
        } else if (button.id == 2) {
            UUID uuid = Minecraft.getMinecraft().thePlayer.getUniqueID();
            AntiKevinMod.playerZones.remove(uuid);
            AntiKevinMod.network.sendToServer(new PacketClearZone());
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Teleport zone cleared."));
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        xField.textboxKeyTyped(typedChar, keyCode);
        yField.textboxKeyTyped(typedChar, keyCode);
        zField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        xField.mouseClicked(mouseX, mouseY, mouseButton);
        yField.mouseClicked(mouseX, mouseY, mouseButton);
        zField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Set Teleport Destination", this.width / 2, this.height / 2 - 70, 0xFFFFFF);
        xField.drawTextBox();
        yField.drawTextBox();
        zField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
