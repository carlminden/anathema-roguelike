package com.anathema_roguelike
package actors

import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost

trait Actor {
  def getDuration: Duration

  def getEnergy: Energy

  def getNextAction: Option[Action[_]]

  def getDefaultAction: Action[Actor] = new Action[Actor](this, EnergyCost.STANDARD(this)) {
    override protected def onTake(): Unit = {

    }
  }

  def energize(): Unit = {
    getEnergy.energize()
  }

  def getEnergyLevel: Double = getEnergy.getEnergyLevel

  def act(): Unit = {
    getNextAction.getOrElse(getDefaultAction).take()
  }
}
