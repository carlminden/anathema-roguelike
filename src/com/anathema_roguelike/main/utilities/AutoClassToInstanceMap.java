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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

public class AutoClassToInstanceMap<T> {
	
	private ClassToInstanceMap<T> classToInstanceMap = MutableClassToInstanceMap.create();
	
	public AutoClassToInstanceMap(Class<T> type, Class<?>[] parameterTypes, Object... args) {
		try {
			for(Class<? extends T> t : Utils.getSubclasses(type)) {
				classToInstanceMap.put(t, t.getConstructor(parameterTypes).newInstance(args));
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public AutoClassToInstanceMap(Class<T> type) {
		try {
			for(Class<? extends T> t : Utils.getSubclasses(type)) {
				classToInstanceMap.put(t, t.getConstructor().newInstance());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public <I extends T> I get(Class<? extends I> key) {
		return classToInstanceMap.getInstance(key);
	}
	
	public Collection<T> getValues() {
		return classToInstanceMap.values();
	}
}
