package com.anathema_roguelike
package entities.characters.foes.traits

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.Perk

abstract class Trait[T <: Perk]() extends PassthroughPerk[T] {
}