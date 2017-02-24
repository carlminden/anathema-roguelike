package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.main.display.Color;

import squidpony.squidgrid.gui.gdx.SColor;

public enum VisibilityLevel {
	IMPERCEPTIBLE("Imperceptible", Color.WHITE),
	CONCEALED("Concealed", Color.WHITE),
	PARTIALLYCONCEALED("Partially Concealed", Color.YELLOW),
	VISIBLE("Visible", Color.RED),
	EXPOSED("Exposed", Color.RED);
	
	private SColor color;
	private String name;
	
	VisibilityLevel(String name, SColor color) {
		this.name = name;
		this.color = color;
	}
	
	public SColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
}