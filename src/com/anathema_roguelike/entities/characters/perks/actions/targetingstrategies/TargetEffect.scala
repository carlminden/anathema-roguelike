package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.stats.Stat
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class TargetEffect[T <: Targetable : TypeTag, +S <: Stat[T]](var name: String) extends HasEffect[Effect[T, S]] {

  def getTargetType: Class[T] = typeTagToClass

  def applyTo(target: T): Unit = {
    getEffect.foreach(e => e.applyTo(target))
  }

  override def toString: String = name
}
