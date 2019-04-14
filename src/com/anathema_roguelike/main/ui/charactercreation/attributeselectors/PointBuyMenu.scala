package com.anathema_roguelike
package main.ui.charactercreation.attributeselectors

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.{AbstractMenu, Menu}
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import squidpony.squidgrid.gui.gdx.SquidInput


object PointBuyMenu {
  private val MAX_ATTRIBUTE_VALUE: Int = 8
  private val INITIAL_ATTRIBUTE_VALUE: Int = 3
  private val attributes: Array[Class[_ <: Attribute]] = Utils.getSubclasses[Attribute]().toArray
}

class PointBuyMenu(position: Point, width: Int, height: Int, centered: Boolean, spacing: Int, player: Player)
  extends AbstractMenu[Class[_ <: Attribute]](
    position,
    width,
    height,
    spacing,
    1.0f,
    false,
    PointBuyMenu.attributes,
    centered,
    "Finish") {

  private var points: Int = 15

  def isDone: Boolean = points <= 0

  override def finish(): Unit = {
    if (isDone) super.finish()
  }

  override def renderContent(): Unit = {
    super.renderContent()

    (0 until getItems.size).foreach(i => {
      val attribute: Class[_ <: Attribute] = getItem(i)
      val current: Int = player.getStatAmount(attribute).toInt
      val text: String = getMenuItems(i).getText
      if (current > 3) renderString(DisplayLayer.UI_FOREGROUND, -2, getSpacing * i, "<")
      if (current < 8 && points > 0) renderString(DisplayLayer.UI_FOREGROUND, text.length + 2, getSpacing * i, ">")
    })
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    getFocusedItem.foreach(attribute => {

        val current: Int = player.getStatAmount(attribute).toInt

        key match {
          case SquidInput.RIGHT_ARROW | 'h' =>
            if (shift && current < PointBuyMenu.MAX_ATTRIBUTE_VALUE) {
              val pointsToSpend: Int = Math.min(PointBuyMenu.MAX_ATTRIBUTE_VALUE - current, points)
              points = points - pointsToSpend
              player.setAttributeScore(current + Math.min(PointBuyMenu.MAX_ATTRIBUTE_VALUE - current, pointsToSpend))(attribute)
            }
            else if (current < PointBuyMenu.MAX_ATTRIBUTE_VALUE && points > 0) {
              points -= 1
              player.setAttributeScore(current + 1)(attribute)
            }

          case SquidInput.LEFT_ARROW | 'l' =>
            if (shift && current > PointBuyMenu.INITIAL_ATTRIBUTE_VALUE) {
              points = points + Math.min(5, current - PointBuyMenu.INITIAL_ATTRIBUTE_VALUE)
              player.setAttributeScore(current - Math.min(5, current - PointBuyMenu.INITIAL_ATTRIBUTE_VALUE))(attribute)
            }
            else if (current > PointBuyMenu.INITIAL_ATTRIBUTE_VALUE) {
              points += 1
              player.setAttributeScore(current - 1)(attribute)
            }

          case _ =>
        }
      })

    super.processKeyEvent(key, alt, ctrl, shift)
  }

  def getPoints: Int = points

  def getPlayer: Player = player

  override def onSelect(obj: Class[_ <: Attribute]): Unit = { }
}
