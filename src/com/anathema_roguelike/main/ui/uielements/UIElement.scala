

package com.anathema_roguelike
package main.ui.uielements

import java.util
import java.util.ArrayList

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.{Color, Display, Outline, RenderSurface, Renderable}
import com.anathema_roguelike.main.display.animations.Animation
import com.anathema_roguelike.main.utilities.position.{HasPosition, Point}
import squidpony.squidgrid.gui.gdx.SColor

import scala.collection.mutable.ListBuffer

abstract class UIElement private (
      position: Point,
      outerPosition: Point,
      width: Int,
      outerWidth: Int,
      height: Int,
      outerHeight: Int,
      background: Float,
      border: Option[Border] = None
  ) extends RenderSurface(width, height) with Renderable with HasPosition {

  private val elements: ListBuffer[UIElement] = ListBuffer[UIElement]()

  def this(position: Point, width: Int, height: Int, background: Float) {
    this(position, position, width, width, height, height, background)
  }

  def this(position: Point, width: Int, height: Int, background: Float, borderTitle: Option[String]) {
    this(
      new Point(position.getX + 1, position.getY + borderTitle.map(_ => 3).getOrElse(1)),
      position,
      width - 2,
      width,
      height - borderTitle.map(_ => 4).getOrElse(2),
      height,
      background
    )
  }

  protected def renderContent(): Unit

  protected def renderBackground(): Unit = {

    border.foreach(_.render())

    for (i <- 0 to getWidth; j <- 0 to getHeight) {
      if (background == 0f) renderChar(DisplayLayer.UI_BACKGROUND, i, j, ' ', Color.BLACK)
      else if (background == 1f) renderChar(DisplayLayer.UI_BACKGROUND, i, j, '\u2588', Color.BLACK)
      else renderChar(DisplayLayer.UI_BACKGROUND, i, j, '\u2588', Color.opacity(Color.BLACK, background))
    }
  }

  override def getPosition: Point = position

  def addUIElement(element: UIElement): Unit = {
    elements :+ element
  }

  def removeUIElement(element: UIElement): Unit = {
    elements -= element
  }

  def getUIElements: Iterable[UIElement] = elements

  def getOuterX: Int = outerPosition.getX

  def getOuterY: Int = outerPosition.getY

  def getOuterWidth: Int = outerWidth

  def getOuterHeight: Int = outerHeight

  def getBackground: Float = background

  override final def render(): Unit = {
    renderBackground()
    renderContent()

    for (element <- getUIElements) {
      element.render()
    }
    //occasionally useful UI debugging tool
    //		renderChar(DisplayLayer.UI_FOREGROUND, 0, 0, 'X', Color.ERROR);
    //		renderChar(DisplayLayer.UI_FOREGROUND, 0, getHeight() - 1, 'X', Color.ERROR);
    //		renderChar(DisplayLayer.UI_FOREGROUND, getWidth() - 1, 0, 'X', Color.ERROR);
    //		renderChar(DisplayLayer.UI_FOREGROUND, getWidth() - 1, getHeight() - 1, 'X', Color.ERROR);
  }

  override def renderChar(layer: Display.DisplayLayer, x: Int, y: Int, string: Char, color: SColor): Unit = {
    Game.getInstance.getDisplay.renderChar(layer, x + getX, y + getY, string, color)
  }

  override def createAnimation(layer: Display.DisplayLayer, animation: Animation): Unit = {
    super.createAnimation(layer, animation, getPosition)
  }

  override def renderOutline(outline: Outline): Unit = {
    outline.setOffset(new Point(getX, getY))
    outline.render()
  }
}
