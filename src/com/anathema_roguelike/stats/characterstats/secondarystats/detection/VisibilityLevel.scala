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
package stats.characterstats.secondarystats.detection

import com.anathema_roguelike.main.display.Color
import squidpony.squidgrid.gui.gdx.SColor

object VisibilityLevel {
  case object IMPERCEPTIBLE extends VisibilityLevel("Imperceptible", Color.WHITE, 0)
  case object CONCEALED extends VisibilityLevel("Concealed", Color.WHITE, 1)
  case object PARTIALLYCONCEALED extends VisibilityLevel("Partially Concealed", Color.YELLOW, 2)
  case object VISIBLE extends VisibilityLevel("Visible", Color.RED, 3)
  case object EXPOSED extends VisibilityLevel("Exposed", Color.RED, 4)
}

abstract class VisibilityLevel(var name: String, var color: SColor, val value: Int) { }