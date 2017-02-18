package com.anathema_roguelike.characters.abilities.targetingstrategies.ranges;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Square;
import com.anathema_roguelike.characters.abilities.targetingstrategies.targetmodes.PointsMode;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.stats.effects.FixedCalculation;
import com.anathema_roguelike.stats.itemstats.WeaponRange;

public class PrimaryWeaponRange extends Range {

	public PrimaryWeaponRange() {
		super(new PointsMode());
	}

	@Override
	protected Shape getShape(Character character) {
		double range = character.getInventory().getEquipedItem(PrimaryWeapon.class).getStatAmount(WeaponRange.class);
		
		if(range == 1) {
			return new Square(character.getPosition(), new FixedCalculation(1));
		} else {
			return new Circle(character.getPosition(), new FixedCalculation(range));
		}
	}
}