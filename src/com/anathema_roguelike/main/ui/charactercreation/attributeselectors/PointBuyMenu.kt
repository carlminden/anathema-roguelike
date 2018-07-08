/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.main.ui.charactercreation.attributeselectors

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.Menu
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.attributes.Attribute

import squidpony.squidgrid.gui.gdx.SquidInput

class PointBuyMenu(position: Point, width: Int, height: Int, centered: Boolean, spacing: Int, val player: Player)
    : Menu<Class<out Attribute>>(position, width, height, centered, spacing, false, 1.0f, attributes, "Finish") {

    var points = 15
        private set

    private val isDone: Boolean
        get() = points <= 0

    init {

        setOnSelectListener { a: Class<out Attribute> -> }
    }

    override fun finish() {
        if (isDone) {
            super.finish()
        }
    }

    override fun renderContent() {
        super.renderContent()
        for (i in 0 until items.size) {

            val attribute = getItem(i)
            val current = player.getStatAmount(attribute).toInt()
            val text = menuItems[i].text

            if (current > 3) {
                renderString(DisplayLayer.UI_FOREGROUND, -2, spacing * i, "<")
            }

            if (current < 8 && points > 0) {
                renderString(DisplayLayer.UI_FOREGROUND, text.length + 2, spacing * i, ">")
            }
        }
    }

    override fun processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean) {

        val attribute = focusedItem

        if (attribute == null) {
            super.processKeyEvent(key, alt, ctrl, shift)

            return
        }


        val current = player.getStatAmount(attribute).toInt()

        when (key) {
            SquidInput.RIGHT_ARROW, 'h' ->

                if (shift && current < MAX_ATTRIBUTE_VALUE) {

                    val pointsToSpend = Math.min(MAX_ATTRIBUTE_VALUE - current, points)

                    points = points - pointsToSpend
                    player.setAttributeScore(attribute, current + Math.min(MAX_ATTRIBUTE_VALUE - current, pointsToSpend))
                } else if (current < MAX_ATTRIBUTE_VALUE && points > 0) {
                    points--
                    player.setAttributeScore(attribute, current + 1)
                }
            SquidInput.LEFT_ARROW, 'l' ->

                if (shift && current > INITIAL_ATTRIBUTE_VALUE) {
                    points = points + Math.min(5, current - INITIAL_ATTRIBUTE_VALUE)
                    player.setAttributeScore(attribute, current - Math.min(5, current - INITIAL_ATTRIBUTE_VALUE))
                } else if (current > INITIAL_ATTRIBUTE_VALUE) {
                    points++
                    player.setAttributeScore(attribute, current - 1)
                }

            else -> {
            }
        }

        super.processKeyEvent(key, alt, ctrl, shift)
    }

    companion object {

        private val MAX_ATTRIBUTE_VALUE = 8
        private val INITIAL_ATTRIBUTE_VALUE = 3
        private val attributes = Utils.getSubclasses(Attribute::class.java)
    }
}
