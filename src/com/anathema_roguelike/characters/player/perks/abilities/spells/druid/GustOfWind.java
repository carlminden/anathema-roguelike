package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.ConalAreaOfEffect;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.VeryShortRange;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class GustOfWind extends Spell<TargetedPerk<Targetable, Location>> {

	public GustOfWind() {
		super(2, Druid.class);
	}

	@Override
	protected TargetedPerk<Targetable, Location> createPerk() {
		return new TargetedPerk<Targetable, Location>(
			"Gust of Wind",
			new VeryShortRange<>(Location.class, new LineOfSight<>(), new LineOfEffect<>()),
			new ConalAreaOfEffect<Targetable>(Targetable.class, () -> getSpecializationLevel() == 3 ? 4.0 : 3.0) {

				@Override
				protected Location getOrigin() {
					return getCharacter().getLocation();
				}
			},
			new TargetConsumer<Targetable>(Targetable.class) {

				@Override
				public void consume(Targetable target) {
					// TODO Auto-generated method stub
					
				}
		});
	}
}
