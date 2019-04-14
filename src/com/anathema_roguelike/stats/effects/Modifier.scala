package com.anathema_roguelike
package stats.effects

import com.anathema_roguelike.stats.Stat
import scala.reflect.runtime.universe._

class Modifier[T <: Stat[_] : TypeTag](calculation: Calculation) {

  def getAffectedStat: Class[T] = typeTagToClass

  def getAdditiveAmount: Double = {
    calculation match {
      case _: AdditiveCalculation => calculation()
      case _ => 0
    }
  }

  def getMultiplier: Double = {
    calculation match {
      case _: MultiplicativeCalculation => calculation()
      case _ => 1
    }
  }
}
