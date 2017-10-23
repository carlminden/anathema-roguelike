package com.anathema_roguelike.main.display.animations;

import com.anathema_roguelike.main.utilities.position.Point;

public abstract class PersistentAnimation extends Animation {

	protected PersistentAnimation(Point position, float duration) {
		super(position, duration);
	}
	
	@Override
	protected void end() {
		getPanelEffect().restart();
	}
}
