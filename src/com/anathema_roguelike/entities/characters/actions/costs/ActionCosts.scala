package com.anathema_roguelike
package entities.characters.actions.costs

import scala.collection.mutable.ListBuffer


class ActionCosts(costs: ActionCost*) {

  private val costList = ListBuffer().addAll(costs)

  def getBeforeCosts: Iterable[ActionCost] = costList.filter(c => c.isBefore)

  def getAfterCosts: Iterable[ActionCost] = costList.filter(c => c.isAfter)

  def getCosts= costList

  def add(cost: ActionCost): Unit = {
    costList :+ cost
  }
}

