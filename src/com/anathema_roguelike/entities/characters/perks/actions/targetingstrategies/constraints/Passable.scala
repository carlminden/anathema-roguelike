package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.constraints

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character

class Passable extends TargetConstraint[Location, Character] {
  override def apply(target: Location, character: Character): Boolean = target.isPassable
}