package com.anathema_roguelike
package entities.characters.foes.traits

import com.anathema_roguelike.entities.characters.perks.{Buff, PassivePerk, PassthroughPerk}
import com.anathema_roguelike.stats.Stat.CharacterStat
import com.anathema_roguelike.stats.effects.{Calculation, Modifier}

import scala.reflect.runtime.universe._

class StatTrait[T <: CharacterStat : TypeTag](name: String, calculation: Calculation) extends PassthroughPerk[PassivePerk] {

  def getStat: Class[T] = typeTagToClass

  override protected def createPerk: PassivePerk = new PassivePerk(name) {
    override def getEffect: Option[Buff] = {
      new Buff(this, List(new Modifier[T](calculation)))
    }
  }
}