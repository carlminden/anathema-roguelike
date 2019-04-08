package com.anathema_roguelike.entities.characters.perks;

import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.LingeringTargetedActionPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Spread;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public abstract class AuraPerk<T extends Targetable> extends LingeringTargetedActionPerk<T, Character> {

	public AuraPerk(String name, Duration duration, Spread<T, Character> strategy, EnergyCost activationEnergyCost, ActionCost ...activationCosts) {
		super(name, duration, new PointBlank(new SelfOnly()), strategy, activationEnergyCost, activationCosts);
	}
}
