package com.anathema_roguelike
package entities.characters.actions.costs

import com.anathema_roguelike.actors.Actor

abstract class ActionCost(actor: Actor, after: Boolean = false) {

  def pay(): Unit

  def getActor: Actor = actor

  def isAfter: Boolean = after

  def isBefore: Boolean = !after
}
