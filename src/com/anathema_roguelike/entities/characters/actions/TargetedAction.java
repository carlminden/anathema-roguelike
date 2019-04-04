package com.anathema_roguelike.entities.characters.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.ActionEffectModificationPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public class TargetedAction<TargetType extends Targetable, OriginType extends Targetable> extends CharacterAction {
	
	private ArrayList<TargetEffect<? extends TargetType, ?>> targetEffects;
	
	private OriginType origin;
	private Collection<? extends TargetType> targets;
	
	@SafeVarargs
	public TargetedAction(Character character, OriginType origin, Collection<? extends TargetType> targets, EnergyCost energyCost, ActionCosts costs, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		this(character, energyCost, costs, targetEffects);
		
		this.origin = origin;
		this.targets = targets;
	}
	
	@SafeVarargs
	public TargetedAction(Character character, EnergyCost energyCost, ActionCosts costs, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(character, energyCost, costs);
		
		this.targetEffects = new ArrayList<>(Arrays.asList(targetEffects));
	}
	
	@SafeVarargs
	public TargetedAction(Character character, OriginType origin, Collection<? extends TargetType> targets, EnergyCost energyCost, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		this(character, energyCost, targetEffects);
		
		this.origin = origin;
		this.targets = targets;
	}
	
	@SafeVarargs
	public TargetedAction(Character character, EnergyCost energyCost, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(character, energyCost);
		
		this.targetEffects = new ArrayList<>(Arrays.asList(targetEffects));
	}
	
	public void setOrigin(OriginType origin) {
		this.origin = origin;
	}
	
	public void setTargets(Collection<? extends TargetType> targets) {
		this.targets = targets;
	}
	
	public OriginType getOrigin() {
		return origin;
	}
	
	public Collection<? extends TargetType> getTargets() {
		return targets;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onTake() {
		if(origin == null || targets == null) {
			throw new RuntimeException("Invalid Targeted CharacterAction, Origin or Targets not set");
		}
		
		for(Targetable target : targets) {
			for(TargetEffect<? extends TargetType, ?> te : targetEffects) {
				if(te.getTargetType().isAssignableFrom(target.getClass())) {
					
					TargetEffect<? extends TargetType, ?> modified = te;
					for(ActionEffectModificationPerk<?, ?> aem : getActor().getPerks(ActionEffectModificationPerk.class)) {
						if(aem.getActionType().isAssignableFrom(getClass()) && te.getTargetType().isAssignableFrom(target.getClass())) {
							modified = aem.getClass().cast(aem).modify(this, modified);
						}
					}
					
					te.getClass().cast(te).applyTo(target);
				}
			}
		}
	}
	
	protected void addTargetEffect(TargetEffect<? extends TargetType, ?> te) {
		targetEffects.add(te);
	}
}
