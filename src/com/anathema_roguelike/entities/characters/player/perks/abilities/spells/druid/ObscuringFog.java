package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class ObscuringFog extends Spell<ActionPerk<Action<Character>>> {

	public ObscuringFog() {
		super(4, Druid.class);
	}

	@Override
	protected ActionPerk<Action<Character>> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
