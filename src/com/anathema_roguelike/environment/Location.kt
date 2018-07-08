/*******************************************************************************
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.environment

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.stimuli.Stimulus
import com.anathema_roguelike.entities.characters.stimuli.StimulusEvent
import com.anathema_roguelike.environment.features.Feature
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.HasStats
import com.anathema_roguelike.stats.StatSet
import com.anathema_roguelike.stats.locationstats.LocationStat
import com.anathema_roguelike.stats.locationstats.LocationStatSet
import com.google.common.collect.TreeMultiset
import com.google.common.eventbus.EventBus

class Location(private val environment: Environment, internal var position: Point, eventBus: EventBus, terrain: Terrain, vararg features: Feature) : HasStats<Location, LocationStat>, Targetable {
    private val stats: LocationStatSet
    var terrain: Terrain? = null
        set(terrain) {
            terrain!!.location = this
            field = terrain
        }

    private val features = TreeMultiset.create<Feature> { o1, o2 ->
        o2.renderPriority.compareTo(o1.renderPriority
        )
    }

    val entities: Collection<Entity>
        get() = getEnvironment().getEntitiesAt(this)

    val isPassable: Boolean
        get() = terrain!!.isPassable

    override val statSet: StatSet<Location, LocationStat>
        get() = stats

    init {

        stats = LocationStatSet(this, eventBus)

        this.terrain = terrain

        for (feature in features) {
            addFeature(feature)
        }
    }

    override fun toString(): String {
        return terrain?.javaClass?.simpleName + ": " + x + ", " + y
    }

    override fun getLocation(): Location {
        return this
    }

    override fun getPosition(): Point {
        return position
    }

    override fun getEnvironment(): Environment {
        return environment
    }

    fun getTerrain(): LocationProperty? {
        return terrain
    }

    fun getFeatures(): Collection<Feature> {
        return features
    }

    fun addFeature(feature: Feature) {
        features.add(feature)
        feature.location = this
    }

    fun <T : Entity> getEntities(cls: Class<T>): Collection<T> {
        return getEnvironment().getEntitiesAt(this, cls)
    }

    fun addEntity(entity: Entity) {
        getEnvironment().addEntity(entity, this)
    }

    fun render() {
        terrain!!.render()

        for (feature in features) {
            feature.render()
        }
    }

    fun generateStimulus(stimulus: Stimulus) {
        getEnvironment().eventBus.post(StimulusEvent(this, stimulus))
    }
}
