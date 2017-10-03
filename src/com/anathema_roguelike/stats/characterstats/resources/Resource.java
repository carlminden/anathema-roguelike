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

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
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
	
	public void modify(Character initiator, HasEffect<? extends Effect<Character, ?>> source, int amount) {
		set(initiator, source, (int) (getAmount() + amount));
	}
	@Override
	public double getAmount() {
		return amount;
	}
	
	public void set(Character initiator, HasEffect<? extends Effect<Character, ?>> source, int amount) {
		
		int currentAmount = (int) getAmount();
		
		int difference = amount - currentAmount;
		
		getObject().postEvent(new ResourceChangedEvent(source, getClass(), difference));
		
		if(initiator != null) {
			if(difference > 0) {
				printResourceGainedMessage(initiator, source, getObject(), difference);
			} else if(difference < 0) {
				printResourceLostMessage(initiator, source, getObject(), Math.abs(difference));
			}
		}
		
		this.amount = amount;
	}

	protected void printResourceGainedMessage(Character initiator, HasEffect<? extends Effect<Character, ?>> source, Character target, int amount) {
		
		if(getObject() instanceof Player) {
			Message m = new Message("You gain " + amount + " points of " + Utils.getName(this), Color.GREEN);
			
			if(initiator instanceof Player) {
				m.appendMessage(" from your ", Color.GREEN);
			} else {
				m.appendMessage(" from the " + Utils.getName(initiator) + "'s ", Color.GREEN);
			}
			
			m.appendMessage(Utils.getName(source), Color.GREEN);
			
			Game.getInstance().getUserInterface().addMessage(m);
		} else if(initiator instanceof Player) {
			Message m = new Message("You grant " + amount + " points of " + Utils.getName(this), Color.GREEN);
			
			m.appendMessage(" to the " + Utils.getName(target) + " with your " + Utils.getName(source), Color.GREEN);
			
			Game.getInstance().getUserInterface().addMessage(m);
		}
	}
	
	protected void printResourceLostMessage(Character initiator, HasEffect<? extends Effect<Character, ?>> source, Character target, int amount) {
		if(getObject() instanceof Player) {
			Message m = new Message("You lose " + amount + " points of " + Utils.getName(this), Color.RED);
			
			if(initiator instanceof Player) {
				m.appendMessage(" from your ", Color.RED);
			} else {
				m.appendMessage(" from the " + Utils.getName(initiator) + "'s ", Color.RED);
			}
			
			m.appendMessage(Utils.getName(source), Color.RED);
			
			Game.getInstance().getUserInterface().addMessage(m);
		} else if(initiator instanceof Player) {
			Message m = new Message("You deal " + amount + " points of " + Utils.getName(this));
			
			m.appendMessage(" damage to the " + Utils.getName(target) + " with your " + Utils.getName(source));
			
			Game.getInstance().getUserInterface().addMessage(m);
		}
	}
}
