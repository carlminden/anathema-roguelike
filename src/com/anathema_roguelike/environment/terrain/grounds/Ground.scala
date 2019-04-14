package com.anathema_roguelike
package environment.terrain.grounds

import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.VisualRepresentation

abstract class Ground(location: Location, representation: VisualRepresentation, opacity: Double, damping: Double)
  extends Terrain(location, representation, true, true, opacity, damping) {
}
