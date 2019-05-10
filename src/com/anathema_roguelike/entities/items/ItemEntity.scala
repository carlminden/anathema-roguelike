

package com.anathema_roguelike
package entities.items

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.environment.{HasLocation, Location}
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer
import com.anathema_roguelike.main.display.VisualRepresentation

class ItemEntity(location: HasLocation, item: Item) extends Entity(location) {

  override protected def renderThis(): Unit = {
    Game.getInstance.getMap.renderEntity(DungeonLayer.NORMAL, this)
  }

  override def getVisualRepresentation: VisualRepresentation = item.getVisualRepresentation

  override def getNextAction: Option[Action[_]] = item.getNextAction
}
