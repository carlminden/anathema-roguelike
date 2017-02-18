package com.anathema_roguelike.main.utilities;

public interface HasWeightedProbability {
	
	public default double getWeightedProbability() {
		return 1.0;
	}
}
