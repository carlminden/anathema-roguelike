

package com.anathema_roguelike
package entities.characters.actions.costs

import com.anathema_roguelike.entities.characters.Character

abstract class CharacterActionCost(character: Character, after: Boolean = false) extends ActionCost(character, after) {

  def getCharacter: Character = character
}
