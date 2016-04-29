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
package com.anathema_roguelike.main.utilities;

import java.util.ArrayList;

public class Dice {
	
	private ArrayList<Die> dice = new ArrayList<>();
	
	public Dice(int number, int sides) {
		for(int i = 0; i < number; i++) {
			add(new Die(sides));
		}
	}
	
	public void add(Die die){
		dice.add(die);
	}
	
	public int roll() {
		
		int sum = 0;
		
		for(Die die : dice) {
			sum += die.roll();
		}
		
		return sum;
	}
}
