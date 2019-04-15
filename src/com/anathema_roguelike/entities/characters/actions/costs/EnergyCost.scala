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
