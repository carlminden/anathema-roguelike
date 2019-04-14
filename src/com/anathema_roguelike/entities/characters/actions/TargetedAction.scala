package com.anathema_roguelike
package entities.characters.actions


import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetEffect, Targetable}

class TargetedAction[TargetType <: Targetable](
    actor: Character,
    energyCost: EnergyCost,
    additionalCosts: ActionCosts,
    targetEffects: TargetEffect[TargetType, _]*
  ) extends CharacterAction(actor, energyCost, additionalCosts.getCosts.toSeq: _*) {

  var targets: Iterable[_ <: TargetType] = List()

  def getTargets: Iterable[_ <: TargetType] = targets
  def setTargets(targets: Iterable[_ <: TargetType]): Unit = this.targets = targets

  override protected def onTake(): Unit = {
    if (targets.isEmpty) throw new RuntimeException("Invalid Targeted CharacterAction, Targets not set")

    for (target <- targets) {
      for (te <- targetEffects) {
        if (te.getTargetType.isAssignableFrom(target.getClass)) {
          te.applyTo(target)
        }
      }
    }
  }
}
