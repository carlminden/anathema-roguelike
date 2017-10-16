package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.SingleTargeted;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.LongRange;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class FlashFreeze extends Spell<TargetedPerk<Targetable, Targetable>> {

	public FlashFreeze() {
		super(3, Druid.class);
	}

	@Override
	protected TargetedPerk<Targetable, Targetable> createPerk() {
		return new TargetedPerk<Targetable, Targetable>(
			"Flash Freeze",
			new LongRange<>(Targetable.class),
			new SingleTargeted<>(Targetable.class),
			new TargetConsumer<Targetable>(Targetable.class) {

				@Override
				public void consume(Targetable target) {
					// TODO Auto-generated method stub
					
				}
		});
	}
}
