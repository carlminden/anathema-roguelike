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
package main

import com.anathema_roguelike.main.display.{Color, Display, DungeonMap}
import com.anathema_roguelike.main.input.Input
import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.UserInterface
import com.anathema_roguelike.main.utilities.Latch
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import com.google.common.eventbus.EventBus
import java.util.Random

import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.charactercreation.CharacterCreationUI
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SplashScreen

object Game {
  private val instance = new Game

  def main(args: Array[String]): Unit = {
    Thread.currentThread.setUncaughtExceptionHandler((arg0: Thread, arg1: Throwable) => {
      System.out.println("Thread " + arg0)
      arg1.printStackTrace()
    })

    val config = new LwjglApplicationConfiguration

    config.width = UIConfig.TERM_WIDTH * Display.cellWidth
    config.height = UIConfig.TERM_HEIGHT * Display.cellHeight

    config.title = "Anathema"
    config.vSyncEnabled = false
    config.foregroundFPS = 0
    config.backgroundFPS = 30
    config.resizable = false

    //config.addIcon(path, fileType);
    new LwjglApplication(Game.getInstance, config)
    Game.getInstance.start()
  }

  def getInstance: Game = {
    instance
  }
}

class Game protected() extends ApplicationAdapter {
  private var state: Option[State] = None
  private var display: Display = _

  private val eventBus = new EventBus
  private val ui = new UserInterface
  private val input = new Input

  private val rand = new Random

  final private val latch = new Latch

  override def create(): Unit = {
    super.create()

    display = new Display(input)
    state = new State

    latch.release()
  }

  def getState: State = state.get

  def getUserInterface: UserInterface = ui

  def getMap: DungeonMap = ui.getMap

  def getDisplay: Display = display

  def getEventBus: EventBus = eventBus

  def getRandom: Random = rand

  def getInput: Input = input

  def quit(): Unit = display.quit()

  protected def start(): Unit = {
    latch.await()
    new SplashScreen().run

    CharacterCreationUI.createCharacter(getState.getPlayer)

    ui.init(state.get)

    while(true) {
      display.lock()
      getState.computeNextState()
      display.unlock()
    }
  }

  override def render(): Unit = {
    display.lock()
    val bgColor = Color.BLACK

    Gdx.gl.glClearColor(bgColor.r / 255.0f, bgColor.g / 255.0f, bgColor.b / 255.0f, 1.0f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    ui.render()

    if(input.getSquidInput.hasNext) {
      input.getSquidInput.next()
    }

    display.draw()
    display.unlock()
  }

  override def pause(): Unit = super.pause()

  override def resume(): Unit = super.resume()

  override def resize(width: Int, height: Int): Unit = {
    super.resize(width, height)
    if(input != null && input.getMouse != null) {
      input.getMouse.reinitialize(
        width.toFloat / UIConfig.TERM_WIDTH,
        height.toFloat / UIConfig.TERM_HEIGHT,
        UIConfig.TERM_WIDTH,
        UIConfig.TERM_HEIGHT,
        0,
        0
      )
    }
  }

  def getElapsedTime: Double = state.map(_.getElapsedTime).getOrElse(0.0)

  def getTurn: Long = state.map(_.getPlayer.getTurn).getOrElse(0L)
}