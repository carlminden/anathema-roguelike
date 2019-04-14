package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.{LineOfEffect, LineOfSight, TargetConstraint}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.{Circle, Shape, Square}
import com.anathema_roguelike.stats.itemstats.WeaponRange
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.FixedCalculation

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class PrimaryWeaponRange[T <: Targetable : TypeTag : ClassTag](constraints: TargetConstraint[T, Character]*) extends Range[T](constraints:_*) {

  addConstraint(new LineOfSight[T])
  addConstraint(new LineOfEffect[T])

  protected def getShape (character: Character): Shape = {
    val range: Double = character.getInventory.getSlot(classOf[PrimaryWeapon]).getEquippedItem.get.getStatAmount[WeaponRange]

    if (range == 1) {
      new Square(character, new FixedCalculation (1))
    } else {
      new Circle(character, new FixedCalculation (range))
    }
  }
}
