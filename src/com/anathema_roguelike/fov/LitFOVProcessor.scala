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
package com.anathema_roguelike.fov

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.BufferMask
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Vision
import squidpony.squidgrid.FOV

object LitFOVProcessor {
  def lightFromCharactersPerspective(character: Character, visionDistance: Double, lightSource: Point, lightLevels: LightLevels): Double = {

    val directionToLight = Direction.approximationOf(character.getPosition, lightSource)
    var lightFromSource = lightLevels.get(lightSource, directionToLight) + .3

    if(lightFromSource > 0) {
      val distanceToCharacter = character.getPosition.squareDistance(lightSource)
      if(distanceToCharacter != 0) lightFromSource *= Math.min(1, visionDistance / (distanceToCharacter))
    }

    lightFromSource
  }
}

class LitFOVProcessor(width: Int, height: Int, resistances: Array[Array[Double]], var lightLevels: LightLevels)
  extends FOVProcessor(width, height, resistances) {

  private var mask: BufferMask = _
  private var character: Character = _
  private var visionDistance = 0

  override protected def visit(entity: Entity, x: Int, y: Int, light: Double): Unit = {
    val lightFromSource = LitFOVProcessor.lightFromCharactersPerspective(character, visionDistance, Point(x, y), lightLevels)
    mask.set(x, y, lightFromSource > .2 && light > 0)
  }

  def computeLitFOVMask(character: Character): BufferMask = {
    this.character = character
    this.visionDistance = character.getStatAmount(classOf[Vision]).toInt + 2

    mask = new BufferMask(width, height)

    val angle = character match {
      case _: Player => 360
      case _ => visionDistance * 20
    }

    doFOV(character, visionDistance, character.getFacing, angle, new FOV(FOV.RIPPLE_TIGHT))

    mask
  }
}