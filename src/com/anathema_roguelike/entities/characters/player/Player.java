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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.entities.characters.player;


import java.util.Collection;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.foes.ai.Faction;
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.entities.characters.inventory.SecondaryWeapon;
import com.anathema_roguelike.entities.characters.player.classes.ClassSet;
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass;
import com.anathema_roguelike.entities.items.AnyItemFactory;
import com.anathema_roguelike.entities.items.armor.ArmorMaterial;
import com.anathema_roguelike.entities.items.armor.Boots;
import com.anathema_roguelike.entities.items.armor.Chestpiece;
import com.anathema_roguelike.entities.items.armor.Helm;
import com.anathema_roguelike.entities.items.armor.Pants;
import com.anathema_roguelike.entities.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.input.InputHandler;
import com.anathema_roguelike.main.input.PlayerKeyHandler;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.secondarystats.Light;

import squidpony.squidgrid.gui.gdx.SColor;

public class Player extends Character {
	
	private String name;
	
	private InputHandler inputHandler = new InputHandler(new PlayerKeyHandler(this));
	private ClassSet classSet = new ClassSet(this);
	
	public Player() {
		setFaction(Faction.PLAYER);
		
		AnyItemFactory f = new AnyItemFactory();
		
		getInventory().equip(new Helm(ArmorMaterial.CHAINMAIL));
		getInventory().equip(new Chestpiece(ArmorMaterial.LEATHER));
		getInventory().equip(new Pants(ArmorMaterial.CHAINMAIL));
		getInventory().equip(new Boots(ArmorMaterial.CHAINMAIL));
		
		getInventory().getSlot(PrimaryWeapon.class).equip(f.generate(WeaponType.class));
		getInventory().getSlot(SecondaryWeapon.class).equip(f.generate(WeaponType.class));
		
		System.out.println(getInventory().getEquippedItems());
		
		setName("Carl");
		
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void onDeath() {
		System.out.println("YOU WERE KILLED");
	}
	
	public int getClassLevels(Class<? extends PlayerClass> characterClass) {
		return classSet.getClassLevels(characterClass);
	}
	
	public void grantClassLevel(Class<? extends PlayerClass> characterClass) {
		classSet.grantClassLevel(characterClass);
	}
	
	public Collection<Class<? extends PlayerClass>> getClasses() {
		return classSet.getClasses();
	}
	
	@Override
	public void levelUp() {
		Collection<Class<? extends PlayerClass>> classes = Utils.getSubclasses(PlayerClass.class);
		
		SelectionScreen<Class<? extends PlayerClass>> classSelectionScreen = new SelectionScreen<>("Select your Class", classes, false);
		
		grantClassLevel(classSelectionScreen.run());
		
		super.levelUp();
	}
	
	@Override
	public void setNextPendingAction() {
		Game.getInstance().getUserInterface().newLine();
		
		Game.getInstance().getDisplay().unlock();
		
		Game.getInstance().getInput().proccessInput(inputHandler, () -> hasPendingActions(), null );
		
		Game.getInstance().getDisplay().lock();
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(DungeonLayer.PLAYER, this);
		
		SColor color = Color.factory.blend(Color.NO_LIGHT_PLAYER, Color.WHITE, getStatAmount(Light.class) + 0.2);
		
		Game.getInstance().getMap().renderChar(DungeonLayer.PLAYER, getX(), getY(), getVisualRepresentation().getChar(), color);
	}
	
	@Override
	public void setFacing(double facing) {
		super.setFacing(facing);
	}

	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('@');
	}
}
