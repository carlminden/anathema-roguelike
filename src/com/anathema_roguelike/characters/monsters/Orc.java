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
package com.anathema_roguelike.characters.monsters;

import com.anathema_roguelike.characters.Monster;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.classes.Rogue;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.attributes.Agility;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;

public class Orc extends Monster {

	public Orc() {
		super(new VisualRepresentation('o', Color.GREEN));
		
		setFaction(Faction.MONSTER);
		
		setAbilityScore(Strength.class, 16);
		setAbilityScore(Agility.class, 14);
		setAbilityScore(Constitution.class, 14);
		setAbilityScore(Intelligence.class, 12);
		setAbilityScore(Perception.class, 10);
		
		setClass(new Rogue());
	}
	
	@Override
	public String toString() {
		return "The Orc";
	}
	
	@Override
	public double getLightEmission() {
		return 10;
	}
}
