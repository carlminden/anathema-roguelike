package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.LongRange;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public class FlashFreeze extends Spell<TargetedPerk<Targetable>> {

	public FlashFreeze() {
		super(3, Druid.class);
	}


	@Override
	protected TargetedPerk<Targetable> createPerk() {
		return new TargetedPerk<Targetable>(
			"Flash Freeze",
			new LongRange<>(Targetable.class),
			new SingleTargeted<>(Targetable.class)){

				@Override
				protected TargetedAction<Targetable, Targetable> createAction() {
					// TODO Auto-generated method stub
					ActionCosts costs = new ActionCosts();
					return new TargetedAction<>(getCharacter(), EnergyCost.STANDARD(getCharacter()), costs,
							new TargetEffect<Location, LocationStat>(Location.class, "Flash Freeze") {
						// TODO Auto-generated method stub
					}
				);
			}
		};
	}
}
