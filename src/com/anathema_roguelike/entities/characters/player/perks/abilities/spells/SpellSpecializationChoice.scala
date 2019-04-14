package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells

import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecializationChoice
import com.anathema_roguelike.entities.characters.Character

class SpellSpecializationChoice(var spellLevel: Int, var casterClass: Class[_ <: PlayerClass])
  extends AbilitySpecializationChoice[Spell[_]] {

  override def validAbility(character: Character, a: Ability): Boolean = {
    super.validAbility(character, a) && (a match {
      case spell: Spell[_] => spell.getCasterClass == casterClass && spell.getSpellLevel == spellLevel
      case _ => false
    })
  }
}