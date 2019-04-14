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
package com.anathema_roguelike.main.ui.uielements;


import com.anathema_roguelike.entities.characters.inventory.*;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.characterstats.attributes.*;
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth;
import com.anathema_roguelike.stats.characterstats.secondarystats.Health;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel;
import squidpony.squidgrid.gui.gdx.SColor;

import java.util.stream.Collectors;

public class PlayerStats extends UIElement {

	private Player player;
	
	private int renderPos;
	
	public PlayerStats(Point position, int width, int height, Player player) {
		super(position, width, height, "Character", 1f);
		
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
		renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, player.getLocation().toString());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Level " + player.getLevel() + " " + player.getClasses().stream().map(c -> Utils.getName(c)).collect(Collectors.joining(",")));
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
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Concealment: " + player.getStatAmount(Concealment.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Veil: " + player.getStatAmount(Veil.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Attenuation: " + player.getStatAmount(Attenuation.class));
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Visibility: " + (int)player.getStatAmount(Visibility.class));
		
		VisibilityLevel visibility = player.getStat(Visibility.class).getVisibilityLevel();
		
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Visibility Level: " + visibility.getName(), visibility.getColor());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Light Level: " + player.getEnvironment().getLightLevels().get(player.getPosition()));
	}
	
	public void renderInventory() {
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Primary Weapon: " + player.getInventory().getSlot(PrimaryWeapon.class).getEquippedItem());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Secondary Weapon: " + player.getInventory().getSlot(SecondaryWeapon.class).getEquippedItem());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Head: " + player.getInventory().getSlot(Head.class).getEquippedItem());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Chest: " + player.getInventory().getSlot(Chest.class).getEquippedItem());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Legs: " + player.getInventory().getSlot(Legs.class).getEquippedItem());
		renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Feet: " + player.getInventory().getSlot(Feet.class).getEquippedItem());
	}
}
