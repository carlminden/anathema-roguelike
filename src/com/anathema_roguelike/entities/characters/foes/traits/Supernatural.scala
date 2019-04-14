package com.anathema_roguelike
package entities.characters.foes.traits

import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation
import scala.reflect.runtime.universe._

class Supernatural[T <: Attribute : TypeTag]()
  extends StatTrait[T]("Supernatural: " + Utils.getName(typeTagToClass[T]), MultiplicativeCalculation.fixed(1.75)) {
}