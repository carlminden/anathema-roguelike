

package com.anathema_roguelike
package entities.characters.actions.costs

import com.anathema_roguelike.stats.characterstats.resources.{Resource, ResourceModification}
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import com.anathema_roguelike.entities.characters.Character

import scala.reflect.runtime.universe._

class ResourceCost[T <: Resource : TypeTag](
    character: Character,
    resource: Class[T],
    var amount: Int) extends CharacterActionCost(character) with HasEffect[Effect[Character, T]] {

  override def pay(): Unit = {
    getEffect.foreach(e => e.applyTo(getCharacter))
  }

  override def getEffect: Option[Effect[Character, T]] = new ResourceModification[T](Option.empty, Option(this), amount)

  def getResource: Class[T] = resource

  def getAmount: Int = amount
}