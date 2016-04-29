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
package com.anathema_roguelike.characters.stats.tertiarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.Stat;

public abstract class TertiaryStat extends Stat {

	private int amount = 0;
	
	public TertiaryStat(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getAmount() {
		return amount;
	}
	
	public void set(Object source, int amount) {
		this.amount = amount;
	}
	
	public void modify(Object source, int amount) {
		set(source, this.amount + amount);
	}
}
