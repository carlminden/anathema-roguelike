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
package com.anathema_roguelike.main.utilities;

public abstract class BooleanCondition {
	
	public abstract boolean isTrue();
	
	public static BooleanCondition alwaysFalse() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return false;
			}
		};
	}
	
	public static BooleanCondition alwaysTrue() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return true;
			}
		};
	}
	
	public static BooleanCondition and(final BooleanCondition c1, final BooleanCondition c2) {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return c1.isTrue() && c2.isTrue();
			}
		};
	}
	
	public static BooleanCondition or(final BooleanCondition c1, final BooleanCondition c2) {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return c1.isTrue() || c2.isTrue();
			}
		};
	}
	
	public static BooleanCondition not(final BooleanCondition c) {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return !c.isTrue();
			}
		};
	}
}
