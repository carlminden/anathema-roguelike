package com.anathema_roguelike.entities.items.miscellaneous

import java.util.Optional

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.stats.effects.AdditiveCalculation
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.Modifier
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.itemstats.Weight

class Rock : Item() {
    init {

        applyEffect(Optional.of(Effect(null,
                Modifier(Weight::class.java, AdditiveCalculation.fixed(1.0))
        )))
    }

    override fun getVisualRepresentation(): VisualRepresentation {
        return VisualRepresentation('*', Color.DARK_GRAY)
    }
}
