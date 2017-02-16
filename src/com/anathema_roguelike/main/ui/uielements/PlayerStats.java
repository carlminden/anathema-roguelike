/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.main.ui.uielements;


import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.characters.inventory.Chest;
import com.anathema_roguelike.characters.inventory.Feet;
import com.anathema_roguelike.characters.inventory.Head;
import com.anathema_roguelike.characters.inventory.Legs;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Agility;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth;
import com.anathema_roguelike.stats.characterstats.secondarystats.Health;

import squidpony.squidgrid.gui.gdx.SColor;

public class PlayerStats extends UIElement {

	private Player player;
	
	private int renderPos;
	
	public PlayerStats(int x, int y, int width, int height, Player player) {
		super(x, y, width, height, "Character", 1f);
		
		this.player = player;
	}
	
	@Override
	protected void renderContent() {
		renderPos = 0;
		
		renderChar();
		renderPos++;
		renderResources();
		renderPos++;
		renderAbilities();
		renderPos++;
		renderOtherStats();
		renderPos++;
		renderInventory();
	}
	
	public void renderChar() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, "" + player);
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Level " + player.getLevel() + " " + Utils.getName(player.getCharClass()));
	}
	
	public void renderResources() {
		
		int currentHP = (int) player.getStatAmount(CurrentHealth.class);
		int maxHP = (int) player.getStatAmount(Health.class);
		
		SColor color = Color.factory.blend(Color.RED, Color.GREEN, (double) currentHP / (double) maxHP);		
		
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "HP: " + currentHP + "/" + maxHP, color);
		
	}
	
	public void renderAbilities() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "STR: " + player.getStatAmount(Strength.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "AGI: " + player.getStatAmount(Agility.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "CON: " + player.getStatAmount(Constitution.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "INT: " + player.getStatAmount(Intelligence.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "PER: " + player.getStatAmount(Perception.class));
	}
	
	public void renderOtherStats() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "DMG: DEPRECATED");
	}
	
	public void renderInventory() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Weapon: " + player.getInventory().getEquipedItem(PrimaryWeapon.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Head:  " + player.getInventory().getEquipedItem(Head.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Chest:  " + player.getInventory().getEquipedItem(Chest.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Legs:  " + player.getInventory().getEquipedItem(Legs.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Feet:  " + player.getInventory().getEquipedItem(Feet.class));
	}
}
