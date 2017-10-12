package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.SingleTargeted;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.ThrowingRange;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.items.miscellaneous.Rock;
import com.anathema_roguelike.stimuli.Sound;

public class ThrowRock extends Technique<TargetedPerk<Location, Location>> {

	public ThrowRock() {
		super(new TargetedPerk<Location, Location>(
				new ThrowingRange<>(Location.class, new Rock(), new LineOfSight<>(), new LineOfEffect<>()),
				new SingleTargeted<>(Location.class),
				new TargetConsumer<Location>(Location.class) {

					@Override
					public void consume(Location target) {
						target.generateStimulus(new Sound(100));
					}
				}));
	}
}
