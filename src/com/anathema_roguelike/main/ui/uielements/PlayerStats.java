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
import com.anathema_roguelike.characters.stats.abilityscores.Agility;
import com.anathema_roguelike.characters.stats.abilityscores.Constitution;
import com.anathema_roguelike.characters.stats.abilityscores.Intelligence;
import com.anathema_roguelike.characters.stats.abilityscores.Perception;
import com.anathema_roguelike.characters.stats.abilityscores.Strength;
import com.anathema_roguelike.characters.stats.secondarystats.DamageBonus;
import com.anathema_roguelike.characters.stats.secondarystats.Health;
import com.anathema_roguelike.characters.stats.tertiarystats.resources.CurrentHealth;
import com.anathema_roguelike.items.Armor;
import com.anathema_roguelike.items.Weapon;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.Utils;

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
		renderPos++;
		renderExp();
		
	}
	
	public void renderChar() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, "" + player);
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Level " + player.getLevel() + " " + Utils.getName(player.getCharClass()));
	}
	
	public void renderResources() {
		
		int currentHP = player.getModifiedStatScore(CurrentHealth.class);
		int maxHP = player.getModifiedStatScore(Health.class);
		
		SColor color = Color.factory.blend(Color.RED, Color.GREEN, (double) currentHP / (double) maxHP);		
		
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "HP: " + currentHP + "/" + maxHP, color);
		
	}
	
	public void renderAbilities() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "STR: " + player.getModifiedStatScore(Strength.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "AGI: " + player.getModifiedStatScore(Agility.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "CON: " + player.getModifiedStatScore(Constitution.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "INT: " + player.getModifiedStatScore(Intelligence.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "PER: " + player.getModifiedStatScore(Perception.class));
	}
	
	public void renderOtherStats() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "DMG: 1d6 +" + player.getModifiedStatScore(DamageBonus.class));
	}
	
	public void renderInventory() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Weapon: " + player.getInventory().getEquipedItem(Weapon.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Armor:  " + player.getInventory().getEquipedItem(Armor.class));
	}
	
	public void renderExp() {
		
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Experience: ");
		
		renderPos++;
		
		int current = player.getExp();
		int next = Player.getRequiredExp(player.getLevel() + 1);
		float ratio = ((float)current/(float)next);
		
		int length = (int) (ratio * (getWidth() + 3));
		
		for(int i = 0; i < length; i++) {
			renderString(DisplayLayer.UI_FOREGROUND, i, renderPos, " ", Color.DARK_GREEN);
		}
	}

}
