package com.anathema_roguelike
package stats.effects

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.stats.Stat

import scala.reflect.runtime.universe._

class Effect[T, +S <: Stat[_ <: T]](
    source: Option[_ <: HasEffect[_ <: Effect[T, S]]],
    modifiers: List[Modifier[_ <: Stat[_ <: T]]],
    duration: Duration = Duration.permanent(),
    var target: Option[T] = None) {


  def getSource = source

  def getModifiers = modifiers

  def getAdditiveBonus[B <: Stat[_] : TypeTag]: Double = {
    getModifiers.filter(m => m.getAffectedStat == typeTagToClass[B]).foldLeft(0.0) {
      (ret, modifier) => ret + modifier.getAdditiveAmount
    }
  }

  def getMultiplier[M <: Stat[_] : TypeTag]: Double = {
    getModifiers.filter(m => m.getAffectedStat == typeTagToClass[M]).foldLeft(1.0) {
      (ret, modifier) => ret * modifier.getMultiplier
    }
  }

  def getDuration: Duration = duration

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder
    builder.append("Effect: ")
    builder.append("Duration: ").append(duration.getRemaining)

    modifiers.foreach(m => {
      builder.append(" Modifier: " + m.getAffectedStat.getSimpleName + " +" + m.getAdditiveAmount + " *" + m.getMultiplier)
    })

    builder.toString
  }

  final def applyTo(target: T): Unit = {
    this.target = target
    onApplicationCallback(target)
  }

  final def remove(): Unit = {
    getTarget.foreach(t => onExpirationCallback(t))
    this.target = Option.empty
  }

  def onApplicationCallback(target: T): Unit = { }

  def onExpirationCallback(target: T): Unit = { }

  def getTarget: Option[T] = target
}
