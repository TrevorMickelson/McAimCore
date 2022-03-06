package com.mcaim.core.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ItemSmeltResult {
    /**
     * Key -> represents the default form of the block
     * Value -> represents what the block will smelt to
     *
     * Using HashMap rather than iterating through
     * recipes everytime to obtain a O(1) time complexity
     */
    private final Map<Material, Material> smeltAbles = new HashMap<>();

    public ItemSmeltResult() {
        registerSmeltAbles();
    }

    private void registerSmeltAbles() {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();

        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();

            if (!(recipe instanceof FurnaceRecipe)) continue;

            FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
            Material currentType = furnaceRecipe.getInput().getType();

            if (!currentType.isBlock()) continue;

            Material smeltResult = furnaceRecipe.getResult().getType();
            smeltAbles.put(currentType, smeltResult);
        }
    }

    public Material get(Material from) { return smeltAbles.get(from); }
}