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
package environment.generation

import com.anathema_roguelike.main.utilities.position.Point

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class FeatureGroup[T <: DungeonFeature] extends Iterable[T]{
  private val features: ArrayBuffer[T] = ArrayBuffer()

  def +=(feature: T): Unit = features += feature
  def -=(feature: T): Unit = features -= feature

  def apply(i: Int): T = features(i)

  def intersections(point: Point): FeatureGroup[T] = {

    features.foldLeft(new FeatureGroup[T]) {
      (ret, feature) => {
        if(feature.intersects(point) && !ret.contains(feature)) {
          ret += feature
        }

        ret
      }
    }
  }

  def intersections(targetFeature: DungeonFeature): FeatureGroup[T] = {

    features.foldLeft(new FeatureGroup[T]) {
      (ret, feature) => {
        if(!ret.contains(feature) && (feature != targetFeature) && feature.intersects(targetFeature)) {
          ret += feature
        }

        ret
      }
    }
  }

  override def iterator: Iterator[T] = features.iterator
}