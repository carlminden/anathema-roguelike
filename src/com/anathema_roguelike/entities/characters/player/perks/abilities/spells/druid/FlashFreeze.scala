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

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.LongRange
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.stats.Stat
import com.anathema_roguelike.stats.effects.Effect

class FlashFreeze() extends Spell[TargetedPerk[Targetable]](3, classOf[Druid]) {
  override protected def createPerk: TargetedPerk[Targetable] = {
    new TargetedPerk[Targetable]("Flash Freeze", new LongRange[Targetable], new SingleTargeted[Targetable]) {
      override protected def createAction: TargetedAction[Targetable] = {
        new TargetedAction(
          getCharacter,
          EnergyCost.STANDARD(getCharacter),
          new ActionCosts,
          new TargetEffect[Targetable, Stat[Targetable]]("Flash Freeze") {
            override def getEffect: Option[Effect[Targetable, Stat[Targetable]]] = ??? // TODO Auto-generated method stub
          }
        )
      }
    }
  }
}