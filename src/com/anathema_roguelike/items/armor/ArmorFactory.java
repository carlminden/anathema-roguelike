package com.anathema_roguelike.items.armor;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.anathema_roguelike.items.ItemFactory;
import com.anathema_roguelike.items.ItemPropertyCache;
import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.main.utilities.Utils;

public class ArmorFactory extends ItemFactory<Armor> {
	
	public ArmorFactory() {
		Utils.getSubclasses(Armor.class).forEach(t -> {
			addFactory(new ItemFactory<Armor>() {

				@Override
				public Class<? extends ItemType<? extends Armor>> getSupportedType() {
					return (Class<? extends ItemType<? extends Armor>>) t;
				}
				
				@Override
				public Armor generate() {
					ArmorType type = ItemPropertyCache.getProperty(ArmorType.class, t.getSimpleName());
					ArmorMaterial material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(ArmorMaterial.class));
					
					t.getConstructors();
					
					try {
						return t.getConstructor(new Class[] { Optional.class, ArmorType.class, ArmorMaterial.class }).newInstance(Optional.empty(), type, material);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						throw new RuntimeException(e);
					}
				}
			});
		});
	}
	
	@Override
	public Class<? extends ItemType<? extends Armor>> getSupportedType() {
		return ArmorType.class;
	}
}
