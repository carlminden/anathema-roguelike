package com.anathema_roguelike.items.weapons.natural_weapons;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;

public class UnarmedWeaponMaterial extends NaturalWeaponMaterial {

	public UnarmedWeaponMaterial() {
		super("Unarmed");
	}
	
	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		Optional<Effect<EquippableItem, ItemStat>> effect = super.getEffect();
		effect.ifPresent(e -> e.addModifier(new Modifier<>(BaseWeaponDamage.class, AdditiveCalculation.build(
				() -> {
					Optional<Character> wearer = e.getTarget().getWearer();
					
					return wearer.isPresent() ? wearer.get().getLevel() : 0.0;
				})
			)));
		
		return effect;
	}

}
