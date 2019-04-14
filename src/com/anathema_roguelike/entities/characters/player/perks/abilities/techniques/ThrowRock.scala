/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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