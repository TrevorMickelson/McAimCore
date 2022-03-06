package com.mcaim.core.events;

import com.mcaim.core.events.models.Armor;
import org.bukkit.entity.Player;

public class ArmorEquipEvent extends CoreEvent {
    private final Player player;
    private final Armor armor;

    public ArmorEquipEvent(final Player player, final Armor armor) {
        this.player = player;
        this.armor = armor;
    }

    public final Player getPlayer() {
        return this.player;
    }

    public Armor getArmor() {
        return this.armor;
    }
}
