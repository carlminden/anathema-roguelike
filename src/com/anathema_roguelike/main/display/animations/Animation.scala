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
package main.display.animations

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Display
import com.anathema_roguelike.main.utilities.position.HasPosition
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.PanelEffect
import squidpony.squidgrid.gui.gdx.SColor
import squidpony.squidgrid.gui.gdx.SparseLayers
import squidpony.squidgrid.gui.gdx.SparseTextMap

abstract class Animation(private var position: HasPosition, duration: Float) extends HasPosition {
  private var target: SparseTextMap = _
  private var panelEffect: PanelEffect = _

  private var offset = new Point(0, 0)
  private var finished = false

  protected def update(percent: Float): Unit

  def create(layer: Display.DisplayLayer): Unit = create(layer, new Point(0, 0))

  def create(layer: Display.DisplayLayer, offset: HasPosition): Unit = {

    this.offset = offset.getPosition
    this.target = Game.getInstance.getDisplay.getLayer(layer)
    val layerGroup = Game.getInstance.getDisplay.getLayerGroup(layer)

    panelEffect = new PanelEffect(layerGroup, duration) {
      override protected def update(percent: Float): Unit = Animation.this.update(percent)

      override protected def end(): Unit = {
        Animation.this.end()
        super.end()
      }

      override def act(delta: Float): Boolean = if(finished) true
      else super.act(delta)
    }

    layerGroup.addAction(panelEffect)
  }

  protected def end(): Unit = {
  }

  override def getPosition = new Point(position.getX + offset.getX, position.getY + offset.getY)

  def moveTo(position: Point): Unit = this.position = position

  def finish(): Unit = finished = true

  protected def getPanelEffect: PanelEffect = panelEffect

  protected def renderChar(p: Point, c: Char, color: SColor): Unit = target.place(p.getX, p.getY, c, color)

  def setOffset(offset: HasPosition): Unit = this.offset = offset
}