

package com.anathema_roguelike
package entities.characters.player.classes

import com.anathema_roguelike.main.utilities.{AutoClassToInstanceMap, Utils}

import scala.collection.mutable
import scala.reflect.ClassTag
import com.anathema_roguelike.entities.characters.Character
import scala.reflect._
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object ClassSet {
  private val classes: AutoClassToInstanceMap[PlayerClass] = new AutoClassToInstanceMap[PlayerClass]
}

class ClassSet(var character: Character) {

  Utils.getSubclasses[PlayerClass]().foreach(c => classLevels.put(c, 0))

  private val classLevels: mutable.Map[Class[_ <: PlayerClass], Int] = mutable.Map[Class[_ <: PlayerClass], Int]()

  def getClassLevels[T <: PlayerClass : TypeTag]: Int = classLevels.getOrElse(typeTagToClass[T], 0)

  def grantClassLevel[T <: PlayerClass : TypeTag](): Unit = {

    val newLevel: Int = getClassLevels[T] + 1

    ClassSet.classes.get[T].getLevel(newLevel).grant(character)
    classLevels.put(typeTagToClass[T], newLevel)
  }

  def getClasses: Iterable[Class[_ <: PlayerClass]] = {
    classLevels.collect {
      case (k, v) if v > 0 => k
    }
  }
}
