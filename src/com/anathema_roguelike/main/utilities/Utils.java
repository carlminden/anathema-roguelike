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

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.reflections.Reflections;

import com.anathema_roguelike.main.display.Color;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import gigadot.rebound.Rebound;
import squidpony.squidgrid.gui.gdx.SColor;

public class Utils {
	
	private static HashMap<Class<?>, ArrayList<?>> subtypeCache = new HashMap<>();
	private static HashMap<Class<? extends Annotation>, Set<Class<?>>> annotationCache = new HashMap<>();
	
	public static Properties names;
	public static Properties descriptions;
	public static Properties colors;
	
	static {
		try {
			names = new Properties();
			descriptions = new Properties();
			colors = new Properties();
			
			names.load(new FileInputStream("res/names.properties"));
			descriptions.load(new FileInputStream("res/descriptions.properties"));
			colors.load(new FileInputStream("res/colors.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T extends HasWeightedProbability> T getWeightedRandomSample(Collection<T> col) {
		ArrayList<Pair<T, Double>> list = new ArrayList<>();
		
		col.forEach(i -> {
			list.add(new Pair<>(i, i.getWeightedProbability()));
		});
		
		return new EnumeratedDistribution<>(list).sample();
	}
	
	public static <T extends Number> T clamp(T n, T l, T h) {
	    return (n.doubleValue() > h.doubleValue() ? h : (n.doubleValue() < l.doubleValue() ? l : n));
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    return map.entrySet()
			.stream()
			.filter(entry -> Objects.equals(entry.getValue(), value))
			.map(Map.Entry::getKey)
			.collect(Collectors.toSet());
	}
	
	public static Class<?> getSuperclass(Object subclass) {
		
		if(!(subclass instanceof Class<?>)) {
			return subclass.getClass().getSuperclass();
		} else {
			return ((Class<?>)subclass).getSuperclass();
		}
		
		
	}
	
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass) {
		return getSubclasses(superclass, false, false, Predicates.alwaysTrue());
	}
	
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass, boolean includeAbstract) {
		return getSubclasses(superclass, includeAbstract, false, Predicates.alwaysTrue());
	}
	
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass, boolean includeAbstract, boolean includeSuperclass) {
		return getSubclasses(superclass, includeAbstract, includeSuperclass, Predicates.alwaysTrue());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass, boolean includeAbstract, boolean includeSuperclass, Predicate<Class<? extends T>> predicate) {
		ArrayList<Class<? extends T>> ret = new ArrayList<>();
		
		if(subtypeCache.containsKey(superclass)) {
			ret = new ArrayList<>((ArrayList<Class<? extends T>>)subtypeCache.get(superclass));
		} else {
		
			Rebound rebound = new Rebound("com.anathema_roguelike", includeAbstract);
			
			Set<Class<? extends T>> subTypes = rebound.getSubClassesOf(superclass);
			
			if(!includeSuperclass) {
				subTypes.remove(superclass);
			}
			
			ArrayList<Class<? extends T>> sorted = new ArrayList<>(subTypes);
			Collections.sort(sorted,
				new Comparator<Class<? extends T>>() {
					@Override
					public int compare(Class<? extends T> o1, Class<? extends T> o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
			
			subtypeCache.put((Class<?>)superclass, (ArrayList<Class<? extends T>>)sorted);
			
			ret = new ArrayList<>(sorted);
		}
		
		return Collections2.filter(ret, predicate);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> filterBySubclass(Collection<? super T> unfiltered, Class<?> type) {
		return (Collection<T>) Collections2.filter(unfiltered, item -> {
			if(item != null) {
				return (type.isAssignableFrom(item.getClass()));
			} else {
				return false;
			}
		});
	}
	
	public static Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) {
		Set<Class<?>> annotated = null;
		
		if(annotationCache.containsKey(annotation)) {
			annotated = annotationCache.get(annotation);
		} else {
			annotated = new Reflections("com.anathema_roguelike").getTypesAnnotatedWith(annotation);
			
			annotationCache.put(annotation, annotated);
		}
		
		return annotated;
	}
	
	static String getProperty(Properties properties, Object obj, String defaultValue) {
		
		if(!(obj instanceof Class)) {
			
			if(obj instanceof String) {
				return (String)obj;
			}
			
			obj = obj.getClass();
		}
		
		Class<?> cls = (Class<?>)obj;
		
		while(cls.isAnonymousClass()) {
			cls = cls.getEnclosingClass();
		}
		
		String property = properties.getProperty(cls.getSimpleName());
		
		if(property != null) {
			return property;
		} else {
			return defaultValue;
		}
	}
	
	public static String getDescription(Object obj) {
		
		if(obj instanceof String || obj.equals(String.class)) {
			return "";
		}
		
		return getProperty(descriptions, obj, getName(obj) + " MISSING DESCRIPTION");
	}

	public static String getName(Object obj) {
		
		Class<?> cls = classify(obj);
		
		String defaultValue = cls.getSimpleName();
		
		return getProperty(names, obj, splitCamelCase(defaultValue));
	}
	
	public static SColor getColor(Object obj) {
		Class<?> cls = classify(obj);
		
		String color = getProperty(colors, obj, "WHITE");
		
		try {
			return (SColor) Color.class.getField(color).get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw (new RuntimeException(color + " is not defined on " + Color.class));
		}
	}
	
	static Class<?> classify(Object obj) {
		Class<?> cls;
		
		if(obj instanceof Class) {
			cls = (Class<?>)obj;
		} else {
			cls = obj.getClass();
		}
		
		while(cls.isAnonymousClass()) {
			cls = cls.getEnclosingClass();
		}
		
		return cls;
	}
	
	//from http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
	static String splitCamelCase(String s) {
		   return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   );
		}
}
