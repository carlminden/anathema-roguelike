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
package com.anathema_roguelike.items.weapons;

import java.util.Random;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.characters.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.characters.stats.itemstats.WeaponStat;
import com.anathema_roguelike.characters.stats.itemstats.Weight;
import com.anathema_roguelike.characters.stats.secondarystats.Accuracy;
import com.anathema_roguelike.characters.stats.secondarystats.BonusWeaponDamage;
import com.anathema_roguelike.characters.stats.secondarystats.WeaponDamageMultiplier;
import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Weapon extends EquippableItem<WeaponStat> {
	
	public Weapon(VisualRepresentation representation) {
		super(representation);
		
	}
	
	@Override
	public Buff getEffect() {
		Buff buff = super.getEffect();
		
		return buff;
	}
	
	@Override
	public void equip(Character character) {
		super.equip(character);
	}
	
	@Override
	public void remove(Character character) {
		super.remove(character);
	}
	
	public abstract int getRange();

	public int getWeaponDamage(Character character) {
		
		Weapon primaryWeapon = character.getInventory().getEquipedItem(PrimaryWeapon.class);
		
		double baseWeaponDamage = primaryWeapon.getStat(BaseWeaponDamage.class);
		double weight = primaryWeapon.getStat(Weight.class);
		
		int bonusWeaponDamage = character.getModifiedStatScore(BonusWeaponDamage.class);
		double weaponDamageMultiplier = character.getModifiedStatScore(WeaponDamageMultiplier.class);
		int accuracy = character.getModifiedStatScore(Accuracy.class);
		
		weaponDamageMultiplier += 0.05 * weight;
		
		accuracy = Math.min(50, accuracy);
		
		double min = -0.25 + (double)accuracy/100;
		double max = 0.25;
		
		Random r = new Random();
		
		double accuracyMultiplier =  1 + min + (max - min) * r.nextDouble();
		
		return (int) (((baseWeaponDamage * accuracyMultiplier) + bonusWeaponDamage) * (weaponDamageMultiplier));
	}
}
