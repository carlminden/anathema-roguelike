package com.anathema_roguelike.main.utilities

import kotlin.reflect.KClass
import java.util.*

class AutoKClassToInstanceMap<T> constructor(val classToInstanceMap : HashMap<KClass<*>, T>) {

    inline fun <reified I : T> get(): I {
        return classToInstanceMap[I::class] as I
    }

    fun getValues(): Collection<T> {
        return classToInstanceMap.values
    }

    companion object {
        inline fun <reified C> create(parameterTypes: Array<Class<*>>, vararg args: Any): AutoKClassToInstanceMap<C> {

            val classToInstanceMap = HashMap<KClass<*>, C>()

            for (t in Utils.getSubclasses<C>(C::class.java)) {
                classToInstanceMap[C::class] = t.getConstructor(*parameterTypes).newInstance(*args)
            }

            return AutoKClassToInstanceMap(classToInstanceMap)
        }

        inline fun <reified C> create(): AutoKClassToInstanceMap<C> {

            val classToInstanceMap = HashMap<KClass<*>, C>()

            for (t in Utils.getSubclasses<C>(C::class.java)) {
                classToInstanceMap[C::class] = t.getConstructor().newInstance()
            }

            return AutoKClassToInstanceMap(classToInstanceMap)
        }
    }
}