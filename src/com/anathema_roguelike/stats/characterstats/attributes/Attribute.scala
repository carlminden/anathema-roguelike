package com.anathema_roguelike
package stats.characterstats.attributes

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.Stat.CharacterStat

abstract class Attribute(character: Character, var base: Int = 3) extends CharacterStat(character) {

  override def getAmount: Double = base

  def setScore(base: Int): Unit = {
    this.base = base
  }
}
