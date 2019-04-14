package com.anathema_roguelike
package environment.terrain

import com.anathema_roguelike.environment.{Location, LocationProperty}
import com.anathema_roguelike.main.display.VisualRepresentation

abstract class Terrain(
    location: Location,
    representation: VisualRepresentation,
    foreground: Boolean,
    passable: Boolean,
    opacity: Double,
    damping: Double)
  extends LocationProperty(location, representation, representation, foreground, passable, opacity, damping) {
}