/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
package com.anathema_roguelike.main.ui.uielements;

import java.util.ArrayList;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.display.animations.Animation;
import com.anathema_roguelike.main.display.Outline;
import com.anathema_roguelike.main.display.RenderSurface;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class UIElement extends RenderSurface implements Renderable, HasPosition {
	
	private Point position;
	private Point outerPosition;
	private int outerWidth;
	private int outerHeight;
	private float background;
	private Border border;
	
	private ArrayList<UIElement> elements = new ArrayList<>();
	
	public UIElement(Point position, int width, int height, float background) {
		super(width, height);
		
		init(position, position, width, height, background);
	}
	
	public UIElement(Point position, int width, int height, String borderTitle, float background) {
		super(width - 2, height - ((borderTitle == null) ? 2 : 4));
		
		init(new Point(position.getX() + 1, position.getY() + ((borderTitle == null) ? 1 : 3)), position, width, height, background);
		
		this.border = new Border(this, borderTitle);
	}
	
	private void init(Point position, Point outerPosition, int width, int height, float background) {
		this.position = position;
		this.outerPosition = outerPosition;
		this.outerWidth = width;
		this.outerHeight = height;
		this.background = background;
	}
	
	protected abstract void renderContent();
	
	protected void renderBackground() {
		
		if(border != null) {
			border.render();
		}
		
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				
				if(background == 0f) {
					renderChar(DisplayLayer.UI_BACKGROUND, i, j, ' ', Color.BLACK);
				} else if(background == 1f) {
					renderChar(DisplayLayer.UI_BACKGROUND, i, j, '\u2588', Color.BLACK);
				} else {
					renderChar(DisplayLayer.UI_BACKGROUND, i, j, '\u2588', Color.opacity(Color.BLACK, background));
				}
			}
		}
	}
	
	@Override
	public Point getPosition() {
		return position;
	}
		
	public void addUIElement(UIElement element) {
		elements.add(element);
	}
	
	public void removeUIElement(UIElement element) {
		elements.remove(element);
	}
	
	public ArrayList<UIElement> getUIElements() {
		return elements;
	}
	
	public int getOuterX() {
		return outerPosition.getX();
	}

	public int getOuterY() {
		return outerPosition.getY();
	}

	public int getOuterWidth() {
		return outerWidth;
	}

	public int getOuterHeight() {
		return outerHeight;
	}
	
	public float getBackground() {
		return background;
	}

	@Override
	public final void render() {
		
		renderBackground();
		renderContent();
		
		for(UIElement element : new ArrayList<>(getUIElements())) {
			element.render();
		}
		
		//occasionally useful UI debugging tool
		
//		renderChar(DisplayLayer.UI_FOREGROUND, 0, 0, 'X', Color.ERROR);
//		renderChar(DisplayLayer.UI_FOREGROUND, 0, getHeight() - 1, 'X', Color.ERROR);
//		renderChar(DisplayLayer.UI_FOREGROUND, getWidth() - 1, 0, 'X', Color.ERROR);
//		renderChar(DisplayLayer.UI_FOREGROUND, getWidth() - 1, getHeight() - 1, 'X', Color.ERROR);
	}
	
	@Override
	public void renderChar(DisplayLayer layer, int x, int y, char string, SColor color) {
		Game.getInstance().getDisplay().renderChar(layer, x + getX(), y + getY(), string, color);
	}
	
	@Override
	public void createAnimation(DisplayLayer layer, Animation animation) {
		super.createAnimation(layer, animation, getPosition());
	}
	
	public void renderOutline(Outline outline) {
		outline.setOffset(new Point(getX(), getY()));
		outline.render();
	}
}
