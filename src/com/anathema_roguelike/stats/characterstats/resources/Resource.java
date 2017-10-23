/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.stats.characterstats.resources;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.entities.characters.player.Player;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class Resource extends CharacterStat {
	
	private int amount = 0;
	
	public Resource(Character character) {
		super(character);
		
	}
	
	public void modify(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, int amount) {
		set(initiator, source, (int) (getAmount() + amount));
	}
	@Override
	public double getAmount() {
		return amount;
	}
	
	public void set(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, int amount) {
		
		int currentAmount = (int) getAmount();
		
		int difference = amount - currentAmount;
		
		getObject().postEvent(new ResourceChangedEvent(source, getClass(), difference));
		
		if(initiator.isPresent()) {
			if(difference > 0) {
				printResourceGainedMessage(initiator, source, getObject(), difference);
			} else if(difference < 0) {
				printResourceLostMessage(initiator, source, getObject(), Math.abs(difference));
			}
		}
		
		this.amount = amount;
	}

	protected void printResourceGainedMessage(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Character target, int amount) {
		
		if(getObject() instanceof Player) {
			Message m = new Message("You gain " + amount + " pointSet of " + Utils.getName(this), Color.GREEN);
			
			initiator.ifPresent(i -> {
				if(i instanceof Player) {
					m.appendMessage(" from your ", Color.GREEN);
				} else {
					m.appendMessage(" from the " + Utils.getName(i) + "'s ", Color.GREEN);
				}
			});
			
			m.appendMessage(Utils.getName(source), Color.GREEN);
			
			Game.getInstance().getUserInterface().addMessage(m);
		} else if(initiator.isPresent() && initiator.get() instanceof Player) {
			Message m = new Message("You grant " + amount + " pointSet of " + Utils.getName(this), Color.GREEN);
			
			m.appendMessage(" to the " + Utils.getName(target));
			
			source.ifPresent(s -> m.appendMessage(" with your " + Utils.getName(s), Color.GREEN));
			
			Game.getInstance().getUserInterface().addMessage(m);
		}
	}
	
	protected void printResourceLostMessage(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Character target, int amount) {
		if(getObject() instanceof Player) {
			Message m = new Message("You lose " + amount + " pointSet of " + Utils.getName(this), Color.RED);
			
			initiator.ifPresent(i -> {
				if(i instanceof Player) {
					m.appendMessage(" from your ", Color.RED);
				} else {
					m.appendMessage(" from the " + Utils.getName(i) + "'s ", Color.RED);
				}
			});
			
			source.ifPresent(s -> m.appendMessage(Utils.getName(s), Color.RED));
			
			Game.getInstance().getUserInterface().addMessage(m);
		} else if(initiator.isPresent() && initiator.get() instanceof Player) {
			Message m = new Message("You deal " + amount + " pointSet of " + Utils.getName(this));
			
			m.appendMessage(" damage to the " + Utils.getName(target));
			
			source.ifPresent(s -> m.appendMessage(" with your " + Utils.getName(s)));
			
			Game.getInstance().getUserInterface().addMessage(m);
		}
	}
}
