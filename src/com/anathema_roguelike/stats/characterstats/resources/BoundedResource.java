package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class BoundedResource extends Resource {
	
	private boolean initiallyFull;
	private boolean initialized = false;
	
	public BoundedResource(Character character, boolean initiallyFull) {
		super(character);
		
		this.initiallyFull = initiallyFull;
	}
	
	@Override
	public double getAmount() {
		if(!initialized) {
			initialized = true;
			
			if(initiallyFull) {
				set(null, null, getMaximum());
			}
		}
		
		return super.getAmount();
	}
	
	@Override
	public void set(Character initiator, HasEffect<? extends Effect<Character, ?>> source, int amount) {
		super.set(initiator, source, Utils.clamp(amount, 0, getMaximum()));
	}
	
	public void reset() {
		set(null, null, getMaximum());
	}
	
	public abstract int getMaximum();
}
