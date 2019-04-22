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
package entities.characters.actions

import com.anathema_roguelike.entities.characters.actions.costs.ActionCost
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.entities.characters.actions.costs.StimulusCost
import com.anathema_roguelike.entities.characters.events.MoveEvent
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.entities.characters.stimuli.Sight
import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility

class MoveAction(character: Character, energyCost: EnergyCost, location: HasLocation, costs: ActionCost*)
  extends CharacterAction(character, energyCost, costs:_*) with HasLocation {

    addCost(new StimulusCost[Sight](character, () => character.getStatAmount[Visibility]))
    addCost(new StimulusCost[Sight](character, () => character.getStatAmount[Visibility], location, true))

    override protected def onTake(): Unit = {
      getActor.getEnvironment.getEventBus.post(new MoveEvent(getActor, location))
      getEnvironment.moveEntityTo(getActor, location)

      if(getActor.isInstanceOf[Player]) {
        System.out.println("Player moving to: " + location)
      }
    }

    override def getLocation: Location = location.getLocation
}