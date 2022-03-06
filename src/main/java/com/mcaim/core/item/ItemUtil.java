package com.mcaim.core.item;

import com.mcaim.core.CorePlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtil {
    public static boolean hasUniqueKey(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null)
            return false;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.has(new NamespacedKey(CorePlugin.getInstance(), key), PersistentDataType.STRING);
    }

    public static String getUniqueKey(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null)
            return null;

        PersistentDataContainer data = meta.getPersistentDataContainer();

        for (NamespacedKey key : data.getKeys())
            return key.getKey();

        return null;
    }

    public static Material getFurnaceSmeltResult(Material from) {
        return CorePlugin.getInstance().getItemSmeltResult().get(from);
    }
}
