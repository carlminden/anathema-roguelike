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
package main

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

object Config {
  val DUNGEON_WIDTH = 108
  val DUNGEON_HEIGHT = 57
  val DUNGEON_DEPTH = 1
  val DEBUG = true

  val defaults = new Properties
  defaults.put("font", "Lucida Console")
  defaults.put("fontsize", "20")

  private val configFile = new Properties(defaults)
  configFile.load(new FileInputStream("res/config.properties"))

  def getString(key: String): String = configFile.getProperty(key)

  def getInt(key: String): Int = configFile.getProperty(key).toInt
}