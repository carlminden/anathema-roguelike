package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies

import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.stream.Collectors

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.environment.Environment
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.Utils

abstract class TargetFilter<T : Targetable, A> @SafeVarargs
constructor(protected val targetType: Class<T>, vararg constraints: TargetConstraint<T, A>) {
    private val constraints: ArrayList<TargetConstraint<T, A>>

    init {
        this.constraints = ArrayList(Arrays.asList(*constraints))
    }

    abstract fun getTargets(arg: A): Collection<T>

    protected fun getTargetsInShape(shape: Shape, environment: Environment, arg: A): Collection<T> {
        val targets = HashSet<T>()

        shape.getLocations(environment).stream().forEach { l -> targets.addAll(getTargetsAt(l, arg)) }

        return targets
    }

    private fun getTargetsAt(location: Location, arg: A): Collection<T> {
        val targets = HashSet<Targetable>()

        targets.add(location)
        targets.addAll(location.entities)

        var ret = Utils.filterBySubclass(targets, targetType)

        ret = ret.filter { t -> constraints.stream().allMatch { c -> c.apply(t, arg) } }

        return ret
    }

    fun addConstraint(constraint: TargetConstraint<T, A>) {
        constraints.add(constraint)
    }
}
