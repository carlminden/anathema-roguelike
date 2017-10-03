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

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement;
import com.anathema_roguelike.main.utilities.Utils;

import squidpony.squidgrid.gui.gdx.SquidInput;

//The way I handled the finishItem is horrible
public class Menu<T> extends InteractiveUIElement<T> {
	
	private ArrayList<T> items;
	private ArrayList<MenuItem<T>> typedMenuItems = new ArrayList<>();
	private ArrayList<MenuItem<?>> menuItems = new ArrayList<>();
	
	private ArrayList<OnFocusChangedListener> onFocusChangedListeners = new ArrayList<>();
	
	private int focused = 0;
	private boolean centered = false;
	private int spacing;
	
	public Menu(int x, int y, int width, int height, boolean centered, int spacing, boolean cancellable,
			float background, Collection<T> items) {
		super(x, y, width, height, cancellable, background);
		this.centered = centered;
		this.spacing = spacing;
		this.items = new ArrayList<>(items);
		
		initializeMenuItems();
	}
	
	public Menu(int x, int y, int width, int height, boolean centered, int spacing, boolean cancellable, float background,
			Collection<T> items, String finishText) {
		
		super(x, y, width, height, cancellable, background);
		this.centered = centered;
		this.spacing = spacing;
		this.items = new ArrayList<>(items);
		
		initializeMenuItems();
		
		this.menuItems.add(new MenuItem<String>(this, finishText, (String s) -> finish(), getX(), getY() + (this.items.size() + 1 * this.spacing), getBackground()));
	}
	
	private void initializeMenuItems() {
		
		for(int i = 0; i < items.size(); i++) {
			if(isCentered()) {
				typedMenuItems.add(new MenuItem<T>(this, items.get(i), getX() + (getWidth() / 2) - (Utils.getName(items.get(i)).length() / 2), getY() + i * spacing, getBackground()));
			} else {
				typedMenuItems.add(new MenuItem<T>(this, items.get(i), getX(), getY() + i * spacing, getBackground()));
			}
			
		}
		
		menuItems.addAll(typedMenuItems);
		menuItems.get(0).focus();
		
	}
	
	@Override
	public void registerMouseCallbacks() {
		for(MenuItem<?> item : menuItems) {
			item.registerMouseCallbacks();
		}
	}
	
	protected void renderContent() {
		for(MenuItem<?> item : menuItems) {
			item.renderContent();
		}
	}
	
	protected int getFocused() {
		return focused;
	}
	
	protected void setFocused(MenuItem<?> menuItem) {
		setFocus(menuItems.indexOf(menuItem));
	}
	
	protected void setFocus(int index) {
		
		menuItems.get(focused).unfocus();
		
		if(focused != index) {
			focused = index;
			
			if(focused < 0) {
				focused = getSize() - 1;
			}
			
			if(focused >= getSize()) {
				focused = 0;
			}
			
			for(OnFocusChangedListener listener : onFocusChangedListeners) {
				listener.onChanged();
			}
		}
		
		menuItems.get(focused).focus();
	}
	
	protected int getSize() {
		return menuItems.size();
	}
	
	protected void menuNext() {
		setFocus(focused + 1);
	}
	
	protected void menuPrev() {
		setFocus(focused - 1);
	}
	
	private void select() {
		menuItems.get(focused).select();
	}
	
	public ArrayList<T> getItems() {
		return items;
	}
	
	public T getItem(int i) {
		return items.get(i);
	}
	
	public ArrayList<MenuItem<?>> getMenuItems() {
		return menuItems;
	}
	
	public void addOnSelectionChangedListener(OnFocusChangedListener listener) {
		onFocusChangedListeners.add(listener);
	}
	
	protected ArrayList<OnFocusChangedListener> getOnSelectionChangedListeners() {
		return onFocusChangedListeners;
	}
	
	public void setOnSelectListener(OnSelectListener<T> listener) {
		for(MenuItem<T> item : typedMenuItems) {
			item.setOnSelectListener(listener);
		}
	}
	
	public void setOnSelectListener(T item, OnSelectListener<T> listener) {
		typedMenuItems.get(items.indexOf(item)).setOnSelectListener(listener);
	}
	
	public T getFocusedItem() {
		if(getFocused() >= items.size()) {
			return null;
		} else {
			return items.get(getFocused());
		}
	}
	
	public MenuItem<?> getFocusedMenuItem() {
		return menuItems.get(getFocused());
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		switch(key) {
        case SquidInput.ENTER:
        case ' ':
        	select();
        	return;
        case 'j':
        case SquidInput.DOWN_ARROW:
        	menuNext();
        	return;
        case 'k':
        case SquidInput.UP_ARROW:
        	menuPrev();
            return;
        default:
        	getFocusedMenuItem().processKeyEvent(key, alt, ctrl, shift);
        	return;
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		return getFocusedMenuItem().processScrollEvent(amount);
	}

	protected boolean isCentered() {
		return centered;
	}

	protected int getSpacing() {
		return spacing;
	}
}
