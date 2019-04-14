package com.anathema_roguelike
package entities.characters.actions.attacks

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetEffect, Targetable}

import scala.collection.mutable.ListBuffer

abstract class Attack[TargetType <: Targetable](
      attacker: Character,
      energyCost: EnergyCost,
      additionalCosts: ActionCosts,
      private val effects: TargetEffect[TargetType, _]*
    ) extends TargetedAction[TargetType](attacker, energyCost, additionalCosts, effects:_*) {

  private val targetEffects = ListBuffer() ++ effects

  def getAttacker: Character = attacker

  def getTargetEffects = targetEffects

  def addTargetEffect(effect: TargetEffect[TargetType, _]) = {
    targetEffects :+ effect
  }
}
