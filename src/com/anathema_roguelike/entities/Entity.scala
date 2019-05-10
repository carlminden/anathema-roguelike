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
package entities

import com.anathema_roguelike.actors.{Actor, Duration, Energy}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.environment.{HasLocation, Location}
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.{Renderable, VisualRepresentation}
import com.google.common.eventbus.EventBus

abstract class Entity(private var location: HasLocation) extends Renderable with Targetable with Actor {

  private val eventBus = new EventBus

  Game.getInstance.getEventBus.register(this)
  eventBus.register(this)

  getLocation.addEntity(this)

  protected def renderThis(): Unit

  def setLocation(location: HasLocation): Unit = {
    this.location = location.getLocation
  }

  override def getLocation: Location = location.getLocation

  //	public VisualRepresentation getVisualRepresentation() {
  //	return new VisualRepresentation('X', Color.ERROR);
  //}

  def getVisualRepresentation: VisualRepresentation

  override final def render(): Unit = {
    if (isVisibleTo(Game.getInstance.getState.getPlayer)) renderThis()
  }

  def getLightEmission = 0.0

  def getEventBus: EventBus = eventBus

  def postEvent(obj: Any): Unit = {
    eventBus.post(obj)
  }

  override def getDuration: Duration = Duration.PERMANENT
}

