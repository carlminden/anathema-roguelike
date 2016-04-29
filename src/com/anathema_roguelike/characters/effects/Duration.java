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
package com.anathema_roguelike.characters.effects;

public abstract class Duration extends Calculation<Integer> implements Comparable<Duration> {
	
	public static int ROUND = 1;
	public static int ENCOUNTER = 25;
	public static int SCENE = 100;
	public static int PERMANENT = -1;
	
	private int remaining = Integer.MAX_VALUE;
	
	private int type;
	
	public static Duration copy(final Duration duration) {
		if(duration == null) {
			return null;
		} else {
			return new Duration(duration.getType()) {
				@Override
				public Integer calculate() {
					return duration.calculate();
				}
			};
		}
	}
	
	public Duration(int type) {
		super();
		this.type = type;
	}
	
	public void activate() {
		remaining = (int) (calculate() * type);
	}
	
	public int getType() {
		return type;
	}
	
	public void decrement() {
		if(getType() == Duration.PERMANENT) {
			return;
		} else {
			remaining--;
		}
	}
	
	public void increase(int amount) {
		remaining += amount;
	}
	
	public boolean isExpired() {
		
		if(getType() == Duration.PERMANENT) {
			return false;
		} else {
			return remaining <= 0;
		}
	}

	public int getRemaining() {
		return remaining == Integer.MAX_VALUE ? calculate() * type : remaining;
	}
	
	public int compareTo(Duration duration) {
		return Integer.compare(getRemaining(), duration.getRemaining());
	}
}
