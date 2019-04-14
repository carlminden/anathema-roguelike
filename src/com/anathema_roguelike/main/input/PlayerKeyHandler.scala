

package com.anathema_roguelike
package main.input

import com.anathema_roguelike.entities.characters.actions.WaitAction
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import squidpony.squidgrid.gui.gdx.SquidInput
import scala.collection.JavaConverters._

class PlayerKeyHandler(var player: Player) extends DirectionalKeyHandler {
  override def up(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.UP)
  }

  override def down(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.DOWN)
  }

  override def left(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.LEFT)
  }

  override def right(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.RIGHT)
  }

  override def upRight(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.UP_RIGHT)
  }

  override def upLeft(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.UP_LEFT)
  }

  override def downRight(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.DOWN_RIGHT)
  }

  override def downLeft(alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    player.move(Direction.DOWN_LEFT)
  }

  override def handleKey(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    key match {
      case '.' =>
        if (shift) {
          player.takeStairs(Direction.DOWN)
          return
        }
      case ',' =>
        if (shift) {
          player.takeStairs(Direction.UP)
          return
        }
      case SquidInput.INSERT | SquidInput.VERTICAL_ARROW =>
        player.addPendingAction(new WaitAction(player))
      case 'a' =>
        val ability = {
          new SelectionScreen[ActionPerk[_]]("Activate an Ability", player.getPerks[ActionPerk[_]].toArray,
            new Point(0, 0), UIConfig.DUNGEON_MAP_WIDTH + 2, UIConfig.DUNGEON_MAP_HEIGHT + 3,
            0f, .5f, true
          ).run
        }

        ability.foreach(a => player.addPendingAction(a.activate))
      case SquidInput.ESCAPE | 'q' =>
        Game.getInstance.quit()
      case ' ' =>
        player.takeStairs(Direction.DOWN)
      case 'L' =>
        player.levelUp()
      case _ =>
    }
  }
}
