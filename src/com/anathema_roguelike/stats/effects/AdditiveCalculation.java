package com.anathema_roguelike.stats.effects;

public interface AdditiveCalculation extends Calculation {
	
	public static AdditiveCalculation build(AdditiveCalculation ac) {
		return ac;
	}
}
