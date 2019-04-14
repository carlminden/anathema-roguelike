package com.anathema_roguelike
package environment.features

import com.anathema_roguelike.environment.{Location, LocationProperty}
import com.anathema_roguelike.environment.features.Feature.Priority.Priority
import com.anathema_roguelike.main.display.VisualRepresentation

object Feature {

  object Priority extends Enumeration {
    type Priority = Value
    val LOW, DEFAULT, HIGH, DEBUG = Value
  }

}

abstract class Feature(
    location: Location,
    representation: VisualRepresentation,
    fogOfWarRepresentation: VisualRepresentation,
    foreground: Boolean,
    passable: Boolean,
    opacity: Double,
    damping: Double,
    renderPriority: Priority = Feature.Priority.DEFAULT)
  extends LocationProperty(location, representation, fogOfWarRepresentation, foreground, passable, opacity, damping) {

  def getRenderPriority: Priority = renderPriority
}