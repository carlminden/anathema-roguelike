package com.anathema_roguelike
package entities.events

import com.anathema_roguelike.environment.Environment

class EnvironmentChangedEvent(var oldEnvironment: Option[Environment], var newEnvironment: Environment) {
  def getNewEnvironment: Environment = newEnvironment

  def getOldEnvironment: Option[Environment] = oldEnvironment

  def reRegister(o: Any): Unit = {
    getOldEnvironment.foreach(old => old.getEventBus.unregister(o))
    getNewEnvironment.getEventBus.register(o)
  }
}
