package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap

abstract class Spell[T <: ActionPerk[_]](spellLevel: Int, casterClass: Class[_ <: PlayerClass]) extends PassthroughPerk[T] with Ability {
  def getSpellLevel: Int = spellLevel

  def getCasterClass: Class[_ <: PlayerClass] = casterClass
}

object Spell {

  private val spells: AutoClassToInstanceMap[Spell[_]] = new AutoClassToInstanceMap[Spell[_]]

  def findSpells(spellLevel: Int, casterClass: Class[_ <: PlayerClass]): Iterable[Spell[_]] = {
    spells.getValues.filter(s => (s.getSpellLevel == spellLevel) && (s.getCasterClass == casterClass))
  }
}
