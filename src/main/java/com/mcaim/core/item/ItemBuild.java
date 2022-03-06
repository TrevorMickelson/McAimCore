package com.mcaim.core.item;

import com.mcaim.core.CorePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class ItemBuild {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuild(ItemStack item) {
        this.item = item;
        meta = item.getItemMeta();
    }

    public ItemBuild name(String name) {
        editMeta(itemMeta -> itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name)));
        return this;
    }

    /**
     * Method designed for adding
     * only 1-3 lines of lore, for
     * easy usage
     */
    public ItemBuild lore(String... lore) {
        listLore(lore);
        return this;
    }

    public ItemBuild lore(List<String> lore) {
        String[] array = Arrays.copyOf(lore.toArray(), lore.size(), String[].class);
        listLore(array);
        return this;
    }

    public ItemBuild listLore(String[] lore) {
        List<String> listLore = new ArrayList<>();

        for (String string : lore)
            listLore.add(ChatColor.translateAlternateColorCodes('&', string));

        editMeta(itemMeta -> itemMeta.setLore(listLore));
        return this;
    }

    /**
     * This adds lore to the already
     * existing lore, it's designed for
     * updating the lore, not setting it
     */
    public ItemBuild addLore(String lore) {
        if (meta.getLore() != null) {
            List<String> newLore = new ArrayList<>(meta.getLore());
            newLore.add(lore);
            meta.setLore(newLore);
            item.setItemMeta(meta);
        } else {
            lore(lore);
        }
        return this;
    }

    /**
     * This removes lore from the
     * already existing lore
     */
    public ItemBuild removeLore(String lore) {
        List<String> metaLore = meta.getLore();
        if (metaLore != null && !metaLore.isEmpty()) {
            List<String> newLore = new ArrayList<>(meta.getLore());
            newLore.remove(lore);
            meta.setLore(newLore);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuild flag(ItemFlag... flag) {
        editMeta(itemMeta -> itemMeta.addItemFlags(flag));
        return this;
    }

    /**
     * This method enchants the item
     * it takes books into consideration
     */
    public ItemBuild enchant(Enchantment enchant, int level) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.meta;
            meta.addEnchant(enchant, level, true);
        } else {
            meta.addEnchant(enchant, level, true);
        }

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuild removeEnchant(Enchantment enchant) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.meta;
            meta.removeEnchant(enchant);
        } else {
            meta.removeEnchant(enchant);
        }

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuild potion(PotionType type, boolean extended, boolean upgraded) {
        PotionMeta potionMeta = (PotionMeta) meta;
        potionMeta.setBasePotionData(new PotionData(type, extended, upgraded));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuild glow() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuild amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuild giveUniqueKey(String key) {
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nameKey = new NamespacedKey(CorePlugin.getInstance(), key);
        data.set(nameKey, PersistentDataType.STRING, key);
        item.setItemMeta(meta);
        return this;
    }

    private void editMeta(Consumer<ItemMeta> consumer) {
        consumer.accept(meta);
        item.setItemMeta(meta);
    }

    /**
     * Returns the item stack itself
     * in case it needs to be accessed
     */
    public ItemStack build() { return item; }

    /**
     * Makes new item stack
     */
    public static ItemBuild of(Material material) { return new ItemBuild(new ItemStack(material)); }

    /**
     * Updates already existing item stack
     */
    public static ItemBuild of(ItemStack item) { return new ItemBuild(item); }
}
