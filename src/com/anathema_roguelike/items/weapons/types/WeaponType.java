package com.anathema_roguelike.items.weapons.types;

import java.util.Optional;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.items.weapons.Weapon;
import com.anathema_roguelike.items.weapons.WeaponMaterial;
import com.anathema_roguelike.items.weapons.WeaponProperty;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.WeaponRange;
import com.anathema_roguelike.stats.itemstats.WeaponSpeed;
import com.anathema_roguelike.stats.itemstats.Weight;
import com.univocity.parsers.annotations.Parsed;

public abstract class WeaponType extends WeaponProperty implements ItemType<Weapon> {
	
	@Parsed(field = "Attack Speed")
	private double attackSpeed;
	
	@Parsed(field = "Damage")
	private double damage;
	
	public WeaponType() {
		super();
	}
	
	public WeaponType(String name, double weight, double attackSpeed, double damage) {
		super(name, weight);
		this.attackSpeed = attackSpeed;
		this.damage = damage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public abstract Class<? extends WeaponMaterial> getMaterialType();
	
	public abstract double getRange();
	
	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<WeaponSpeed>(WeaponSpeed.class, AdditiveCalculation.build(() -> getAttackSpeed())),
				new Modifier<BaseWeaponDamage>(BaseWeaponDamage.class, AdditiveCalculation.build(() -> getDamage())),
				new Modifier<WeaponRange>(WeaponRange.class, AdditiveCalculation.build(() -> getRange())),
				new Modifier<Weight>(Weight.class, AdditiveCalculation.build(() -> getWeight()))
		) {});
	}
}
