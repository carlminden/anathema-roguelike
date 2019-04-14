

package com.anathema_roguelike
package main.display

import java.util.Objects

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.foes.Foe
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.fov.{LightLevels, TotalLightShader, VisibleLightBackgroundShader, VisibleLightForegroundShader}
import com.anathema_roguelike.main.{Config, Game, State}
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer.DungeonLayer
import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.uielements.Rectangular
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SColor

import scala.collection.JavaConverters._

object DungeonMap {

  object DungeonLayer extends Enumeration {
    type DungeonLayer = Value
    val FOG_OF_WAR_LIGHT, FOG_OF_WAR_BACKGROUND, FOG_OF_WAR_FOREGROUND,
    LIT_FOG_OF_WAR_LIGHT, LIT_FOG_OF_WAR_BACKGROUND, LIT_FOG_OF_WAR_FOREGROUND,
    LIGHT, BACKGROUND, FOREGROUND, NORMAL, NPCS, PLAYER = Value
  }

}

class DungeonMap(var x: Int, var y: Int, var state: State) extends Renderable with Rectangular {
  private val width: Int = UIConfig.DUNGEON_MAP_WIDTH
  private val height: Int = UIConfig.DUNGEON_MAP_HEIGHT
  private val dungeonWidth: Int = Config.DUNGEON_WIDTH
  private val dungeonHeight: Int = Config.DUNGEON_HEIGHT
  private val dungeonLayers: Map[DungeonLayer, DisplayBuffer] = {
    DungeonMap.DungeonLayer.values.toList.map(layer => layer -> new DisplayBuffer(dungeonWidth, dungeonHeight)).toMap
  }

  override def render(): Unit = {

    for (layer <- DungeonMap.DungeonLayer.values) {
      dungeonLayers(layer).clear()
    }

    state.render()

    val player: Player = Game.getInstance.getState.getPlayer
    val lightLevels: LightLevels = state.getCurrentEnvironment.getLightLevels

    val visibility: BufferMask = player.getCurrentVisibility

    val visibleLight: DisplayBuffer = dungeonLayers(DungeonMap.DungeonLayer.LIGHT)
    val visibleBackground: DisplayBuffer = dungeonLayers(DungeonMap.DungeonLayer.BACKGROUND)
    val visibleForeground: DisplayBuffer = dungeonLayers(DungeonMap.DungeonLayer.FOREGROUND)

    visibleForeground.compose(dungeonLayers(DungeonMap.DungeonLayer.NORMAL))

    val fogOfWarLight: DisplayBuffer = generateLayer(
      lightLevels, visibility, visibleLight,
      dungeonLayers(DungeonMap.DungeonLayer.LIT_FOG_OF_WAR_LIGHT),
      Game.getInstance.getState.getCurrentEnvironment.getFogOfWarLight,
      dungeonLayers(DungeonMap.DungeonLayer.FOG_OF_WAR_LIGHT)
    )

    val fogOfWarBackground: DisplayBuffer = generateLayer(
      lightLevels, visibility, visibleBackground,
      dungeonLayers(DungeonMap.DungeonLayer.LIT_FOG_OF_WAR_BACKGROUND),
      Game.getInstance.getState.getCurrentEnvironment.getFogOfWarBackground,
      dungeonLayers(DungeonMap.DungeonLayer.FOG_OF_WAR_BACKGROUND)
    )

    val fogOfWarForeground: DisplayBuffer = generateLayer(
      lightLevels, visibility, visibleForeground,
      dungeonLayers(DungeonMap.DungeonLayer.LIT_FOG_OF_WAR_FOREGROUND),
      Game.getInstance.getState.getCurrentEnvironment.getFogOfWarForeground,
      dungeonLayers(DungeonMap.DungeonLayer.FOG_OF_WAR_FOREGROUND)
    )

    visibleForeground.compose(dungeonLayers(DungeonMap.DungeonLayer.NPCS))

    visibleBackground.transform(new VisibleLightForegroundShader(lightLevels))
    visibleBackground.applyMask(visibility)

    visibleForeground.transform(new VisibleLightForegroundShader(lightLevels))
    visibleForeground.applyMask(visibility)

    visibleForeground.compose(dungeonLayers(DungeonMap.DungeonLayer.PLAYER))


    for (i <- 0 until dungeonWidth; j <- 0 until dungeonHeight) {
      Game.getInstance.getDisplay.renderChar(DisplayLayer.DUNGEON_LIGHT, getX + i, getY + j, ' ', Color.BLACK)
      Game.getInstance.getDisplay.renderChar(DisplayLayer.DUNGEON_BACKGROUND, getX + i, getY + j, ' ', Color.BLACK)
      Game.getInstance.getDisplay.renderChar(DisplayLayer.DUNGEON_FOREGROUND, getX + i, getY + j, ' ', Color.BLACK)
      Game.getInstance.getDisplay.renderChar(DisplayLayer.DUNGEON_OVERLAY, getX + i, getY + j, ' ', Color.BLACK)
      if (visibility.get(i, j)) {
        //Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_OVERLAY, getX() + i, getY() + j, 'X', Color.ERROR);
      }
    }

    fogOfWarLight.render(DisplayLayer.DUNGEON_LIGHT, getX, getY, getWidth, getHeight)
    fogOfWarBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX, getY, getWidth, getHeight)
    fogOfWarForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX, getY, getWidth, getHeight)

    visibleLight.transform(new VisibleLightBackgroundShader(lightLevels))
    visibleLight.applyMask(visibility)

    visibleLight.render(DisplayLayer.DUNGEON_LIGHT, getX, getY, getWidth, getHeight)
    visibleBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX, getY, getWidth, getHeight)
    visibleForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX, getY, getWidth, getHeight)

    renderFoVs()
  }

  private def generateLayer(lightLevels: LightLevels, visibility: BufferMask, visible: DisplayBuffer, litFogOfWar: DisplayBuffer, fogOfWar: DisplayBuffer, newFogOfWar: DisplayBuffer): DisplayBuffer = {
    litFogOfWar.transform(new TotalLightShader(lightLevels))
    litFogOfWar.applyMask(visibility)

    newFogOfWar.applyMask(visibility)

    fogOfWar.compose(litFogOfWar)
    fogOfWar.compose(newFogOfWar, (buffer: DisplayBuffer, x: Int, y: Int, string: Char, color: SColor, display: Boolean) => {
      new DisplayCell(string, Color.factory.dimmer(color), display)
    })

    fogOfWar
  }

  private def renderFoVs(): Unit = {
    val player: Player = Game.getInstance.getState.getPlayer
    val visibility: BufferMask = player.getCurrentVisibility

    val enemyUnawareVision: BufferMask = new BufferMask(dungeonWidth, dungeonHeight)
    val enemyAlertedVision: BufferMask = new BufferMask(dungeonWidth, dungeonHeight)
    val enemyDetectedVision: BufferMask = new BufferMask(dungeonWidth, dungeonHeight)

    for (character <- state.getCurrentEnvironment.getEntities(classOf[Foe])) {
      if (character.isVisibleTo(player)) {
        if (player.isVisibleTo(character)) {
          enemyDetectedVision.or(character.getCurrentVisibility)

        } else if (character.getMostInterestingStimulus.isPresent) {
          enemyAlertedVision.or(character.getCurrentVisibility)

        } else {
          enemyUnawareVision.or(character.getCurrentVisibility)
        }
      }
    }
    enemyUnawareVision.nand(enemyAlertedVision)
    enemyUnawareVision.nand(enemyDetectedVision)

    enemyAlertedVision.nand(enemyDetectedVision)

    renderFoVOverlay(visibility, enemyUnawareVision, Color.UNAWARE)
    renderFoVOverlay(visibility, enemyAlertedVision, Color.ALERTED)
    renderFoVOverlay(visibility, enemyDetectedVision, Color.DETECTED)

    for (character <- state.getCurrentEnvironment.getEntities(classOf[Foe])) {
      if (character.isVisibleTo(player)) {
        if (!player.isVisibleTo(character) && character.getMostInterestingStimulus.isPresent) {
          //TODO needs work
          //if(character.getMostInterestingStimulus().get().getLocation().isPresent()) {
          //TODO remove, this is just a debug output
          //Point p = character.getMostInterestingStimulus().get().getLocation().get().getPosition();
          //Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_OVERLAY, getX() + p.getX(), getY() + p.getY(), '@', Color.ALERTED);
          //}
          character.getPercievedStimuli.foreach(s => {
            s.getLocation.foreach(l => {
              Game.getInstance.getDisplay.renderChar(DisplayLayer.DUNGEON_OVERLAY, getX + l.getX, getY + l.getY, '@', Color.ALERTED)
            })
          })
        }
      }
    }

    player.getPercievedStimuli.foreach(s => {
      s.getLocation.foreach(l => {
        Game.getInstance.getDisplay.renderVisualRepresentation(DisplayLayer.DUNGEON_OVERLAY, getX + l.getX, getY + l.getY, s.getVisualRepresentation)
      })
    })
  }

  private def renderFoVOverlay(playerVision: BufferMask, enemyFoV: BufferMask, color: SColor): Unit = {
    enemyFoV.and(playerVision)
    Game.getInstance.getDisplay.renderOutline(DisplayLayer.DUNGEON_OVERLAY, new BufferMaskOutline(new Point(getX, getY), enemyFoV, color))
  }

  def renderEntity(layer: DungeonLayer, entity: Entity): Unit = {
    val x: Int = entity.getX
    val y: Int = entity.getY
    val rep: VisualRepresentation = entity.getVisualRepresentation
    renderVisualRepresentation(layer, x, y, Objects.requireNonNullElseGet(rep, () => new VisualRepresentation('X', Color.ERROR)))
  }

  def renderVisualRepresentation(layer: DungeonLayer, x: Int, y: Int, rep: VisualRepresentation): Unit = {
    renderChar(layer, x, y, rep.getChar, rep.getColor)
  }

  def renderChar(layer: DungeonLayer, x: Int, y: Int, string: Char, color: SColor): Unit = {
    dungeonLayers(layer).get(x, y).setChar(string)
    dungeonLayers(layer).get(x, y).setColor(color)
  }

  def renderChar(layer: DungeonLayer, x: Int, y: Int, string: Char): Unit = {
    renderChar(layer, x, y, string, Color.WHITE)
  }

  def renderString(layer: DungeonLayer, x: Int, y: Int, string: String, color: SColor): Unit = {
    var i: Int = 0
    while ( {
      i < string.length
    }) {
      renderChar(layer, x + i, y, string.charAt(i), color)

      {
        i += 1; i - 1
      }
    }
  }

  def renderString(layer: DungeonLayer, x: Int, y: Int, string: String): Unit = {
    renderString(layer, x, y, string, Color.WHITE)
  }

  override def getWidth: Int = width

  override def getHeight: Int = height

  override def getX: Int = x

  override def getY: Int = y
}
