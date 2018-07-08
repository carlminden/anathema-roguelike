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
package com.anathema_roguelike.environment.generation.rooms

import java.util.ArrayList
import java.util.Random

import com.anathema_roguelike.entities.characters.foes.corruptions.Thrall
import com.anathema_roguelike.entities.characters.foes.roles.Brawler
import com.anathema_roguelike.entities.characters.foes.species.generic.Orc
import com.anathema_roguelike.environment.Environment
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.environment.terrain.grounds.Stone
import com.anathema_roguelike.environment.terrain.walls.StoneWall
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point

class Cave(depth: Int, averageWidth: Int, averageHeight: Int) : Room(depth, 15, 15, averageWidth, averageHeight) {

    internal var cells: Array<BooleanArray> = Array(width) { BooleanArray(height) }
    internal var cells2: Array<BooleanArray> = Array(width) { BooleanArray(height) }

    private val rand = Random()

    internal var floors = ArrayList<Point>()

    init {

        var openArea = 0f

        while (openArea < .5f) {

            cells = Array(width) { BooleanArray(height) }
            cells2 = Array(width) { BooleanArray(height) }

            for (i in 0 until width) {
                for (j in 0 until height) {
                    cells[i][j] = rand.nextFloat() < .4f
                }
            }

            for (passes in 0..2) {
                for (i in 0 until width) {
                    for (j in 0 until height) {
                        if (filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= 2) {
                            cells2[i][j] = true
                        } else {
                            cells2[i][j] = false
                        }
                    }
                }

                cells = cells2
                cells2 = Array(width) { BooleanArray(height) }
            }

            for (passes in 0..4) {
                for (i in 0 until width) {
                    for (j in 0 until height) {
                        if (filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= -1) {
                            cells2[i][j] = true
                        } else {
                            cells2[i][j] = false
                        }
                    }
                }

                cells = cells2
                cells2 = Array(width) { BooleanArray(height) }
            }

            var x = rand.nextInt(width)
            var y = rand.nextInt(height)

            while (cells[x][y]) {
                x = rand.nextInt(width)
                y = rand.nextInt(height)
            }

            for (i in 0 until width) {
                for (j in 0 until height) {
                    cells2[i][j] = cells[i][j]
                }
            }

            openArea = floodFill(x, y, cells2).toFloat() / (width * height)
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                cells[i][j] = !(cells[i][j] xor cells2[i][j])
            }
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                if (!cells[i][j]) {
                    floors.add(Point(i, j))
                }
            }
        }
    }

    private fun floodFill(x: Int, y: Int, temp: Array<BooleanArray>): Int {

        if (temp[x][y]) {
            return 0
        }

        temp[x][y] = true

        var ret = 1

        for (i in 0..7) {
            val direction = Direction.DIRECTIONS_8[i]

            val next = Direction.offset(Point(x, y), direction)

            if (next.x >= getX() && next.x < getX() + width && next.y >= getY() && next.y < getY() + height) {
                ret += floodFill(next.x, next.y, temp)
            }
        }

        return ret
    }

    override fun place(generator: DungeonGenerator) {

        val map = generator.map
        val level = generator.level

        for (floor in floors) {
            val x = floor.x + x
            val y = floor.y + y

            map[x][y].terrain = Stone()
        }

        for (door in doors) {

            var current = door.position

            while (DungeonGenerator.validPoint(current) && map[current.x][current.y].terrain is StoneWall) {
                map[current.x][current.y].terrain = Stone()

                current = Direction.offset(current, door.direction, 1)
            }

            current = Direction.offset(door.position, door.direction, -1)

            while (DungeonGenerator.validPoint(current) && map[current.x][current.y].terrain is StoneWall) {
                map[current.x][current.y].terrain = Stone()

                current = Direction.offset(current, door.direction, -1)
            }
        }
    }

    override fun getRandomPointInRoom(): Point {

        val floor = floors[rand.nextInt(floors.size)]

        return Point(floor.x + x, floor.y + y)
    }

    private fun filledCellsWithin(x: Int, y: Int, distance: Int): Int {

        var ret = 0

        for (i in -distance..distance) {
            for (j in -distance..distance) {
                if (getCell(x + i, y + j)) {
                    ret++
                }
            }
        }

        return ret
    }

    private fun getCell(x: Int, y: Int): Boolean {
        return if (x >= 0 && x < width && y >= 0 && y < height) {
            cells[x][y]
        } else {
            true
        }
    }

    override fun generateEncounter(level: Environment) {

        for (i in 0..9) {
            val x = Game.getInstance().random.nextInt(width - 2) + x + 1
            val y = Game.getInstance().random.nextInt(height - 2) + y + 1

            if (level.isPassable(Point(x, y))) {
                val orc = Orc(Brawler(), Thrall())

                level.addEntity(orc, Point(x, y))
            }
        }
    }
}
