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
package com.anathema_roguelike.main.display;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MouseCallback;
import com.badlogic.gdx.InputAdapter;

import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;
import squidpony.squidgrid.gui.gdx.SquidMouse;

public class Input {
	
	private SquidInput input;
	
	private KeyHandler keyHandler;
	private SquidMouse mouse;
	
	private Consumer<InputHandler> result;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	private HashMap<Point, MouseCallback> mouseCallbacks = new HashMap<>();
	
	public Input() {
		
		keyHandler = new KeyHandler() {
			
			@Override
			public void handle(char key, boolean alt, boolean ctrl, boolean shift) {
				setResult((inputHandler) -> { inputHandler.getKeyHandler().handle(key, alt, ctrl, shift); });
			}
		};
		
		mouse = new SquidMouse(UIConfig.CELL_WIDTH, UIConfig.CELL_HEIGHT, UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, 0, 0, new InputAdapter() {
			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				setResult((inputHandler) -> { 
					inputHandler.getMouse().touchUp(x, y, pointer, button);
					
					MouseCallback callback = mouseCallbacks.get(new Point(x, y));
					
					if(callback != null) {
						callback.onClick();
					} else {
					}
				});
				return true;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				setResult((inputHandler) -> { inputHandler.getMouse().touchDown(screenX, screenY, pointer, button); });
				return true;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				setResult((inputHandler) -> { inputHandler.getMouse().touchDragged(screenX, screenY, pointer); });
				return true;
			}
			
			@Override
			public boolean scrolled(int amount) {
				setResult((inputHandler) -> { inputHandler.getMouse().scrolled(amount); });
				return true;
			}
			
			@Override
			public boolean mouseMoved(int x, int y) {
				setResult((inputHandler) -> { 
					inputHandler.getMouse().mouseMoved(x, y);
					
					MouseCallback callback = mouseCallbacks.get(new Point(x, y));
					
					if(callback != null) {
						callback.onMouseover();
					}
				});
				return true;
			}
		});
		
		input = new SquidInput(keyHandler, mouse);
	}
	
	public SquidInput getSquidInput() {
		return input;
	}
	
	private void handleInput(InputHandler inputHandler) {
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runInputResult(inputHandler);
	}
	
	private void runInputResult(InputHandler inputHandler) {
		if(result != null) {
			
			Consumer<InputHandler> tempResult = result;
			result = null;
			
			tempResult.accept(inputHandler);
		}
	}
	
	private synchronized void setResult(Consumer<InputHandler> result) {
		
		this.result = result;
		
		latch.countDown();
		latch = new CountDownLatch(1);
	}

	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
	public SquidMouse getMouse() {
		return mouse;
	}

	public void proccessInput(InputHandler inputHandler, BooleanSupplier done, Runnable registerMouseCallbacks) {
		
		while(!done.getAsBoolean()) {
			
			mouseCallbacks.clear();
			
			if(registerMouseCallbacks != null) {
				registerMouseCallbacks.run();
			}
			
			handleInput(inputHandler);
		}
	}
	
	public void registerMouseCallback(MouseCallback callback, Point p) {
		mouseCallbacks.put(p, callback);
	}
}
