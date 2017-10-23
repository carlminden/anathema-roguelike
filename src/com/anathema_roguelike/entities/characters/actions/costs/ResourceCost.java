package com.anathema_roguelike.entities.characters.actions.costs;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.characterstats.resources.ResourceModification;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public class ResourceCost<T extends Resource> extends CharacterActionCost implements HasEffect<Effect<Character, ? extends CharacterStat>> {
	
	private Class<T> resource;
	private int amount;
	
	public ResourceCost(Character character, Class<T> resource, int amount) {
		super(character);
		
		this.resource = resource;
		this.amount = amount;
	}

	@Override
	public void pay() {
		getEffect().ifPresent(e -> e.applyTo(getCharacter()));
	}
	
	@Override
	public Optional<Effect<Character, ? extends CharacterStat>> getEffect() {
		return Optional.of(new ResourceModification<Resource>(Optional.empty(), Optional.of(this), resource, amount));
	}
	
	public Class<T> getResource() {
		return resource;
	}
	
	public int getAmount() {
		return amount;
	}
}