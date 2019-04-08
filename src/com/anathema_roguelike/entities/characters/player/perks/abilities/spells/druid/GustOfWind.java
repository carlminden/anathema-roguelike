package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ConalAreaOfEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.VeryShortRange;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class GustOfWind extends Spell<GenericTargetedPerk<Targetable, Location>> {

	public GustOfWind() {
		super(2, Druid.class);
	}

	@Override
	protected GenericTargetedPerk<Targetable, Location> createPerk() {
		return new GenericTargetedPerk<>(
			"Gust of Wind",
			new VeryShortRange<>(Location.class, new LineOfSight<>(), new LineOfEffect<>()),
			new ConalAreaOfEffect<Targetable, Location>(Targetable.class, () -> getSpecializationLevel() == 3 ? 4.0 : 3.0) {

				@Override
				protected Location getOrigin() {
					return getCharacter().getLocation();
				}
			}){

				@Override
				protected TargetedAction<Targetable, Location> createAction() {
					// TODO Auto-generated method stub
					return null;
				}
		};
	}
}
