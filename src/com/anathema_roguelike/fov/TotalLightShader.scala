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

class TotalLightShader(lightLevels: LightLevels) extends LightLevelShader(lightLevels) {
  def compute(buffer: DisplayBuffer, x: Int, y: Int, string: Char, color: SColor, display: Boolean): DisplayCell = {
    val lightFromSource = getLightLevels.get(x, y)
    val objectLight = Color.factory.blend(Color.factory.dimmest(color), color, (lightFromSource / 2) + 0)

    new DisplayCell(string, objectLight, display)
  }
}