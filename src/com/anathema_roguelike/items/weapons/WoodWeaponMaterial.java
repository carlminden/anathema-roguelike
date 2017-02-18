package com.anathema_roguelike.items.weapons;

import java.util.Optional;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.WeaponRange;
import com.anathema_roguelike.stats.itemstats.Weight;
import com.univocity.parsers.annotations.Parsed;

public class WoodWeaponMaterial extends WeaponMaterial {
	
	@Parsed(field = "Range")
	private double range;
	
	public double getRange() {
		return range;
	}
	
	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<BaseWeaponDamage>(BaseWeaponDamage.class, MultiplicativeCalculation.build(() -> getDamage())),
				new Modifier<WeaponRange>(WeaponRange.class, MultiplicativeCalculation.build(() -> getRange())),
				new Modifier<Weight>(Weight.class, MultiplicativeCalculation.build(() -> getWeight()))
		) {});
	}
}
