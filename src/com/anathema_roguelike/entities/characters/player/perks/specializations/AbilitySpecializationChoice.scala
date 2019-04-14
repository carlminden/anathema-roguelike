package com.anathema_roguelike
package entities.characters.player.perks.specializations

import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.perks.PerkChoice
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import scala.reflect.runtime.universe._

import com.anathema_roguelike.entities.characters.Character

class AbilitySpecializationChoice[T <: Ability : TypeTag] extends PerkChoice("Choose an Ability to specialize into") {
  def getChoices(character: Character): Iterable[_ <: Perk] = {

    val learnedAbilities = character.getPerks[Perk].filterByType[T]

    learnedAbilities.filter(a => validAbility(character, a)).map(a => new AbilitySpecialization(a.getClass))
  }

  def validAbility(character: Character, a: Ability): Boolean = {
    typeTagToClass[T].isAssignableFrom(a.getClass) && character.getSpecialization(a.getClass) < 3
  }
}