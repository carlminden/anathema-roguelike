package com.anathema_roguelike.entities.characters.perks.requirements;

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public abstract class SelectedTargetRequirement<TargetType extends Targetable, OriginType extends Targetable> extends PerkRequirement {
	
	Range<OriginType> range;
	
	public SelectedTargetRequirement(GenericTargetedPerk<TargetType, OriginType> perk, Range<OriginType> range) {
		super(perk);
		
		this.range = range;
	}
	
	protected abstract void targeted(OriginType target);

	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				OriginType target = range.getTarget(getPerk().getCharacter());
				targeted(target);
				return target != null;
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return Utils.getName(getPerk()) + " activation canceled, no Target was selected";
	}

}
