package com.anathema_roguelike.main.animations;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.PanelEffect;
import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidgrid.gui.gdx.SparseLayers;
import squidpony.squidgrid.gui.gdx.SparseTextMap;

public abstract class Animation implements HasPosition {
	
	
	private PanelEffect panelEffect;
	SparseTextMap target;
	private float duration;
	private HasPosition position;
	private HasPosition offset;
	private boolean finished = false;

	public Animation(HasPosition position, float duration) {
		this.position = position;
		this.duration = duration;
	}
	
	protected abstract void update(float percent);
	
	public void create(DisplayLayer layer) {
		create(layer, new Point(0, 0));
	}
	
	public void create(DisplayLayer layer, HasPosition offset) {
		this.offset = offset;
		this.target = Game.getInstance().getDisplay().getLayer(layer);
		SparseLayers layerGroup = Game.getInstance().getDisplay().getLayerGroup(layer);
		
		panelEffect = new PanelEffect(layerGroup, duration){
			
			@Override
			protected void update(float percent) {
				Animation.this.update(percent);
			}
			
			@Override
			protected void end() {
				Animation.this.end();
				super.end();
			}
			
			@Override
			public boolean act(float delta) {
				if(finished) {
					return true;
				} else {
					return super.act(delta);
				}
			}
			
		};
		
		layerGroup.addAction(panelEffect);
	}
	
	protected void end() {
		
	}
	
	@Override
	public Point getPosition() {
		return new Point(position.getX() + offset.getX(), position.getY() + offset.getY());
	}
	
	public void moveTo(Point position) {
		this.position = position;
	}
	
	public void finish() {
		finished = true;
	}
	
	protected PanelEffect getPanelEffect() {
		return panelEffect;
	}
	
	protected void renderChar(Point p, char c, SColor color) {
		target.place(p.getX(), p.getY(), c, color);
	}

	public void setOffset(HasPosition offset) {
		this.offset = offset;
	}
}
