package com.anathema_roguelike.entities.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.ThrowingRange;
import com.anathema_roguelike.entities.characters.stimuli.Sound;
import com.anathema_roguelike.entities.items.miscellaneous.Rock;
import com.anathema_roguelike.environment.Location;

public class ThrowRock extends Technique<TargetedPerk<Location>> {

	public ThrowRock() {
		super();
	}

	@Override
	protected TargetedPerk<Location> createPerk() {
		return new TargetedPerk<Location>("Throw Rock",
			new ThrowingRange<>(Location.class, new Rock(), new LineOfSight<>(), new LineOfEffect<>()),
			new SingleTargeted<>(Location.class)){

				@Override
				protected TargetedAction<Location, Location> createAction() {
					return new TargetedAction<Location, Location>(getCharacter(), EnergyCost.STANDARD(getCharacter())) {
						@Override
						public void onTake() {
							super.onTake();
							getOrigin().generateStimulus(new Sound(100, getActor()));
						}
					};
				}
		};
	}
}
