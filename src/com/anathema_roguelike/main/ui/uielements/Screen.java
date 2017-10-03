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

import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement;

public class Screen<T> extends InteractiveUIElement<T> {
	
	private InteractiveUIElement<T> focusedUIElement;
	
	public Screen(int x, int y, int width, int height, String title, boolean cancellable, float background) {
		super(x, y, width, height, title, cancellable, background);
	}
	
	public Screen(String title, boolean cancellable) {
		super(0, 0, UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, title, cancellable, 1f);
	}
	
	public void setFocusedUIElement(InteractiveUIElement<T> focusedUIElement) {
		this.focusedUIElement = focusedUIElement;
	}
	
	@Override
	public ArrayList<UIElement> getUIElements() {
		
		ArrayList<UIElement> ret = new ArrayList<>(super.getUIElements());
		ret.add(focusedUIElement);
		
		return ret;
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		for(UIElement element : getUIElements()) {
			
			
			if(element == focusedUIElement) {
				focusedUIElement.processKeyEvent(key, alt, ctrl, shift);
				
			} else if(element instanceof InteractiveUIElement) {
				((InteractiveUIElement<?>)element).processKeyEvent(key, alt, ctrl, shift);
			}
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		
		for(UIElement element : getUIElements()) {
			if(element instanceof InteractiveUIElement) {
				((InteractiveUIElement<?>)element).processScrollEvent(amount);
			}
		}
		
		return focusedUIElement.processScrollEvent(amount);
	}

	@Override
	public void registerMouseCallbacks() {
		focusedUIElement.registerMouseCallbacks();
	}

	@Override
	protected void renderContent() {
		
	}
	
	@Override
	public boolean isFinished() {
		return focusedUIElement.isFinished();
	}
	
	@Override
	public T getResult() {
		return focusedUIElement.getResult();
	}
}
