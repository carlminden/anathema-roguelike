/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus;

import java.util.ArrayList;

import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MouseCallback;

import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidgrid.gui.gdx.SquidInput;

//The way I handled the finishItem is horrible
public class Menu<T> extends InteractiveUIElement<T> {
	
	private ArrayList<MenuItem<T>> items;
	private MenuItem<String> finishItem;
	
	private ArrayList<OnSelectListener<T>> onSelectListeners = new ArrayList<>();
	private OnSelectListener<String> onSelectFinishListener;
	
	private ArrayList<OnSelectionChangedListener> onSelectionChangedListeners = new ArrayList<>();
	
	private int selectedItem = 0;
	private boolean centered = false;
	private int spacing = 1;
	
	public Menu(int x, int y, int width, int height, boolean centered, int spacing, boolean cancellable, float background) {
		super(x, y, width, height, cancellable, background);
		this.centered = centered;
		this.spacing = spacing;
		this.items = new ArrayList<>();
	}

	public Menu(int x, int y, int width, int height, boolean centered, int spacing, String finishText, boolean cancellable, float background) {
		super(x, y, width, height, cancellable, background);
		this.centered = centered;
		this.spacing = spacing;
		this.items = new ArrayList<>();
		this.finishItem = new MenuItem<String>(finishText);
	}
	
	@Override
	public void registerMouseCallbacks() {
		
		ArrayList<MenuItem<?>> temp = new ArrayList<>(items);
		
		if(finishItem != null) {
			temp.add(finishItem);
		}
		
		for(MenuItem<?> item : temp) {
			
			int index = items.indexOf(item);
			
			MouseCallback callback = new MouseCallback() {
				
				@Override
				public void onMouseover() {
					selectedItem = index;
				
					selectionChanged();
				}
				
				@Override
				public void onClick() {
					selectedItem = index;
					
					selectionChanged();
					
					select();
				}
			};
			
			
	        int x = getX();
			
			if(isCentered()) {
				x = getWidth() / 2 - item.getText().length() / 2;
        	}
			
			for(int i = 0; i < getWidth(); i++) {
				Game.getInstance().getInput().registerMouseCallback(callback, new Point(x + i, getY() + getSpacing() * index));
			}
		}
	}
	
	protected void renderContent() {
		SColor background;
		SColor foreground;
		
		for(int i = 0; i < getSize(); i++) {
			if(getSelected() == i) {
				background = Color.WHITE;
				foreground = Color.BLACK;
            } else {
            	background = Color.BLACK;
				foreground = Color.WHITE;
            }
			
			if(finishItem != null && i == getSize() - 1) {
        		if(isCentered()) {
        			renderStringCentered(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, (getSpacing() * getSize()), finishItem.getText(), foreground, background);
            	} else {
            		renderString(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, 0, (getSpacing() * getSize()), finishItem.getText(), foreground, background);
            	}
        		continue;
        	}
        	
        	if(isCentered()) {
        		renderStringCentered(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, (getSpacing() * i), items.get(i).getText(), foreground, background);
        	} else {
        		renderString(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, 0, (getSpacing() * i), items.get(i).getText(), foreground, background);
        	}
        }
	}
	
	protected int getSelected() {
		return selectedItem;
	}
	
	protected int getSize() {
		if(finishItem != null) {
			return items.size() + 1;
		} else {
			return items.size();
		}
	}
	
	private void menuNext() {
		selectedItem++;
		
		if(selectedItem >= getSize()) {
			selectedItem = 0;
		}
		selectionChanged();
	}
	
	private void menuPrev() {
		selectedItem--;
		if(selectedItem < 0) {
			selectedItem = getSize() - 1;
		}
		
		selectionChanged();
	}
	
	private void selectionChanged() {
		for(OnSelectionChangedListener listener : onSelectionChangedListeners) {
			listener.onChanged();
		}
	}
	
	private void select() {
		if(isFinishSelected()) {
			if(onSelectFinishListener != null) {
				onSelectFinishListener.onSelect(finishItem.getItem());
				finishItem.select();
			}
			return;
		}
		
		for(OnSelectListener<T> listener : onSelectListeners) {
			listener.onSelect(getSelectedItem());
		}
		
		items.get(selectedItem).select();
	}
	
	private boolean isFinishSelected() {
		return selectedItem == getSize() - 1 && finishItem != null;
	}
	
	public void setMenuItems(ArrayList<MenuItem<T>> items) {
		this.items = items;
	}
	
	public ArrayList<MenuItem<T>> getItems() {
		return items;
	}
	
	public void addMenuItem(MenuItem<T> item) {
		items.add(item);
	}
	
	public void addOnSelectListener(OnSelectListener<T> listener) {
		onSelectListeners.add(listener);
	}
	
	protected ArrayList<OnSelectListener<T>> getOnSelectListeners() {
		return onSelectListeners;
	}
	
	public void addOnSelectionChangedListener(OnSelectionChangedListener listener) {
		onSelectionChangedListeners.add(listener);
	}
	
	protected ArrayList<OnSelectionChangedListener> getOnSelectionChangedListeners() {
		return onSelectionChangedListeners;
	}
	
	public void setOnSelectFinishListener(OnSelectListener<String> onSelectFinishListener) {
		this.onSelectFinishListener = onSelectFinishListener;
	}
	
	public T getSelectedItem() {
		if(getSelected() == getSize() || (getSelected() == getSize() - 1 && finishItem != null)) {
			return null;
		} else {
			return items.get(getSelected()).getItem();
		}
	}
	
	public MenuItem<String> getFinishItem() {
		return finishItem;
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
        	return;
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		return false;
	}

	protected boolean isCentered() {
		return centered;
	}

	protected int getSpacing() {
		return spacing;
	}
}
