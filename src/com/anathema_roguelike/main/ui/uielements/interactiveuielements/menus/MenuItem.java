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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus;

import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MouseCallback;
import com.anathema_roguelike.main.utilities.Utils;

import squidpony.squidgrid.gui.gdx.SColor;


public class MenuItem<T> extends InteractiveUIElement<T> {
	
	private OnSelectListener<T> onSelectListener;
	private T item;
	private Menu<?> menu;
	private boolean focused = false;
	
	public MenuItem(Menu<?> menu, T item, int x, int y, float background) {
		super(x, y, Utils.getName(item).length(), 1, false, background);
		this.item = item;
		this.menu = menu;
		
		this.onSelectListener = (T obj) -> {
			throw new RuntimeException("Unhandled Menu Option: " + obj);
		};
	}
	
	public MenuItem(Menu<?> menu, T item, OnSelectListener<T> onSelectListener, int x, int y, float background) {
		super(x, y, Utils.getName(item).length(), 1, false, background);
		this.onSelectListener = onSelectListener;
		
		this.item = item;
		this.menu = menu;
	}
	
	public String getText() {
		return Utils.getName(item);
	}
	
	public void setOnSelectListener(OnSelectListener<T> listener) {
		onSelectListener = listener;
	}
	
	public void select() {
		if(onSelectListener != null) {
			onSelectListener.onSelect(item);
		}
	}
	
	public void focus() {
		focused = true;
	}
	
	public void unfocus() {
		focused = false;
	}

	public T getItem() {
		return item;
	}

	@Override
	public void registerMouseCallbacks() {
		MouseCallback callback = new MouseCallback() {
			
			@Override
			public void onMouseover() {
				menu.setFocused(MenuItem.this);
			}
			
			@Override
			public void onClick() {
				menu.setFocused(MenuItem.this);
				select();
			}
		};
		
		for(int i = 0; i < getWidth(); i++) {
			Game.getInstance().getInput().registerMouseCallback(callback, new Point(getX() + i, getY()));
		}
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean processScrollEvent(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void renderContent() {
		
		SColor foreground;
		SColor background;
		
		if(focused) {
			background = Color.WHITE;
			foreground = Color.BLACK;
        } else {
        	background = Color.BLACK;
			foreground = Color.WHITE;
        }
		
    	renderString(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, 0, 0, getText(), foreground, background);
	}
}
