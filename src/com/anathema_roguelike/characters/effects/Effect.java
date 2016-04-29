/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters.effects;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.anathema_roguelike.characters.Character;

public abstract class Effect {
	private Duration duration;
	private Object source;
	private Character affectedCharacter;
	private Callable<Void> onCancelCallback;
	
	
	private ArrayList<Effect> aditionalEffects = new ArrayList<>();
	
	public Effect(Object source) {
		this.duration = new FixedDuration(Duration.PERMANENT);
		this.source = source;
	}
	
	public Effect(Object source, Duration duration) {
		this.duration = Duration.copy(duration);
		this.source = source;
	}
	
	public Calculation<Integer> onePerAffectedCharacterLevel(){
		return new Calculation<Integer>(){

			@Override
			public Integer calculate() {
				return getAffectedCharacter().getLevel();
			}
		};
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public Object getSource() {
		return source;
	}
	
	public Character getAffectedCharacter() {
		return affectedCharacter;
	}
	
	public void setOnCancelCallback(Callable<Void> onCancelCallback) {
		this.onCancelCallback = onCancelCallback;
	}
	
	public void cancel() {
		if(onCancelCallback != null) {
			try {
				onCancelCallback.call();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void activate(Character character) {
		this.affectedCharacter = character;
		
		getDuration().activate();
	}
	
	public void addAditionalEffect(Effect effect) {
		aditionalEffects.add(effect);
	}
	
	public final void applyTo(EffectSet effectSet) {
		applyThis(effectSet);
		
		for(Effect effect : aditionalEffects) {
			effect.applyThis(effectSet);
		}
	}
	
	public final void removeFrom(EffectSet effectSet) {
		removeThis(effectSet);
		
		for(Effect effect : aditionalEffects) {
			effect.removeThis(effectSet);
		}
	}
	
	protected abstract void applyThis(EffectSet effectSet);
	protected abstract void removeThis(EffectSet effectSet);
}
