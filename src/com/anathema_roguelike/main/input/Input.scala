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
package main.input

import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MouseCallback
import com.anathema_roguelike.main.utilities.position.Point
import com.badlogic.gdx.InputAdapter
import squidpony.squidgrid.gui.gdx.SquidInput
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler
import squidpony.squidgrid.gui.gdx.SquidMouse
import java.util.concurrent.CountDownLatch
import java.util.function.BooleanSupplier
import java.util.function.Consumer

import com.anathema_roguelike.main.display.Display

import scala.collection.mutable

class Input() {

  private var result: Option[Consumer[InputHandler]] = None
  private var latch = new CountDownLatch(1)
  private val mouseCallbacks = mutable.Map[Point, MouseCallback]()


  private val keyHandler: KeyHandler = (key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean) => {
    setResult(inputHandler => inputHandler.getKeyHandler.handle(key, alt, ctrl, shift))
  }

  private val mouse = new SquidMouse(Display.cellWidth, Display.cellHeight, UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, 0, 0, new InputAdapter() {
    override def touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean = {
      setResult(inputHandler => {
        inputHandler.getMouse.touchUp(x, y, pointer, button)

        mouseCallbacks.get(Point(x, y)).foreach(callback => {
          callback.onClick()
        })
      })
      true
    }

    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
      setResult(inputHandler => {
        inputHandler.getMouse.touchDown(screenX, screenY, pointer, button)
      })
      true
    }

    override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
      setResult(inputHandler => {
        inputHandler.getMouse.touchDragged(screenX, screenY, pointer)
      })
      true
    }

    override def scrolled(amount: Int): Boolean = {
      setResult(inputHandler => {
        inputHandler.getMouse.scrolled(amount)
      })
      true
    }

    override def mouseMoved(x: Int, y: Int): Boolean = {
      setResult((inputHandler: InputHandler) => {
        inputHandler.getMouse.mouseMoved(x, y)

        mouseCallbacks.get(Point(x, y)).foreach(callback => {
          callback.onMouseover()
        })
      })
      true
    }
  })

  private val input = new SquidInput(keyHandler, mouse)


  def getSquidInput: SquidInput = input

  private def handleInput(inputHandler: InputHandler): Unit = {
    latch.await()

    runInputResult(inputHandler)
  }

  private def runInputResult(inputHandler: InputHandler): Unit = {

    result.foreach(r => {
      val tempResult = r
      result = None
      tempResult.accept(inputHandler)
    })
  }

  private def setResult(result: Consumer[InputHandler]): Unit = {
    this.result = result
    latch.countDown()
    latch = new CountDownLatch(1)
  }

  def getKeyHandler: SquidInput.KeyHandler = keyHandler

  def getMouse: SquidMouse = mouse

  def proccessInput(inputHandler: InputHandler, done: () => Boolean, registerMouseCallbacks: Option[() => Unit] = None): Unit = {
    while(!done()) {
      mouseCallbacks.clear()

      registerMouseCallbacks.foreach(register => register())

      handleInput(inputHandler)
    }
  }

  def registerMouseCallback(callback: MouseCallback, p: Point): Unit = mouseCallbacks.put(p, callback)
}