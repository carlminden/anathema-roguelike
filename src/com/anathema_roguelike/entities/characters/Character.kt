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
package com.anathema_roguelike.entities.characters

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.LinkedList
import java.util.Optional
import java.util.stream.Collectors

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.actions.BasicMovementAction
import com.anathema_roguelike.entities.characters.actions.OpenDoorAction
import com.anathema_roguelike.entities.characters.actions.TakeStairsAction
import com.anathema_roguelike.entities.characters.actions.attacks.BasicAttack
import com.anathema_roguelike.entities.characters.actions.attacks.BasicAttackPerk
import com.anathema_roguelike.entities.characters.events.ResourceChangedEvent
import com.anathema_roguelike.entities.characters.events.TurnEndEvent
import com.anathema_roguelike.entities.characters.events.TurnStartEvent
import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.inventory.Inventory
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.perks.PerkSet
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecializationSet
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimuli
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent
import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.environment.features.Doorway
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.main.display.BufferMask
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.stats.HasStats
import com.anathema_roguelike.stats.StatSet
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.characterstats.CharacterStats
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import com.anathema_roguelike.stats.characterstats.resources.BoundedResource
import com.anathema_roguelike.stats.characterstats.resources.Resource
import com.anathema_roguelike.stats.characterstats.secondarystats.LightEmission
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.HasEffect
import com.google.common.base.Predicate
import com.google.common.eventbus.Subscribe

abstract class Character : Entity(), HasStats<Character, CharacterStat> {

    var faction: Int = 0
        protected set

    var level = 0
        private set

    var turn: Long = 0
        private set

    private val perks = PerkSet()
    private val abilitySpecializations = AbilitySpecializationSet()

    val inventory = Inventory(this)

    var isAlive = true
        private set

    open var facing = Direction.UP.toDouble()
    internal var currentVisibility: BufferMask? = null

    @PublishedApi internal val stats = CharacterStats(this, eventBus)
    val percievedStimuli = PerceivedStimuli(this)
    val lastSeenCharacterLocations = HashMap<Character, Location>()

    private val pendingActions = LinkedList<Action<Character>>()

    val primaryWeaponDamage: Int
        get() = inventory.getSlot<PrimaryWeapon>().equippedItem!!.weaponDamage

    val currentlyVisibleCharacters: Collection<Character>
        get() = environment.getEntities(Character::class.java).filter { c -> c.isVisibleTo(this) }

    abstract fun onDeath()

    protected abstract fun setNextPendingAction()

    init {
        BasicAttackPerk().grant(this)
    }

    open fun levelUp() {
        level++
    }

    override val statSet: StatSet<Character, CharacterStat>
    get() {
        return stats
    }

    fun <T : Perk> getPerks(superclass: Class<T>): Collection<T> {
        return perks.get(superclass)
    }

    fun <T : Perk> getPerks(superclass: Class<T>, predicate: Predicate<T>): Collection<T> {
        return perks.get(superclass, predicate)
    }

    fun addPerk(perk: Perk) {
        perks.add(perk)
    }

    fun removePerk(perk: Perk) {
        perks.remove(perk)
    }

    fun <P : Perk> hasPerk(perk: Class<P>): Boolean {
        return !getPerks(perk).isEmpty()
    }

    fun getSpecialization(ability: Class<out Ability>): Int {
        return abilitySpecializations.getSpecializationLevel(ability)
    }

    fun specialize(ability: Class<out Ability>, amount: Int) {
        abilitySpecializations.specialize(ability, amount)
    }

    fun basicAttack(target: Character) {
        addPendingAction(BasicAttack(this, target))
    }

    @Subscribe
    fun handleResourceChangedEvent(event: ResourceChangedEvent) {
        //TODO not sure if i need to do anything in this case but figure its a good thing to have an event for
    }

    override fun act() {
        computeVisibility()

        super.act()

        computeVisibility()

        lastSeenCharacterLocations.clear()
        currentlyVisibleCharacters.stream().forEach { c -> lastSeenCharacterLocations[c] = c.location }
    }

    override fun getNextAction(): Action<*> {

        eventBus.post(TurnStartEvent())

        if (!hasPendingActions()) {
            turn++

            setNextPendingAction()
        }

        eventBus.post(TurnEndEvent())

        return pendingActions.pop()
    }

    @Subscribe
    fun handleEnvironmentChangedEvent(e: EnvironmentChangedEvent) {
        e.reRegister(percievedStimuli)
    }

    fun move(direction: Int) {

        val location = environment.getLocation(Direction.offset(position, direction))

        if (!location.isPassable) {
            return
        } else if (location.terrain is Doorway && this is Player) {
            val door = location.terrain as Doorway

            addPendingAction(OpenDoorAction(this, door))
        } else {

            val characters = location.getEntities(Character::class.java)

            if (characters.size > 0) {
                val character = location.getEntities(Character::class.java).iterator().next()

                if (!Faction.friendly(character, this)) {
                    basicAttack(character)
                }
            } else {
                addPendingAction(BasicMovementAction(this, location))
            }
        }
    }

    fun kill() {
        isAlive = false
    }

    fun getResourceMax(resource: Class<BoundedResource>): Int {
        return getStat<BoundedResource>(resource).maximum
    }

    inline fun <reified T : Attribute> setAttributeScore(amount: Int) {
        stats.getStat<T>().setScore(amount)
    }

    inline fun setAttributeScore(attribute : Class<out Attribute>, amount: Int) {
        stats.getStat(attribute).setScore(amount)
    }

    inline fun <reified R : Resource> setResource(initiator: Optional<Character>, source: Optional<HasEffect<out Effect<Character, *>>>, amount: Int) {
        stats.getStat<R>().set(initiator, source, amount)
    }

    inline fun <reified R : Resource> modifyResource(initiator: Optional<Character>, source: Optional<HasEffect<out Effect<Character, *>>>, amount: Int) {
        modifyResource(initiator, source, R::class.java, amount)
    }

    inline fun <R : Resource> modifyResource(initiator: Optional<Character>, source: Optional<HasEffect<out Effect<Character, *>>>, resource :Class<R>, amount: Int) {
        val resource = stats.getStat(resource)
        resource.modify(initiator, source, amount)
    }

    fun takeStairs(direction: Int) {

        if (environment.getLocation(position).terrain is Stairs) {
            val stairs = environment.getLocation(position).terrain as Stairs
            if (stairs.direction == direction) {
                addPendingAction(TakeStairsAction(this, stairs))
            }
        }
    }

    fun getVisibilityOf(character: Character?): VisibilityLevel {
        if (character == null) {
            return VisibilityLevel.IMPERCEPTIBLE
        }

        if (character === this) {
            return VisibilityLevel.EXPOSED
        }

        val visibility = character.getStat(Visibility::class.java).visibilityLevel

        if (getCurrentVisibility()!!.get(character.x, character.y)) {
            if (position.isAdjacentTo(character.position)) {
                return VisibilityLevel.EXPOSED
            } else if (position.isAdjacentTo(character) && visibility.ordinal < 4) {
                return VisibilityLevel.VISIBLE
            } else if (position.distance(character) <= 2 && (visibility == VisibilityLevel.CONCEALED || visibility == VisibilityLevel.PARTIALLYCONCEALED)) {
                return VisibilityLevel.VISIBLE
            }

            return character.getStat(Visibility::class.java).visibilityLevel
        } else {
            return VisibilityLevel.IMPERCEPTIBLE
        }
    }

    override fun isVisibleTo(character: Character): Boolean {
        return character.getVisibilityOf(this).ordinal >= 3
    }

    fun getCurrentVisibility(): BufferMask? {
        if (currentVisibility == null) {
            computeVisibility()
        }

        return currentVisibility
    }

    fun hasLineOfSightTo(location: HasLocation): Boolean {
        return getCurrentVisibility()!!.get(location.x, location.y)
    }

    fun hasLineOfEffectTo(location: HasLocation): Boolean {
        return getLocation().environment.lineOfEffectBetween(getLocation(), location)
    }

    fun computeVisibility() {
        environment.lightLevels.recomputeLightLevels()
        currentVisibility = environment.litFOVProcessor.computeLitFOVMask(this)
    }

    override fun getLightEmission(): Double {
        return getStatAmount(LightEmission::class.java)
    }

    fun addPendingAction(pendingAction: Action<Character>) {
        this.pendingActions.add(pendingAction)
    }

    fun addPendingAction(pendingAction: Optional<Action<Character>>) {
        pendingAction.ifPresent { pa -> this.pendingActions.add(pa) }
    }

    fun addPendingActions(vararg pendingActions: Action<Character>) {
        this.pendingActions.addAll(Arrays.asList(*pendingActions))
    }

    fun addPendingActions(pendingActions: Collection<Action<Character>>) {
        this.pendingActions.addAll(pendingActions)
    }

    fun hasPendingActions(): Boolean {
        return !this.pendingActions.isEmpty()
    }

}
