package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Utils;

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
				set(null, getMaximum());
			}
		}
		
		return super.getAmount();
	}
	
	@Override
	public void set(Object source, int amount) {
		super.set(source, Utils.clamp(amount, 0, getMaximum()));
	}
	
	public abstract int getMaximum();
}
