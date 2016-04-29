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

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.reflections.Reflections;

import com.anathema_roguelike.main.display.Color;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

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
	
	public static <T extends Number> T clamp(T n, T l, T h) {
	    return (n.doubleValue() > h.doubleValue() ? h : (n.doubleValue() < l.doubleValue() ? l : n));
	}
	
	public static int roll(int dice, int sides) {
		Random rand = new Random();
		int sum = 0;
		
		for(int i = 0; i < dice; i++) {
			sum += rand.nextInt(sides) + 1;
		}
		
		return sum;
	}
	
	public static Class<?> getSuperclass(Object subclass) {
		
		if(!(subclass instanceof Class<?>)) {
			return subclass.getClass().getSuperclass();
		} else {
			return ((Class<?>)subclass).getSuperclass();
		}
		
		
	}
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass, Class<? extends Annotation> annotation) {
		return getSubclasses(superclass, annotation, Predicates.<Class<? extends T>>alwaysTrue());
	}
	
	public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> superclass, Class<? extends Annotation> annotation, Predicate<Class<? extends T>> predicate) {
		ArrayList<Class<? extends T>> ret = new ArrayList<>();
		
		Set<Class<?>> annotated = getAnnotatedClasses(annotation);
		
		if(subtypeCache.containsKey(superclass)) {
			ret = new ArrayList<>((ArrayList<Class<? extends T>>)subtypeCache.get(superclass));
		} else {
		
			Reflections reflections = new Reflections("com.anathema_roguelike");
	
			Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(superclass);
			
			Collection<Class<? extends T>> intersection = Sets.intersection(subTypes, annotated);
			
			ArrayList<Class<? extends T>> sorted = new ArrayList<>(intersection);
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
	
	public static <T> Collection<Class<? extends T>> getListedSubclasses(Class<T> superclass) {
		return getSubclasses(superclass, Listed.class, Predicates.<Class<? extends T>>alwaysTrue());
	}
	
	public static <T> Collection<Class<? extends T>> getListedSubclasses(Class<T> superclass, Class<? extends Annotation> annotation) {
		return getListedSubclasses(superclass, annotation, Predicates.<Class<? extends T>>alwaysTrue());
	}
	
	public static <T> Collection<Class<? extends T>> getListedSubclasses(Class<T> superclass, final Class<? extends Annotation> annotation, Predicate<Class<? extends T>> predicate) {
		return getSubclasses(superclass, Listed.class, Predicates.and(predicate, new Predicate<Class<? extends T>>() {

			@Override
			public boolean apply(Class<? extends T> cls) {
				return cls.isAnnotationPresent(annotation);
			}
		}));
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
