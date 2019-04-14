package com.anathema_roguelike
package entities.characters.foes.traits

import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation
import scala.reflect.runtime.universe._

class Deficient[T <: Attribute : TypeTag]
  extends StatTrait[T]("Deficient: " + Utils.getName(typeTagToClass[T]), MultiplicativeCalculation.fixed(0.25)) {
}