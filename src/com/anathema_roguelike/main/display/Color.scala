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
package main.display

import com.google.common.collect.HashBasedTable
import squidpony.squidgrid.gui.gdx.SColor
import squidpony.squidgrid.gui.gdx.SColorFactory

object Color {
  val factory = new SColorFactory
  val RED: SColor = SColor.RED
  val YELLOW: SColor = SColor.YELLOW
  val DARK_GRAY: SColor = factory.dim(factory.dimmer(SColor.GRAY))
  val GRAY: SColor = SColor.GRAY
  val GREEN: SColor = SColor.KELLY_GREEN
  val BROWN: SColor = SColor.AMBER_DYE
  val ABILITY: SColor = factory.light(SColor.SAFETY_ORANGE)
  val ERROR: SColor = SColor.AMETHYST
  val WHITE: SColor = SColor.WHITE
  val BLACK: SColor = SColor.BLACK
  val FAILURE: SColor = SColor.CARMINE
  val LIGHT_BROWN: SColor = factory.lighter(SColor.BROWN)
  val LIGHT_RED: SColor = factory.lighter(SColor.RED)
  val DARK_GREEN: SColor = SColor.ISLAMIC_GREEN
  val CRIT: SColor = SColor.YELLOW
  val MANA: SColor = SColor.BLUE
  val SPIRIT: SColor = SColor.TURQUOISE
  val INSTRUCTIONS: SColor = SColor.GOLDEN
  val TORCHLIGHT: SColor = factory.lightest(SColor.LEMON)
  val FOG_OF_WAR_GROUND = new SColor(0x2534A6)
  val NO_LIGHT_BACKGROUND = new SColor(0x080913)
  val FULL_LIGHT_BACKGROUND = new SColor(0x171E4E)
  val NO_LIGHT_PLAYER = new SColor(0x4F4F4F)
  val UNAWARE = new SColor(64, 127, 127)
  val ALERTED = new SColor(128, 125, 21)
  val DETECTED = new SColor(85, 0, 0)
  val ENCHANTED_ITEM: SColor = SColor.YELLOW
  val RESONANCE = new SColor(0xEB42F4)
  val opacityCache: HashBasedTable[SColor, Float, SColor] = HashBasedTable.create[SColor, Float, SColor]

  def opacity(color: SColor, opacity: Float): SColor = {
    val cached = opacityCache.get(color, opacity)
    if(cached != null) {
      cached
    } else {
      val temp = new SColor(color)
      temp.set(color.r, color.g, color.b, opacity)
      opacityCache.put(color, opacity, temp)
      temp
    }
  }
}