package com.anathema_roguelike
package entities

import com.anathema_roguelike.actors.{Actor, Duration, Energy}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.{Renderable, VisualRepresentation}
import com.google.common.eventbus.EventBus

abstract class Entity(var location: Location) extends Renderable with Targetable with Actor {

  Game.getInstance.getEventBus.register(this)
  eventBus.register(this)
  Game.getInstance.getState.registerActor(this)

  location.getEnvironment.addEntity(this)

  private val energy = new Energy
  private val eventBus = new EventBus

  protected def renderThis(): Unit

  def setLocation(location: Location): Unit = {
    this.location = location
  }

  override def getLocation: Location = location

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

  override def getDuration: Duration = Duration.permanent

  override def getEnergy: Energy = energy
}

