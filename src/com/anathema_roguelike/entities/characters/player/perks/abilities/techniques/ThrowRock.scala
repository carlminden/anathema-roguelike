package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost}
import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.ThrowingRange
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.entities.characters.stimuli.Sound
import com.anathema_roguelike.entities.items.miscellaneous.Rock
import com.anathema_roguelike.environment.Location

class ThrowRock() extends PassthroughPerk[TargetedPerk[Location]] with Ability {
  override protected def createPerk: TargetedPerk[Location] = {
    new TargetedPerk(
      "Throw Rock",
      new ThrowingRange[Location](new Rock, new LineOfSight(), new LineOfEffect[Location]()),
      new SingleTargeted[Location]()
    )
    {
      override protected def createAction: TargetedAction[Location] = {
        new TargetedAction[Location](getCharacter, EnergyCost.STANDARD(getCharacter), new ActionCosts()) {
          override def onTake(): Unit = {
            super.onTake()
            getTargets.foreach(t => t.generateStimulus(new Sound(100, getActor)))
          }
        }
      }
    }
  }
}