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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements;


import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.InputHandler;
import com.anathema_roguelike.main.ui.uielements.UIElement;
import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils;
import com.badlogic.gdx.InputAdapter;

import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;

public abstract class InteractiveUIElement<T> extends UIElement {
	
	private T result;
	private boolean cancellable;
	private boolean finished = false;
	
	public InteractiveUIElement(int x, int y, int width, int height, String title, boolean cancellable, float background) {
		super(x, y, width, height, title, background);
		
		this.cancellable = cancellable;
	}
	
	public InteractiveUIElement(int x, int y, int width, int height, boolean cancellable, float background) {
		super(x, y, width, height, background);
		
		this.cancellable = cancellable;
	}
	
	public abstract void registerMouseCallbacks();
	
	public abstract void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift);
	
	public abstract boolean processScrollEvent(int amount);
	
	public T run() {
		Game.getInstance().getUserInterface().addUIElement(this);
		setResult(null);
		
		KeyHandler keyHandler = new KeyHandler() {
			
			@Override
			public void handle(char key, boolean alt, boolean ctrl, boolean shift) {
				
				if(key == SquidInput.ESCAPE) {
					if(isCancellable()) {
		        		finish();
		        	}
				} else {
					processKeyEvent(key, alt, ctrl, shift);
				}
			}
		};
		
		InputAdapter mouse = new InputAdapter() {
			@Override
			public boolean scrolled(int amount) {
				return processScrollEvent(amount);
			}
		};
		
		Game.getInstance().getInput().proccessInput(new InputHandler(keyHandler, mouse), () -> { return isFinished(); }, () -> {
			for(InteractiveUIElement<?> element : CollectionUtils.filterByClass(getUIElements(), InteractiveUIElement.class)) {
				element.registerMouseCallbacks();
			}
		});
	    
	    Game.getInstance().getUserInterface().removeUIElement(this);
	    return getResult();
	}
	
	protected void setResult(T result) {
		this.result = result;
	}
	
	public T getResult() {
		return result;
	}
	
	public boolean isCancellable() {
		return cancellable;
	}
	
	public void finish() {
		finished = true;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
