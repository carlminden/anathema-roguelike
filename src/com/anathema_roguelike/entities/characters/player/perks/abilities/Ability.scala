

package com.anathema_roguelike
package entities.characters.player.perks.abilities

import com.anathema_roguelike.entities.characters.Character

trait Ability {
  def getCharacter: Character

  def getSpecializationLevel: Int = getCharacter.getSpecialization(getClass)
}
