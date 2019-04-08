

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.{Shape, SinglePoint}
import com.anathema_roguelike.entities.characters.Character

class PointBlank (

val constraints: TargetConstraint[Character, Character]*) extends Range[Character](constraints:_*) {
  protected def getShape (character: Character): Shape = {
    new SinglePoint (character.getPosition)
  }
}
