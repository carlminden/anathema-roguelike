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
package entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{ConalAreaOfEffect, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.{LineOfEffect, LineOfSight}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.VeryShortRange
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class GustOfWind() extends Spell[GenericTargetedPerk[Targetable, Location]](2, classOf[Druid]) {
  override protected def createPerk: GenericTargetedPerk[Targetable, Location] = {
    new GenericTargetedPerk(
      "Gust of Wind",
      new VeryShortRange[Location](new LineOfSight[Location], new LineOfEffect[Location]),
      new ConalAreaOfEffect[Targetable, Location](() => if (getSpecializationLevel == 3) 4.0 else 3.0) {
        override protected def getOrigin: Location = getCharacter.getLocation
      }
    ) {
      override protected def createAction: TargetedAction[Targetable] = ???
    }
  }
}
