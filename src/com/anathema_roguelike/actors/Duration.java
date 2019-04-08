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
package com.anathema_roguelike.actors;

public class Duration implements Comparable<Duration> {

	public static int INSTANT = 0;
	public static int EXTREMELY_SHORT = 1;
	public static int VERY_SHORT = 3;
	public static int SHORT = 5;
	public static int MEDIUM = 10;
	public static int LONG = 25;
	public static int VERY_LONG = 50;
	public static int EXTREMELY_LONG = 100;
	public static int PERMANENT = Integer.MIN_VALUE;
	
	private double remainingDuration = Double.MAX_VALUE;
	private int initialDuration;
	
	public static Duration copy(final Duration duration) {
		return new Duration(duration.getInitialDuration());
	}
	
	public static Duration permanent() {
		return new Duration(PERMANENT);
	}
	
	public static Duration instant() {
		return new Duration(INSTANT);
	}
	
	public Duration(int duration) {
		this.initialDuration = duration;
		this.remainingDuration = initialDuration;
	}
	
	public int getInitialDuration() {
		return initialDuration;
	}
	
	public void delay(double amount) {
		if(initialDuration != PERMANENT) {
			remainingDuration += amount;
		}
	}
	
	public void elapse(double amount) {
		if(initialDuration != PERMANENT) {
			remainingDuration -= amount;
		}
	}
	
	public boolean isExpired() {
		
		if(initialDuration == PERMANENT) {
			return false;
		} else {
			return remainingDuration <= 0;
		}
	}

	public double getRemaining() {
		return remainingDuration == Double.MAX_VALUE ? initialDuration : remainingDuration;
	}
	
	public int compareTo(Duration duration) {
		return Double.compare(getRemaining(), duration.getRemaining());
	}
}
