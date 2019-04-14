

package com.anathema_roguelike
package entities.characters.actions.costs

import com.anathema_roguelike.actors.Actor

object EnergyCost {
  def VERY_QUICK(actor: Actor) = new EnergyCost(actor, 0.25)

  def QUICK(actor: Actor) = new EnergyCost(actor, 0.5)

  def STANDARD(actor: Actor) = new EnergyCost(actor, 1)

  def SLOW(actor: Actor) = new EnergyCost(actor, 1.5)

  def VERY_SLOW(actor: Actor) = new EnergyCost(actor, 2)

  def EXTREMELY_SLOW(actor: Actor) = new EnergyCost(actor, 4)
}

class EnergyCost(actor: Actor, var energy: Double) extends ActionCost(actor) {

  override def pay(): Unit = {
    getActor.getEnergy.use(energy)
  }
}
