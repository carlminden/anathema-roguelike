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
package main.display

import java.util.concurrent.locks.ReentrantLock

import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.animations.Animation
import com.anathema_roguelike.main.input.Input
import com.anathema_roguelike.main.ui.UIConfig
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.google.common.collect.ArrayListMultimap
import squidpony.squidgrid.gui.gdx.{DefaultResources, SColor, SparseLayers, SparseTextMap, TextCellFactory}

import scala.collection.JavaConverters._
import scala.collection.mutable

object Display {
  private val dimension = java.awt.Toolkit.getDefaultToolkit.getScreenSize
  var cellWidth: Int = dimension.getWidth.toInt / 150
  var cellHeight: Int = dimension.getHeight.toInt / 48

  sealed class DisplayLayer(val value: Int)

  object DisplayLayer {

    case object DUNGEON_LIGHT extends DisplayLayer(0)
    case object DUNGEON_BACKGROUND extends DisplayLayer(1)
    case object DUNGEON_FOREGROUND extends DisplayLayer(2)
    case object DUNGEON_OVERLAY extends DisplayLayer(3)
    case object UI_BACKGROUND extends DisplayLayer(4)
    case object UI_FOREGROUND extends DisplayLayer(5)
    case object DEBUG extends DisplayLayer(6)

    val layers: Array[DisplayLayer] = Array(
      DUNGEON_LIGHT, DUNGEON_BACKGROUND, DUNGEON_FOREGROUND,
      DUNGEON_OVERLAY, UI_BACKGROUND, UI_FOREGROUND,
      DEBUG
    )

    implicit def toValue(displayLayer: DisplayLayer): Int = displayLayer.value
  }

  System.out.println(cellWidth)
  System.out.println(cellHeight)
}

class Display(val input: Input) extends RenderSurface(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT) {
  private val cellWidth: Int = Display.cellWidth
  private val cellHeight: Int = Display.cellHeight
  private var renderTime = System.currentTimeMillis

  private val batch = new SpriteBatch
  private val pixmap = new Pixmap(256, 128, Pixmap.Format.RGBA8888)
  pixmap.setColor(1, 1, 1, 1)
  pixmap.fill()

  private val tex = new Texture(pixmap)
  private val gameTextCellFactory = new TextCellFactory

  gameTextCellFactory.fontDistanceField("assets/Inconsolata-LGC-Custom-distance.fnt", "assets/Inconsolata-LGC-Custom-distance.png")

  private val uiTextCellFactory = new TextCellFactory

  uiTextCellFactory.fontDistanceField("assets/Inconsolata-LGC-Custom-distance.fnt", "assets/Inconsolata-LGC-Custom-distance.png")

  private val gameStage = new Stage(new StretchViewport(getWidth * cellWidth, getHeight * cellHeight), batch)
  private val gameDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, gameTextCellFactory)
  gameDisplay.font.tweakWidth(cellWidth * 1.1f).tweakHeight(cellHeight * 1.1f).initBySize
  gameDisplay.setPosition(0, 0)

  private val uiStage = new Stage(new StretchViewport(getWidth * cellWidth, getHeight * cellHeight), batch)
  private val uiDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, uiTextCellFactory)
  uiDisplay.font.tweakWidth(cellWidth).tweakHeight(cellHeight * 1f).initBySize
  uiDisplay.setPosition(0, 0)

  private val debugStage = new Stage(new StretchViewport(getWidth * cellWidth, getHeight * cellHeight), batch)
  private val debugDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, uiTextCellFactory)
  debugDisplay.font.tweakWidth(cellWidth).tweakHeight(cellHeight * 1f).initBySize
  debugDisplay.setPosition(0, 0)


  private val layers = mutable.Map[DisplayLayer, SparseTextMap]()
  private val layerGroups = mutable.Map[DisplayLayer, SparseLayers]()
  private val reentrantLock = new ReentrantLock
  private val outlines = ArrayListMultimap.create[DisplayLayer, Outline]

  layers(DisplayLayer.DUNGEON_LIGHT) = gameDisplay.addLayer(DisplayLayer.DUNGEON_LIGHT)
  layerGroups(DisplayLayer.DUNGEON_LIGHT) = gameDisplay

  layers(DisplayLayer.DUNGEON_BACKGROUND) = gameDisplay.addLayer(DisplayLayer.DUNGEON_BACKGROUND)
  layerGroups(DisplayLayer.DUNGEON_BACKGROUND) = gameDisplay

  layers(DisplayLayer.DUNGEON_FOREGROUND) = gameDisplay.addLayer(DisplayLayer.DUNGEON_FOREGROUND)
  layerGroups(DisplayLayer.DUNGEON_FOREGROUND) = gameDisplay

  layers(DisplayLayer.DUNGEON_OVERLAY) = gameDisplay.addLayer(DisplayLayer.DUNGEON_OVERLAY)
  layerGroups(DisplayLayer.DUNGEON_OVERLAY) = gameDisplay

  layers(DisplayLayer.UI_BACKGROUND) = uiDisplay.addLayer(DisplayLayer.UI_BACKGROUND)
  layerGroups(DisplayLayer.UI_BACKGROUND) = uiDisplay

  layers(DisplayLayer.UI_FOREGROUND) = uiDisplay.addLayer(DisplayLayer.UI_FOREGROUND)
  layerGroups(DisplayLayer.UI_FOREGROUND) = uiDisplay

  layers(DisplayLayer.DEBUG) = debugDisplay.addLayer(DisplayLayer.DEBUG)
  layerGroups(DisplayLayer.DEBUG) = debugDisplay


  Gdx.input.setInputProcessor(new InputMultiplexer(gameStage, input.getSquidInput))
  gameStage.addActor(gameDisplay)
  uiStage.addActor(uiDisplay)
  debugStage.addActor(debugDisplay)


  def put(layer: DisplayLayer, x: Int, y: Int, string: Char, color: SColor): Unit = layers(layer).place(x, y, string, color)

  def getLayer(layer: DisplayLayer): SparseTextMap = layers(layer)

  def getLayerGroup(layer: DisplayLayer): SparseLayers = layerGroups(layer)

  def quit(): Unit = Gdx.app.exit()

  def draw(): Unit = {
    renderTime = System.currentTimeMillis
    gameStage.act()
    gameStage.getViewport.apply(false)
    gameStage.draw()
    gameDisplay.clear()
    batch.begin()

    for (layer <- Display.DisplayLayer.layers) {
      for (o <- outlines.get(layer).asScala) {
        o.render()
      }
    }
    outlines.clear()
    batch.end()
    uiStage.act()
    uiStage.getViewport.apply(false)
    uiStage.draw()
    if(Config.DEBUG) {
      debugStage.act()
      debugStage.getViewport.apply(false)
      debugStage.draw()
      debugDisplay.clear()
    }
    Gdx.graphics.setTitle("Anathema")
  }

  def getRenderTime: Long = renderTime

  def renderOutline(layer: DisplayLayer, outline: Outline): Unit = outlines.put(layer, outline)

  def drawLine(x1: Int, y_1: Int, x2: Int, y_2: Int, color: SColor): Unit = {
    val y1 = (getHeight * Display.cellHeight) - y_1
    val y2 = (getHeight * Display.cellHeight) - y_2

    val length = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toInt
    val angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1)).toInt

    batch.setColor(color.r, color.g, color.b, color.a)
    batch.draw(tex, x1, y1, 0f, 1f, length, 1f, 1f, 1f, angle, 0, 0, tex.getWidth, tex.getHeight, false, false)
  }

  def addAnimation(animation: Animation): Unit = animation.create(DisplayLayer.DUNGEON_OVERLAY)

  def clear(layer: DisplayLayer): Unit = layers(layer).clear()

  def lock(): Unit = reentrantLock.lock()

  def unlock(): Unit = reentrantLock.unlock()
}