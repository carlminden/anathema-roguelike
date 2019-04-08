

package com.anathema_roguelike
package entities.characters.player.classes


import com.anathema_roguelike.entities.characters.perks.PerkGroup

abstract class PlayerClass(val firstLevel: PerkGroup, val levels: PerkGroup*) {

  private val perks = firstLevel +: levels.toList

  def getLevel(level: Int): PerkGroup = perks.get(level - 1)
}