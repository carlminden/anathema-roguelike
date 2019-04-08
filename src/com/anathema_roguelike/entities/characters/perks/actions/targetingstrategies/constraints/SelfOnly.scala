

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.constraints
import com.anathema_roguelike.entities.characters.Character

class SelfOnly extends TargetConstraint[Character, Character] {
  override def apply(target: Character, character: Character): Boolean = {
    character == target
  }
}
