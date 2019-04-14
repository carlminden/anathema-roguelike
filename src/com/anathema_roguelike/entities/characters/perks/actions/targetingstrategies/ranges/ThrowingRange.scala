package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.characterstats.attributes.Strength
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery
import com.anathema_roguelike.stats.itemstats.Weight
import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character

class ThrowingRange[T <: Targetable : TypeTag](var item: Item, val constraints: TargetConstraint[T, Character]*)
  extends CircularRange[T] (constraints:_*) {

  protected def getRadius (character: Character): Double = {
    val throwingMastery: Double = character.getStatAmount[ThrowingWeaponMastery]
    val strength: Double = character.getStatAmount[Strength]
    val weight: Double = item.getStatAmount[Weight]

    (2 + throwingMastery) * (1 + (2 * strength - weight) / 30)
  }
}