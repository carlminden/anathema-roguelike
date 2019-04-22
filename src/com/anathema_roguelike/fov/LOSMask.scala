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

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.BufferMask
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Vision
import squidpony.squidgrid.FOV
import com.anathema_roguelike.entities.characters.Character

class LOSMask(width: Int, height: Int, resistances: Array[Array[Double]]) extends FOVProcessor(width, height, resistances) {
  private val mask = new BufferMask(width, height)

  protected def visit(entity: Entity, x: Int, y: Int, vision: Double): Unit = mask.set(x, y, vision > 0)

  def getFOVMask(character: Character): BufferMask = {
    val visionDistance = character.getStatAmount[Vision].asInstanceOf[Int]

    val angle = character match {
      case _ : Player => 360
      case _ => visionDistance * 10
    }

    doFOV(character, visionDistance, character.getFacing, angle, new FOV(FOV.RIPPLE_TIGHT))
    mask
  }
}