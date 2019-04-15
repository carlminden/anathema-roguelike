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

import java.util.Optional

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost, StimulusCost}
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.stimuli.{Resonance, Sight, Sound}
import com.anathema_roguelike.stats.characterstats.attributes.Agility
import com.anathema_roguelike.stats.effects.{AdditiveArrayCalculation, Effect, Modifier}
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.Stat.CharacterStat

class Grace(source: Any) extends Spell[SelfTargetedPerk](1, classOf[Druid]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Grace") {
    override protected def createAction: TargetedAction[Character] = {


      val costs = new ActionCosts(
        new StimulusCost[Sight](getCharacter, classOf[Sight], () => 25.0),
        new StimulusCost[Sound](getCharacter, classOf[Sound], () => 50.0),
        new StimulusCost[Resonance](getCharacter, classOf[Resonance],
          new AdditiveArrayCalculation(() => getSpecializationLevel, 50.0, 70.0, 100.0, 140.0))
      )

      new TargetedAction[Character](getCharacter, EnergyCost.STANDARD(getCharacter), costs,
        new TargetEffect[Character, CharacterStat]("Grace") {

          override def getEffect: Option[Effect[Character, CharacterStat]] = {
            new Effect[Character, CharacterStat](this, List(
                new Modifier[Agility](new AdditiveArrayCalculation(() => getSpecializationLevel, 5.0, 7.0, 10.0, 14.0))
            ))
          }
        })
    }
  }
}

