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
package entities.items.armor

import com.anathema_roguelike.entities.items.ItemProperty

abstract class ArmorProperty(name: String, weight: Double) extends ItemProperty[Armor](name, weight) {
  private var concealment = 0.0
  private var veil = 0.0
  private var attenuation = 0.0

  protected def getConcealment: Double = concealment

  protected def getVeil: Double = veil

  protected def getAttenuation: Double = attenuation

  def setAttenuation(attenuation: Double): Unit = this.attenuation = attenuation

  def setConcealment(concealment: Double): Unit = this.concealment = concealment

  def setVeil(veil: Double): Unit = this.veil = veil
}