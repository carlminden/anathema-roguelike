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
package entities.characters.actions.attacks

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.characterstats.resources.{CurrentHealth, Damage}
import com.anathema_roguelike.stats.characterstats.secondarystats.AttackSpeed
import com.anathema_roguelike.stats.effects.Effect


object WeaponAttack {
  private def getEnergyCost(character: Character) = new EnergyCost(character, character.getStatAmount[AttackSpeed])
}

abstract class WeaponAttack(
      attacker: Character,
      additionalCosts: ActionCosts,
      targetEffects: TargetEffect[Character, _]*

    ) extends Attack[Character](
      attacker,
      WeaponAttack.getEnergyCost(attacker),
      additionalCosts, targetEffects:_*) {

    addTargetEffect(getWeaponAttackEffect)


    def getWeaponAttackEffect: TargetEffect[Character, _ <: CharacterStat] = {
      new TargetEffect[Character, CurrentHealth](Utils.getName(this)) {
        override def getEffect: Option[Effect[Character, CurrentHealth]] = {
          new Damage[CurrentHealth](getAttacker, this, () => {
            getAttacker.getPrimaryWeaponDamage.toDouble
          })
        }
      }
    }
  }
