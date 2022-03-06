package com.mcaim.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class PlayerUtil {
    // Get uuid of online or offline player
    public static UUID getUUID(String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? player.getUniqueId() : Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    // Get name of online or offline player
    public static String getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? player.getName() : Bukkit.getOfflinePlayer(uuid).getName();
    }

    public static void giveItem(Player player, ItemStack itemStack) {
        Map<Integer, ItemStack> map = player.getInventory().addItem(itemStack);

        if (!map.isEmpty())
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }

    public static boolean hasPlayedBefore(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null)
            return true;

        return Bukkit.getOfflinePlayer(uuid).hasPlayedBefore();
    }

    public static void removeItem(Player player, ItemStack itemStack, int amount) {
        int counter = 0;
        int removeAmount = amount;
        Inventory inv = player.getInventory();

        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                if (item.isSimilar(itemStack)) {
                    int itemAmount = item.getAmount();

                    if (removeAmount <= itemAmount) {
                        item.setAmount(itemAmount - removeAmount);
                        break;
                    } else {
                        inv.setItem(counter, new ItemStack(Material.AIR));
                        removeAmount-= itemAmount;
                    }
                }
            }

            counter++;
        }
    }
}

