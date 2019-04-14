package com.anathema_roguelike
package entities.characters.perks

import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import java.util
import com.anathema_roguelike.entities.characters.Character

abstract class PerkChoice(var title: String) extends Perk(title) {

  def getChoices(character: Character): Iterable[Perk]

  override def grant(character: Character): Unit = {
    val choice = new SelectionScreen[Perk](title, getChoices(character).toArray, cancellable = false).run
    choice.get.grant(character)
  }

  override def remove(character: Character) = throw new RuntimeException("Perk Choices should never be granted and therefore shouldnt be able to be removed")

  override def getCharacter = throw new RuntimeException("Perk Choices should never be granted and therefore will never have a Character set")
}