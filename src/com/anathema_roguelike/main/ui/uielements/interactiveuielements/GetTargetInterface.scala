/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetSet
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.PointsOutline
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.main.display.animations.Blink
import com.anathema_roguelike.main.input.DirectionalKeyHandler
import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import squidpony.squidgrid.gui.gdx.SquidInput

import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.Border

class GetTargetInterface[T <: Targetable](potentialTargets: Iterable[T], instructions: String)
  extends InteractiveUIElement[T](
    new Point(0, UIConfig.MAP_START_Y),
    UIConfig.DUNGEON_MAP_WIDTH,
    UIConfig.DUNGEON_MAP_HEIGHT,
    0f,
    true) {

  private val targets = new TargetSet[T](potentialTargets)
  private val animation = new Blink(new VisualRepresentation('X', Color.RED), new Point(0, 0), 1f)
  private val mapBorder = new Border(Game.getInstance.getUserInterface.getMapPlaceholder, getTitleString)

  Game.getInstance.getUserInterface.addMessage(new Message(instructions, Color.INSTRUCTIONS))

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = new DirectionalKeyHandler() {

    override protected def upRight(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.UP_RIGHT)
    }
    override protected def upLeft(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.UP_LEFT)
    }
    override protected def up(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.UP)
    }
    override protected def right(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.RIGHT)
    }
    override protected def left(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.LEFT)
    }
    override protected def downRight(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.DOWN_RIGHT)
    }
    override protected def downLeft(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.DOWN_LEFT)
    }
    override protected def down(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
      targets.inDirection(Direction.DOWN)
    }

    override protected def handleKey(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = key match {
      case ' ' | SquidInput.ENTER =>
        setResult(targets.current)
        finish()
      case _ =>
    }
  }.handle(key, alt, ctrl, shift)

  override def finish(): Unit = {
    animation.finish()
    super.finish()
  }

  override def processScrollEvent(amount: Int): Boolean = {
    targets.next(amount)
    true
  }

  private def getTitleString = "Current target: " + currentlyTargeted

  override protected def renderContent(): Unit = {
    mapBorder.setTitle(getTitleString)
    mapBorder.render()

    Game.getInstance.getDisplay.renderOutline(
      DisplayLayer.DUNGEON_OVERLAY,
      new PointsOutline(UIConfig.DUNGEON_OFFSET,targets.getTargets.map(t => t.getPosition), Color.ERROR)
    )
  }

  private def currentlyTargeted = {
    animation.moveTo(targets.current.getPosition)
    targets.current
  }

  override def run: Option[T] = {
    animation.moveTo(currentlyTargeted.getPosition)
    animation.create(DisplayLayer.DUNGEON_OVERLAY, UIConfig.DUNGEON_OFFSET)
    super.run
  }

  override def registerMouseCallbacks(): Unit = {
    // TODO Auto-generated method stub
  }
}