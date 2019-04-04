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
package com.anathema_roguelike.entities.items;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.anathema_roguelike.entities.items.armor.ArmorMaterial;
import com.anathema_roguelike.entities.items.armor.ArmorType;
import com.anathema_roguelike.entities.items.weapons.MetalWeaponMaterial;
import com.anathema_roguelike.entities.items.weapons.WoodWeaponMaterial;
import com.anathema_roguelike.entities.items.weapons.types.BluntWeapon;
import com.anathema_roguelike.entities.items.weapons.types.Bow;
import com.anathema_roguelike.entities.items.weapons.types.Crossbow;
import com.anathema_roguelike.entities.items.weapons.types.LongBlade;
import com.anathema_roguelike.entities.items.weapons.types.ShortBlade;
import com.anathema_roguelike.entities.items.weapons.types.Spear;
import com.anathema_roguelike.entities.items.weapons.types.ThrowingWeapon;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ImmutableTable.Builder;

public abstract class ItemPropertyCache {

	public static ImmutableTable<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> propertyCache;

	static {
		
		Builder<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> builder = ImmutableTable.builder();

		loadPropertyFile(builder, ArmorMaterial.class, "/items/materials/armor_materials.csv", cellProcessors(4));
		loadPropertyFile(builder, ArmorType.class, "/items/armor_types.csv", cellProcessors(4));

		loadPropertyFile(builder, MetalWeaponMaterial.class, "/items/materials/metal_weapon_materials.csv", cellProcessors(2));
		loadPropertyFile(builder, WoodWeaponMaterial.class, "/items/materials/wood_weapon_materials.csv", cellProcessors(3));

		loadPropertyFile(builder, ShortBlade.class, "/items/weapon_types/short_blade.csv", cellProcessors(3));
		loadPropertyFile(builder, LongBlade.class, "/items/weapon_types/long_blade.csv", cellProcessors(3));
		loadPropertyFile(builder, BluntWeapon.class, "/items/weapon_types/blunt.csv", cellProcessors(3));
		loadPropertyFile(builder, Spear.class, "/items/weapon_types/spear.csv", cellProcessors(3));
		loadPropertyFile(builder, Bow.class, "/items/weapon_types/bow.csv", cellProcessors(4));
		loadPropertyFile(builder, Crossbow.class, "/items/weapon_types/crossbow.csv", cellProcessors(4));
		loadPropertyFile(builder, ThrowingWeapon.class, "/items/weapon_types/throwing.csv", cellProcessors(3));

		propertyCache = builder.build();
	}
	
	private static CellProcessor[] cellProcessors(int columns) {
		ArrayList<CellProcessor> ret = new ArrayList<>();
		
		ret.add(new NotNull());
		
		for(int i = 0; i < columns; i++) {
			ret.add(new ParseDouble());
		}
		
		return ret.toArray(new CellProcessor[ret.size()]);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ItemProperty<?>> Collection<? extends T> getProperties(Class<T> type) {

		Collection<T> properties = new HashSet<>();

		propertyCache.rowKeySet().forEach(r -> {
			if (type.isAssignableFrom(r)) {
				Class<T> row = type.getClass().cast(r);
				properties.addAll((Collection<? extends T>) ItemPropertyCache.propertyCache.row(row).values());
			}
		});

		return properties;
	}

	@SuppressWarnings("unchecked")
	public static <T extends ItemProperty<?>> T getProperty(Class<T> type, String name) {
		return (T) ItemPropertyCache.propertyCache.row(type).get(name);
	}

	@SuppressWarnings("deprecation")
	private static <T extends ItemProperty<?>> void loadPropertyFile(
			Builder<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> builder, Class<T> type,
			String filename, CellProcessor[] processors) {
		
		ICsvBeanReader beanReader = null;
		
        try {
        	InputStreamReader reader = new InputStreamReader(ItemPropertyCache.class.getResourceAsStream(filename));
        	beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
        	String[] header = beanReader.getHeader(true);
        	
        	T obj;
        	while((obj = beanReader.read(type, header, processors)) != null ) {
				builder.put(type, obj.getName(), obj);
			}
		} catch (FileNotFoundException e1) {
			throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( beanReader != null ) {
			    try {
					beanReader.close();
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		}
    }

	public static void main(String[] args) {
		ItemPropertyCache.getProperties(Spear.class).forEach(p -> System.out.println(p.getName()));
	}
}
