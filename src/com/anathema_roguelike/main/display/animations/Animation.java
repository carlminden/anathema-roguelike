/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike.main.display.animations;

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
