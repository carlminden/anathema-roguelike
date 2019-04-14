package com.anathema_roguelike
package entities.characters.foes.traits

import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat
import com.anathema_roguelike.stats.effects.AdditiveCalculation
import scala.reflect.runtime.universe._

class Extraordinary[T <: SecondaryStat : TypeTag]()
  extends StatTrait[T]("Extraordinary: " + Utils.getName(typeTagToClass[T]), AdditiveCalculation.fixed(10.0)) {
}