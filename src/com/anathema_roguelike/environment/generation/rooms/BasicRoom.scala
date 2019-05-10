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
package environment.generation.rooms

import com.anathema_roguelike.entities.characters.foes.corruptions.Thrall
import com.anathema_roguelike.entities.characters.foes.roles.Brawler
import com.anathema_roguelike.entities.characters.foes.species.generic.Orc
import com.anathema_roguelike.environment.{Environment, EnvironmentFactory}
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.utilities.position.Point

class BasicRoom(depth: Int, averageWidth: Int, averageHeight: Int) extends Room(depth, averageWidth, averageHeight) {
  def generateEncounter(factory: EnvironmentFactory): Unit = {

    (0 until 5).foreach(i => {
      val x = Game.getInstance.getRandom.nextInt(getWidth - 2) + getX + 1
      val y = Game.getInstance.getRandom.nextInt(getHeight - 2) + getY + 1

      factory.addEntity((l) => new Orc(l, new Brawler, new Thrall), Point(x, y))
    })
  }

  override def place(factory: EnvironmentFactory): Unit = {
    super.place(factory)
    //generator.getLevel().addEntity(new Brazier(), new Point(getX() + 1, getY() + 1));
    //generator.getLevel().addEntity(new Brazier(), new Point(getX() + getWidth() - 2, getY() + 1));
    //generator.getLevel().addEntity(new Brazier(), new Point(getX() + 1, getY() + getHeight() - 2));
    //generator.getLevel().addEntity(new Brazier(), new Point(getX() + getWidth() - 2, getY() + getHeight() - 2));
  }
}