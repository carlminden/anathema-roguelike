

package com.anathema_roguelike
package environment.terrain.grounds

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Direction.Direction2

class Stairs(location: Location, direction: Direction2) extends Ground(location, if (direction == Direction.UP) '<' else '>', 0, 0) {
  def getDirection: Direction2 = direction
}
