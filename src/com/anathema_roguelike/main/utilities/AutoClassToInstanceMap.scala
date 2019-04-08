package com.anathema_roguelike
package main.utilities

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

class AutoClassToInstanceMap[T : TypeTag](parameterTypes: List[Class[_]], args: Any*) {

  private val classToInstanceMap = mutable.Map[Class[_ <: T], T]()

  Utils.getSubclasses[T]().foreach(t => {
    classToInstanceMap.put(t, t.getConstructor(parameterTypes.toArray: _*).newInstance(args))
  })

  def this() {
    this(List())
  }

  def get[I <: T : TypeTag]: I = classToInstanceMap(typeTagToClass[I]).asInstanceOf[I]

  def getValues: Iterable[T] = classToInstanceMap.values
}
