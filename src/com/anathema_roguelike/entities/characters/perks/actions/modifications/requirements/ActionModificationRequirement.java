package com.anathema_roguelike.entities.characters.perks.actions.modifications.requirements;

import java.util.function.Function;

import com.anathema_roguelike.entities.characters.actions.CharacterAction;

public interface ActionModificationRequirement<T extends CharacterAction> extends Function<T, Boolean> {
	
}
