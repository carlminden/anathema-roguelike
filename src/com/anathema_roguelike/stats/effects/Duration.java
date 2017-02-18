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
package com.anathema_roguelike.stats.effects;

import java.util.function.Supplier;

public abstract class Duration implements Supplier<Double>, Comparable<Duration> {
	
	public static int INSTANT = 0;
	public static int EXTREMELY_SHORT = 1;
	public static int VERY_SHORT = 3;
	public static int SHORT = 5;
	public static int MEDIUM = 10;
	public static int LONG = 25;
	public static int VERY_LONG = 50;
	public static int EXTREMELY_LONG = 100;
	public static int PERMANENT = -1;
	
	
	private double remaining = Double.MAX_VALUE;
	
	private int type;
	
	public static Duration copy(final Duration duration) {
		if(duration == null) {
			return null;
		} else {
			return new Duration(duration.getType()) {
				@Override
				public Double get() {
					return duration.get();
				}
			};
		}
	}
	
	public static Duration permanent() {
		return new FixedDuration(PERMANENT);
	}
	
	public static Duration instant() {
		return new FixedDuration(INSTANT);
	}
	
	public Duration(int type) {
		super();
		this.type = type;
	}
	
	public void activate() {
		remaining = (int) (get() * type);
	}
	
	public int getType() {
		return type;
	}
	
	public void decrement() {
		if(getType() == PERMANENT) {
			return;
		} else {
			remaining--;
		}
	}
	
	public void increase(int amount) {
		remaining += amount;
	}
	
	public boolean isExpired() {
		
		if(getType() == PERMANENT) {
			return false;
		} else {
			return remaining <= 0;
		}
	}

	public double getRemaining() {
		return remaining == Double.MAX_VALUE ? get() * type : remaining;
	}
	
	public int compareTo(Duration duration) {
		return Double.compare(getRemaining(), duration.getRemaining());
	}
}
