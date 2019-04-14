/** *****************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************//*******************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************/

package com.anathema_roguelike
package stats.characterstats.secondarystats.detection

import com.anathema_roguelike.stats.characterstats.resources.RecentMotion
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Concealment
import com.anathema_roguelike.entities.characters.Character

class Visibility(character: Character) extends SecondaryStat(character) {

  override def getAmount: Double = {

    val concealment = getObject.getStatAmount[Concealment]
    val motion = getObject.getStatAmount[RecentMotion]
    val light = getObject.getEnvironment.getLightLevels.get(getObject.getPosition)

    25 + Math.pow(20 * light, 1.75) - concealment + motion
  }

  def getVisibilityLevel: VisibilityLevel = {
    getAmount match {
      case s if(s < 25) => VisibilityLevel.IMPERCEPTIBLE
      case s if(s < 50) => VisibilityLevel.CONCEALED
      case s if(s < 75) => VisibilityLevel.PARTIALLYCONCEALED
      case s if(s < 100) => VisibilityLevel.VISIBLE
      case _ => VisibilityLevel.EXPOSED
    }
  }
}