package com.mcaim.core.gui;

import com.mcaim.core.item.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class Gui {
    protected final Player player;
    protected final Inventory inventory;

    private final HashMap<Integer, Consumer<Player>> clickActions = new HashMap<>();
    private final List<Consumer<Player>> closeActions = new ArrayList<>();

    public Gui(Player player, String title, int size) {
        this.player = player;
        this.inventory = Bukkit.createInventory(new GUIHolder(), size, title);
    }

    public HashMap<Integer, Consumer<Player>> getClickActions() { return clickActions; }
    public List<Consumer<Player>> getCloseActions() { return closeActions; }

    public void open() {
        init();
        player.openInventory(inventory);
    }

    protected abstract void init();

    protected void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    protected void setItem(int slot, ItemStack itemStack, Consumer<Player> consumer) {
        setItem(slot, itemStack);
        registerButton(slot, consumer);
    }

    protected void fillItems(List<ItemStack> items) {
        int index = inventory.firstEmpty();

        for (ItemStack item : items) {
            setItem(index, item);
            index++;
        }
    }

    protected void fillBackGround() {
        fillBackGround(inventory.getSize() - 1);
    }

    protected void fillBackGround(int slotMax) {
        for (int i = 0; i <= slotMax; i++) {
            ItemStack currentItem = inventory.getItem(i);

            if (currentItem == null || currentItem.getType() == Material.AIR)
                setBackPanel(i);
        }
    }

    protected void registerButton(int slot, Consumer<Player> consumer) {
        clickActions.put(slot, consumer);
    }

    protected void unRegisterButton(int slot) {
        clickActions.remove(slot);
    }

    protected void unRegisterAllButtons() {
        clickActions.clear();
    }

    protected void registerCloseAction(Consumer<Player> consumer) {
        closeActions.add(consumer);
    }

    protected void setBackButton(Gui toLocation) {
        ItemStack backButton = ItemBuild.of(Material.OAK_FENCE_GATE).name("&f&lBack").lore("&7&nClick to go back").build();
        setItem(inventory.getSize() - 1, backButton, (player) -> { toLocation.open(); });
    }

    private void setBackPanel(int slot) {
        setItem(slot, ItemBuild.of(Material.BLACK_STAINED_GLASS_PANE).name("&8☠").build());
    }

    public class GUIHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() { return inventory; }

        public Gui getGui() { return Gui.this; }
    }
}
