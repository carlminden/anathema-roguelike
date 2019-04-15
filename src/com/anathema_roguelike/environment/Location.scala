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
package environment

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.stimuli.{Stimulus, StimulusEvent}
import com.anathema_roguelike.environment.features.Feature
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.Stat.LocationStat
import com.anathema_roguelike.stats.StatSet.LocationStats
import com.anathema_roguelike.stats.{HasStats, StatSet}
import com.google.common.collect.TreeMultiset
import com.google.common.eventbus.EventBus

import scala.collection.JavaConverters._

import scala.reflect.runtime.universe._

class Location(
    environment: Environment,
    position: Point,
    eventBus: EventBus,
    var terrain: Terrain,
    initialFeatures: Feature*)
  extends HasStats[Location, LocationStat] with Targetable {

  private val stats = new LocationStats(this)

  setTerrain(terrain)

  for (feature <- initialFeatures) {
    addFeature(feature)
  }

  private val features: TreeMultiset[Feature] = TreeMultiset.create((o1: Feature, o2: Feature) => o2.getRenderPriority.compareTo(o1.getRenderPriority))

  override def toString: String = terrain.getClass.getSimpleName + ": " + getX + ", " + getY

  override def getLocation: Location = this

  override def getPosition: Point = position

  override def getEnvironment: Environment = environment

  def getTerrain: LocationProperty = terrain

  def setTerrain(terrain: Terrain): Unit = {

    terrain.setLocation(this)
    this.terrain = terrain
  }

  def getFeatures: Iterable[Feature] = features.asScala

  def addFeature(feature: Feature): Unit = {
    features.add(feature)
    feature.setLocation(this)
  }

  def getAllEntities: Iterable[Entity] = getEnvironment.getEntitiesAt(this)

  def getEntities[T <: Entity : TypeTag]: Iterable[T] = getEnvironment.getEntitiesAt[T](this)

  def addEntity(entity: Entity): Unit = {
    getEnvironment.addEntity(entity, this)
  }

  def isPassable: Boolean = terrain.isPassable

  def render(): Unit = {
    terrain.render()

    features.forEach(feature => {
      feature.render()
    })
  }

  def generateStimulus(stimulus: Stimulus): Unit = {
    getEnvironment.getEventBus.post(new StimulusEvent(this, stimulus))
  }

  override def getStatSet: StatSet[Location, LocationStat] = stats
}
