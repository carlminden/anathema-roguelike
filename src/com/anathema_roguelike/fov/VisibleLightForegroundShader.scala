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
package fov

import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.DisplayBuffer
import com.anathema_roguelike.main.display.DisplayCell
import squidpony.squidgrid.gui.gdx.SColor
import squidpony.squidmath.PerlinNoise

class VisibleLightForegroundShader(val lightLevels: LightLevels) extends LightLevelShader(lightLevels) {
  override def compute(buffer: DisplayBuffer, x: Int, y: Int, string: Char, color: SColor, display: Boolean): DisplayCell = {

    var light = Math.min(getLightLevels.get(x, y) + PerlinNoise.noise(x, y) * .15, 1.3)
    val cell = buffer.get(x, y)
    var objectLight = cell.getColor

    if(cell.getChar == '.') if(light > .3f) {
      light = LightLevels.anitmateLight(light, x, y)
      objectLight = Color.factory.blend(Color.factory.blend(objectLight, Color.BLACK, .8f), objectLight, (light) - .6)
    } else {
      objectLight = Color.factory.blend(Color.factory.blend(objectLight, Color.BLACK, .8f), objectLight, 0)
    }

    DisplayCell(cell.getChar, objectLight, display)
  }
}