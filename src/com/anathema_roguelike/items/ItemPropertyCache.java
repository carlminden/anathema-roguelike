package com.anathema_roguelike.items;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.anathema_roguelike.items.armor.ArmorMaterial;
import com.anathema_roguelike.items.armor.ArmorType;
import com.anathema_roguelike.items.weapons.MetalWeaponMaterial;
import com.anathema_roguelike.items.weapons.WoodWeaponMaterial;
import com.anathema_roguelike.items.weapons.types.BluntWeapon;
import com.anathema_roguelike.items.weapons.types.Bow;
import com.anathema_roguelike.items.weapons.types.Crossbow;
import com.anathema_roguelike.items.weapons.types.LongBlade;
import com.anathema_roguelike.items.weapons.types.ShortBlade;
import com.anathema_roguelike.items.weapons.types.Spear;
import com.anathema_roguelike.items.weapons.types.ThrowingWeapon;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ImmutableTable.Builder;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public abstract class ItemPropertyCache {

	public static ImmutableTable<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> propertyCache;

	static {

		Builder<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> builder = ImmutableTable
				.<Class<? extends ItemProperty<?>>, String, ItemProperty<?>> builder();

		loadPropertyFile(builder, ArmorMaterial.class, "res/items/materials/armor_materials.csv");
		loadPropertyFile(builder, ArmorType.class, "res/items/armor_types.csv");

		loadPropertyFile(builder, MetalWeaponMaterial.class, "res/items/materials/metal_weapon_materials.csv");
		loadPropertyFile(builder, WoodWeaponMaterial.class, "res/items/materials/wood_weapon_materials.csv");

		loadPropertyFile(builder, ShortBlade.class, "res/items/weapon_types/short_blade.csv");
		loadPropertyFile(builder, LongBlade.class, "res/items/weapon_types/long_blade.csv");
		loadPropertyFile(builder, BluntWeapon.class, "res/items/weapon_types/blunt.csv");
		loadPropertyFile(builder, Spear.class, "res/items/weapon_types/spear.csv");
		loadPropertyFile(builder, Bow.class, "res/items/weapon_types/bow.csv");
		loadPropertyFile(builder, Crossbow.class, "res/items/weapon_types/crossbow.csv");
		loadPropertyFile(builder, ThrowingWeapon.class, "res/items/weapon_types/throwing.csv");

		propertyCache = builder.build();
	}

	static int i = 0;

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
			String filename) {
		
		BeanListProcessor<T> rowProcessor = new BeanListProcessor<T>(type);

		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);

		CsvParser parser = new CsvParser(parserSettings);
		try {
			parser.parse(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load Item Property file: " + filename);
		}

		List<T> properties = rowProcessor.getBeans();
		
		if(properties.isEmpty()) {
			//CSV parsing library is unreliable, should get a better solution, but for now just try again
			loadPropertyFile(builder, type, filename);
		} else {
			properties.forEach(p -> {
				builder.put(type, p.getName(), p);
	
			});
		}
	}

	public static void main(String[] args) {
		ItemPropertyCache.getProperties(Spear.class).forEach(p -> System.out.println(p.getName()));
	}
}
