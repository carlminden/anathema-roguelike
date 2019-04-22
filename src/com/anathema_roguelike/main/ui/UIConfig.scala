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
package main.ui

import com.anathema_roguelike.main.utilities.position.Point

object UIConfig {
  final val DUNGEON_MAP_WIDTH: Int = 108
  final val DUNGEON_MAP_HEIGHT: Int = 57
  final val TERM_HEIGHT: Int = 60
  final val TERM_WIDTH: Int = 160
  final val SCREEN_START_X: Int = 1
  final val SCREEN_START_Y: Int = 1
  final val MAP_START_Y: Int = 0
  final val MAP_START_X: Int = 0
  final val DUNGEON_OFFSET: Point = Point(1, 3)
}
