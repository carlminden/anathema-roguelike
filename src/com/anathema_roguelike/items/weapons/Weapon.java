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

import java.util.Optional;
import java.util.Random;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.secondarystats.Accuracy;
import com.anathema_roguelike.stats.characterstats.secondarystats.BonusWeaponDamage;
import com.anathema_roguelike.stats.characterstats.secondarystats.WeaponDamageMultiplier;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.Weight;

public class Weapon extends EquippableItem {
	
	private WeaponType type;
	private WeaponMaterial material;
	
	public Weapon(Optional<VisualRepresentation> representation, WeaponType type, WeaponMaterial material) {
		super(representation);
		
		this.type = type;
		this.material = material;
		
		applyEffect(type.getEffect());
		applyEffect(material.getEffect());
	}
	
	public WeaponType getType() {
		return type;
	}
	
	public WeaponMaterial getMaterial() {
		return material;
	}
	
	@Override
	public String toString() {
		return material.getName() + " " + type.getName();
	}

	public int getWeaponDamage(Character character) {
		
		Weapon primaryWeapon = character.getInventory().getEquipedItem(PrimaryWeapon.class);
		
		double baseWeaponDamage = primaryWeapon.getStatAmount(BaseWeaponDamage.class);
		double weight = primaryWeapon.getStatAmount(Weight.class);
		
		int bonusWeaponDamage = (int) character.getStatAmount(BonusWeaponDamage.class);
		double weaponDamageMultiplier = character.getStatAmount(WeaponDamageMultiplier.class);
		int accuracy = (int) character.getStatAmount(Accuracy.class);
		
		weaponDamageMultiplier += 0.05 * weight;
		
		accuracy = Math.min(50, accuracy);
		
		double min = -0.25 + (double)accuracy/100;
		double max = 0.25;
		
		Random r = new Random();
		
		double accuracyMultiplier =  1 + min + (max - min) * r.nextDouble();
		
		return (int) (((baseWeaponDamage * accuracyMultiplier) + bonusWeaponDamage) * (weaponDamageMultiplier));
	}
}
