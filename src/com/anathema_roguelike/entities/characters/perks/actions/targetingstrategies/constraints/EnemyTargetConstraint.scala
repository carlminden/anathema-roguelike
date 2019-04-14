package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.constraints

import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.Character

class EnemyTargetConstraint extends TargetConstraint[Character, Character] {
  override def apply(target: Character, character: Character): Boolean = !Faction.friendly(target, character)
}