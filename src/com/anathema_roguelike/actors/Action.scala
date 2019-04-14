package com.anathema_roguelike
package actors

import com.anathema_roguelike.entities.characters.actions.costs.{ActionCost, ActionCosts, EnergyCost}

abstract class Action[T <: Actor](actor: T, energyCost: EnergyCost, additionalCosts: ActionCost*) {

  private val costs = new ActionCosts(additionalCosts :+ energyCost:_*)

  protected def onTake(): Unit

  def take(): Unit = {
    getBeforeCosts.foreach(c => c.pay())
    onTake()
    getAfterCosts.foreach(c => c.pay())
  }

  def getActor: T = actor

  def getBeforeCosts: Iterable[ActionCost] = costs.getBeforeCosts

  def getAfterCosts: Iterable[ActionCost] = costs.getAfterCosts

  def addCost(cost: ActionCost): Unit = {
    costs.add(cost)
  }
}
