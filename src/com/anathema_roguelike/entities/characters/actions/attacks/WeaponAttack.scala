

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
      targets: Iterable[Character],
      additionalCosts: ActionCosts,
      targetEffects: TargetEffect[Character, _]*

    ) extends Attack[Character](
      attacker,
      targets,
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
