package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.NearbyTargeted;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.Passable;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.LongRange;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.characters.player.perks.specializations.SpecializationCalculation;
import com.anathema_roguelike.environment.Location;

public class LightningLeap extends Spell<TargetedPerk<Location, Location>> {

	public LightningLeap() {
		super(4, Druid.class);
	}

	@Override
	protected TargetedPerk<Location, Location> createPerk() {
		return new TargetedPerk<Location, Location>(
			"Lightning Leap",
			new LongRange<>(Location.class, new LineOfSight<>(), new LineOfEffect<>()),
			new NearbyTargeted<>(Location.class, (l) -> getShape(l), new Passable()),
			new TargetConsumer<Location>(Location.class) {

				@Override
				public void consume(Location target) {
					// TODO Auto-generated method stub
					
				}
		});
	}
	
	private Shape getShape(Location target) {
		return new SpecializationCalculation<Shape>(this) {

			@Override
			protected Shape zero() {
				return new Circle(target, () -> 3.0);
			}

			@Override
			protected Shape one() {
				return new Circle(target, () -> 3.0);
			}

			@Override
			protected Shape two() {
				return new Circle(target, () -> 3.0);
			}

			@Override
			protected Shape three() {
				return new Circle(target, () -> 3.0);
			}
		}.get();
	}
}
