package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells

import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.perks.PerkChoice
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.main.utilities.Utils

import com.anathema_roguelike.entities.characters.Character

class SpellOrSpecialization(var spellLevel: Int, var casterClass: Class[_ <: PlayerClass])
  extends PerkChoice("Level " + spellLevel + " " + Utils.getName(casterClass) + " Spell") {

  override def getChoices(character: Character): Iterable[_ <: Perk] = {
    val choices = new SpellSpecializationChoice(spellLevel, casterClass).getChoices(character)

    choices ++ Spell.findSpells(spellLevel, casterClass).filter(s => !character.hasPerk(s.getClass))
  }
}