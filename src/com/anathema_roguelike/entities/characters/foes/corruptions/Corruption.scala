package com.anathema_roguelike
package entities.characters.foes.corruptions

import com.anathema_roguelike.entities.characters.foes.traits.Trait
import com.anathema_roguelike.entities.characters.perks.PerkGroup

abstract class Corruption(val traits: Trait[_]*) extends PerkGroup(traits:_*) {
}