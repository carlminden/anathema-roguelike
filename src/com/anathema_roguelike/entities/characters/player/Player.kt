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
package com.anathema_roguelike.entities.characters.player


import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.inventory.SecondaryWeapon
import com.anathema_roguelike.entities.characters.player.classes.ClassSet
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.entities.items.AnyItemFactory
import com.anathema_roguelike.entities.items.armor.ArmorMaterial
import com.anathema_roguelike.entities.items.armor.Boots
import com.anathema_roguelike.entities.items.armor.Chestpiece
import com.anathema_roguelike.entities.items.armor.Helm
import com.anathema_roguelike.entities.items.armor.Pants
import com.anathema_roguelike.entities.items.weapons.Weapon
import com.anathema_roguelike.entities.items.weapons.types.WeaponType
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.main.input.InputHandler
import com.anathema_roguelike.main.input.PlayerKeyHandler
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.secondarystats.Light

import squidpony.squidgrid.gui.gdx.SColor

class Player : Character() {

    private var name: String = ""

    private val inputHandler = InputHandler(PlayerKeyHandler(this))
    private val classSet = ClassSet(this)

    val classes: Collection<Class<out PlayerClass>>
        get() = classSet.classes

    override var facing: Double
        get() = super.facing
        set(facing) {
            super.facing = facing
        }

    init {
        faction = Faction.PLAYER

        val f = AnyItemFactory()

        inventory.equip(Helm(ArmorMaterial.CHAINMAIL))
        inventory.equip(Chestpiece(ArmorMaterial.LEATHER))
        inventory.equip(Pants(ArmorMaterial.CHAINMAIL))
        inventory.equip(Boots(ArmorMaterial.CHAINMAIL))

        inventory.getSlot<PrimaryWeapon>().equip(f.generate<Weapon, WeaponType>(WeaponType::class.java))
        inventory.getSlot<SecondaryWeapon>().equip(f.generate<Weapon, WeaponType>(WeaponType::class.java))

        println(inventory.getEquippedItems())

        setName("Carl")

    }

    override fun toString(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    override fun onDeath() {
        println("YOU WERE KILLED")
    }

    fun getClassLevels(characterClass: Class<out PlayerClass>): Int {
        return classSet.getClassLevels(characterClass)
    }

    fun grantClassLevel(characterClass: Class<out PlayerClass>?) {
        classSet.grantClassLevel(characterClass)
    }

    override fun levelUp() {
        val classes = Utils.getSubclasses(PlayerClass::class.java)

        val classSelectionScreen = SelectionScreen("Select your Class", classes, false)

        grantClassLevel(classSelectionScreen.run())

        super.levelUp()
    }

    public override fun setNextPendingAction() {
        Game.getInstance().userInterface.newLine()

        Game.getInstance().display.unlock()

        Game.getInstance().input.proccessInput(inputHandler, { hasPendingActions() }, null)

        Game.getInstance().display.lock()
    }

    override fun renderThis() {
        Game.getInstance().map.renderEntity(DungeonLayer.PLAYER, this)

        val color = Color.factory.blend(Color.NO_LIGHT_PLAYER, Color.WHITE, getStatAmount(Light::class.java) + 0.2)

        Game.getInstance().map.renderChar(DungeonLayer.PLAYER, x, y, visualRepresentation.char, color)
    }

    override fun getVisualRepresentation(): VisualRepresentation {
        return VisualRepresentation('@')
    }
}
