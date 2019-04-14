

package com.anathema_roguelike
package stats

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.environment.Location

object Stat {
  type CharacterStat = Stat[Character]
  type LocationStat = Stat[Location]
}

abstract class Stat[T](var obj: T) {
  def getObject: T = obj

  def getAmount: Double
}
