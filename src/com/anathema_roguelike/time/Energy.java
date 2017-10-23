package com.anathema_roguelike.time;

public class Energy {
	private double energyLevel = 1;
	
	public void energize() {
		energyLevel += 1;
	}
	
	public double getEnergyLevel() {
		return energyLevel;
	}
	
	public void use(double energy) {
		energyLevel -= energy;
	}
}
