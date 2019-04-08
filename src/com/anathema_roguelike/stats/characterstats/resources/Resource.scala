

package com.anathema_roguelike
package stats.characterstats.resources

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.events.ResourceChangedEvent
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}

abstract class Resource(character: Character) extends CharacterStat(character) {
  private var amount = 0

  def modify(initiator: Option[Character], source: Option[_ <: HasEffect[_ <: Effect[Character, _]]], amount: Int): Unit = {
    set(initiator, source, (getAmount + amount).toInt)
  }

  override def getAmount: Double = amount

  def set(initiator: Option[Character], source: Option[_ <: HasEffect[_ <: Effect[Character, _]]], amount: Int): Unit = {
    val currentAmount = getAmount.toInt
    val difference = amount - currentAmount

    getObject.postEvent(new ResourceChangedEvent(source, getClass, difference))

    initiator.foreach(i => {
      if (difference > 0) {
        printResourceGainedMessage(i, source, getObject, difference)
      } else if(difference < 0) {
        printResourceLostMessage(i, source, getObject, Math.abs(difference))
      }
    })

    this.amount = amount
  }

  protected def printResourceGainedMessage(
      initiator: Option[Character],
      source: Option[_ <: HasEffect[_ <: Effect[Character, _]]],
      target: Character,
      amount: Int
    ): Unit = {

    (getObject, initiator) match {
        case (_: Player, init) => {
          val m = new Message("You gain " + amount + " pointSet of " + Utils.getName(this), Color.GREEN)

          init match {
            case Some(_: Player) => m.appendMessage(" from your ", Color.GREEN)
            case Some(npc) => m.appendMessage(" from the " + Utils.getName(npc) + "'s ", Color.GREEN)
          }

          m.appendMessage(Utils.getName(source), Color.GREEN)
          Game.getInstance.getUserInterface.addMessage(m)
        }
        case (_, Some(_: Player)) => {
          val m = new Message("You grant " + amount + " pointSet of " + Utils.getName(this), Color.GREEN)
          m.appendMessage(" to the " + Utils.getName(target))

          source.foreach(s => m.appendMessage(" with your " + Utils.getName(s), Color.GREEN))

          Game.getInstance.getUserInterface.addMessage(m)
        }
        case _ =>
      }
  }

  protected def printResourceLostMessage(initiator: Option[Character], source: Option[_ <: HasEffect[_ <: Effect[Character, _]]], target: Character, amount: Int): Unit = {

    (getObject, initiator) match {
      case (_: Player, init) => {
        val m = new Message("You lose " + amount + " pointSet of " + Utils.getName(this), Color.RED)

        init match {
          case Some(_: Player) => m.appendMessage(" from your ", Color.RED)
          case Some(npc) => m.appendMessage(" from the " + Utils.getName(npc) + "'s ", Color.RED)
        }

        source.foreach(s => m.appendMessage(Utils.getName(s), Color.RED))
        Game.getInstance.getUserInterface.addMessage(m)
      }
      case (_, Some(_: Player)) => {
        val m = new Message("You deal " + amount + " pointSet of " + Utils.getName(this))
        m.appendMessage(" damage to the " + Utils.getName(target))

        source.foreach(s => m.appendMessage(" with your " + Utils.getName(s)))

        Game.getInstance.getUserInterface.addMessage(m)
      }
      case _ =>
    }
  }
}
