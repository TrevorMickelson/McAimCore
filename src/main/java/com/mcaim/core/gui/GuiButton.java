package com.mcaim.core.gui;

import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;

public class GuiButton {
    private final ClickType clickType;
    private final Sound sound;
    private final ClickAction clickAction;

    public GuiButton(ClickType clickType, Sound sound, ClickAction clickAction) {
        this.clickType = clickType;
        this.sound = sound;
        this.clickAction = clickAction;
    }

    public ClickType getClickType() { return clickType; }
    public Sound getSound() { return sound; }
    public ClickAction getClickAction() { return clickAction; }
}
