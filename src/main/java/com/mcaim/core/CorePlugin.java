package com.mcaim.core;

import com.mcaim.core.events.listeners.ArmorListener;
import com.mcaim.core.gui.GuiListener;
import com.mcaim.core.item.ItemSmeltResult;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {
    private static CorePlugin corePlugin;
    private ItemSmeltResult itemSmeltResult;

    @Override
    public void onEnable() {
        corePlugin = this;
        itemSmeltResult = new ItemSmeltResult();
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);

    }

    public ItemSmeltResult getItemSmeltResult() { return itemSmeltResult; }
    public static CorePlugin getInstance() { return corePlugin; }
}
