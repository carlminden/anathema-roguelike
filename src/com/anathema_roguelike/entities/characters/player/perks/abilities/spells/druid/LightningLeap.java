package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.LocationTargeted;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.Passable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.LongRange;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.NearbyTargeted;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.entities.characters.player.perks.specializations.SpecializationCalculation;
import com.anathema_roguelike.environment.Location;

public class LightningLeap extends Spell<GenericTargetedPerk<Targetable, Location>> {

	public LightningLeap() {
		super(4, Druid.class);
	}

	@Override
	protected GenericTargetedPerk<Targetable, Location> createPerk() {
		
		LongRange<Location> range = new LongRange<>(Location.class, new LineOfSight<>(), new LineOfEffect<>());
		
		return new GenericTargetedPerk<Targetable, Location>(
			"Lightning Leap",
			new NearbyTargeted(Location.class, range, (l) -> getShape(l), new Passable()), new LocationTargeted<>(Targetable.class)) {

				@Override
				protected TargetedAction<Targetable, Location> createAction() {
					// TODO Auto-generated method stub
					return null;
				}
			
		};
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
