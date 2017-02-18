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
package com.anathema_roguelike.items;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.ItemStatSet;

public abstract class EquippableItem extends Item implements HasStats<EquippableItem, ItemStat> {
	
	private ItemStatSet stats;
	
	public EquippableItem(Optional<VisualRepresentation> representation) {
		super(representation);
		
		this.stats = new ItemStatSet(this);
	}
	
	public void equip(Character character) {
		
	}
	
	public void remove(Character character) {
		
	}
	
	@Override
	public StatSet<EquippableItem, ItemStat> getStatSet() {
		return stats;
	}
}
