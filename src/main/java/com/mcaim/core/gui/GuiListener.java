package com.mcaim.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getClickedInventory();

        if (item != null && inventory != null) {
            // Getting inventory the player is looking at
            Gui gui = getGUIFromInventory(player.getOpenInventory().getTopInventory());

            if (gui == null)
                return;

            event.setCancelled(true);

            // Making sure player is clicking in top menu (before making checks)
            if (Objects.equals(player.getInventory(), inventory))
                return;

            if (gui.getClickActions().isEmpty())
                return;

            // Handling the click action
            Consumer<Player> playerConsumer = gui.getClickActions().get(event.getSlot());

            if (playerConsumer != null)
                playerConsumer.accept(player);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        // If player closes chest (narrows down checks)
        if (inventory.getType() == InventoryType.CHEST) {
            Gui gui = getGUIFromInventory(inventory);

            if (gui != null) {
                if (!gui.getCloseActions().isEmpty()) {
                    for (Consumer<Player> playerConsumer : gui.getCloseActions())
                        playerConsumer.accept(player);
                }
            }
        }
    }

    private Gui getGUIFromInventory(Inventory inventory) {
        if (inventory.getHolder() == null) return null;
        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof Gui.GUIHolder)) return null;

        return ((Gui.GUIHolder) holder).getGui();
    }
}
