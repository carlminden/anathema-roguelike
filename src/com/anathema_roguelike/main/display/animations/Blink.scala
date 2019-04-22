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
package main.display.animations

import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SColor

class Blink(representation: VisualRepresentation, position: Point, duration: Float) extends PersistentAnimation(position, duration) {
  override protected def update(percent: Float): Unit = {

    val color = if(percent < .5) {
      Color.factory.blend(Color.BLACK, representation.color, percent * 2)
    } else {
      Color.factory.blend(representation.color, Color.BLACK, (percent - .5) * 2)
    }

    renderChar(getPosition, representation.char, color)
  }
}