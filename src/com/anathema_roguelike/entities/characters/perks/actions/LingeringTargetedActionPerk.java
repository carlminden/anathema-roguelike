package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.Optional;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.CharacterAction;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range;

public abstract class LingeringTargetedActionPerk<TargetType extends Targetable, OriginType extends Targetable> extends GenericTargetedPerk<TargetType, OriginType> {
	
	private Duration duration;
	private ActionCosts activationCosts;
	private EnergyCost activationEnergyCost;
	
	public LingeringTargetedActionPerk(String name, Duration duration, Range<OriginType> range, TargetingStrategy<? extends TargetType, OriginType> strategy,
			EnergyCost activationEnergyCost, ActionCost ...activationCosts) {
		super(name, range, strategy);
		
		this.duration = duration;
		this.activationCosts = new ActionCosts(activationCosts);
	}
	
	@Override
	public Optional<Action<Character>> activate() {
		return Optional.of(new CharacterAction(getCharacter(), activationEnergyCost, activationCosts) {

			@Override
			protected void onTake() {
				new LingeringActivation(duration) {

					@Override
					protected Action<?> createLingeringAction() {
						return LingeringTargetedActionPerk.super.activate().get();
					}
				};
			}
		});
	}
}
