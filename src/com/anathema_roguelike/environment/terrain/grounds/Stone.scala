

package com.anathema_roguelike
package environment.terrain.grounds

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer

class Stone(location: Location) extends Ground(location, '.', 0, 0) {
  override def renderToFogOfWar(): Unit = {
    Game.getInstance.getMap.renderChar(DungeonLayer.LIT_FOG_OF_WAR_FOREGROUND, getX, getY, getRepresentation.getChar, Color.FOG_OF_WAR_GROUND)
  }
}
