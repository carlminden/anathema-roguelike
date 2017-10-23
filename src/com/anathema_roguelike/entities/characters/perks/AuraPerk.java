package com.anathema_roguelike.entities.characters.perks;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.LingeringTargetedActionPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Spread;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.SelfOnly;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.PointBlank;
import com.anathema_roguelike.time.Duration;

public abstract class AuraPerk<T extends Targetable> extends LingeringTargetedActionPerk<T, Character> {

	public AuraPerk(String name, Duration duration, Spread<T, Character> strategy, EnergyCost activationEnergyCost, ActionCost ...activationCosts) {
		super(name, duration, new PointBlank(new SelfOnly()), strategy, activationEnergyCost, activationCosts);
	}
}
