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
package actors

import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.main.Game

trait Actor {

  private val energy = new Energy

  Game.getInstance.getState.registerActor(this)

  def getDuration: Duration

  def getNextAction: Option[Action[_]]

  def getDefaultAction: Action[Actor] = new Action[Actor](this, EnergyCost.STANDARD(this)) {
    override protected def onTake(): Unit = {

    }
  }

  def getEnergy: Energy = energy

  def energize(): Unit = {
    getEnergy.energize()
  }

  def getEnergyLevel: Double = getEnergy.getEnergyLevel

  def act(): Unit = {
    getNextAction.getOrElse(getDefaultAction).take()
  }
}
