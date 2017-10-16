package com.anathema_roguelike.main.animations;

import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Ring;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.utilities.position.HasPosition;

public class Ripple extends Animation {
	
	private double radius;
	
	public Ripple(HasPosition position, double radius, float duration) {
		super(position, duration);
		
		this.radius = radius;
	}

	@Override
	protected void update(float percent) {
		
		double currentRadius = radius * percent;
		
		drawRing(currentRadius, 0.05f);
		drawRing(currentRadius + 1, 0.025f);
		
		if(currentRadius > 1) {
			drawRing(currentRadius - 1, 0.025f);
		}
		
	}
	
	private void drawRing(double radius, float opacity) {
		new Ring(getPosition(), () -> radius).getPoints().forEach(p -> {
			renderChar(p, '\u2588', Color.opacity(Color.WHITE, opacity));
		});
	}

}
