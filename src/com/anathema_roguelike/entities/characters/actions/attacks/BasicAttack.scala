package com.anathema_roguelike
package entities.characters.actions.attacks

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, ResourceCost, StimulusCost}
import com.anathema_roguelike.entities.characters.stimuli.AttenuatedSound
import com.anathema_roguelike.stats.characterstats.resources.{CurrentEndurance, RecentMotion}

class BasicAttack(attacker: Character) extends WeaponAttack(attacker, new ActionCosts()) {
    addCost(new ResourceCost(attacker, classOf[RecentMotion], 50))
    addCost(new StimulusCost(attacker, classOf[AttenuatedSound],  () => 30.0))
    addCost(new ResourceCost(attacker, classOf[CurrentEndurance], -30))
}
