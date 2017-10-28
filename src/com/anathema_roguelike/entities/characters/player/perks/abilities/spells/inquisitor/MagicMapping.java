package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class MagicMapping extends Spell<ActionPerk<Action<Character>>> {

	public MagicMapping() {
		super(3, Inquisitor.class);
	}

	@Override
	protected ActionPerk<Action<Character>> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
