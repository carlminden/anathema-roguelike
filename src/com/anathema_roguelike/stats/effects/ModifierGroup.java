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

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.stats.Stat;

public class ModifierGroup<T extends Stat<?>> {
	private Duration duration;
	private Object source;
	private ArrayList<Modifier<? extends T>> modifiers;
	
	@SafeVarargs
	public ModifierGroup(Object source, Modifier<? extends T>... modifiers) {
		this.duration = new FixedDuration(Duration.PERMANENT);
		this.source = source;
		
		this.modifiers = new ArrayList<Modifier<? extends T>>(Arrays.asList(modifiers));
	}
	
	@SafeVarargs
	public ModifierGroup(Object source, Duration duration, Modifier<? extends T>... modifiers) {
		this.duration = Duration.copy(duration);
		this.source = source;
		
		this.modifiers = new ArrayList<Modifier<? extends T>>(Arrays.asList(modifiers));
	}
	
	public ArrayList<Modifier<? extends T>> getModifiers() {
		return modifiers;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public Object getSource() {
		return source;
	}
}
