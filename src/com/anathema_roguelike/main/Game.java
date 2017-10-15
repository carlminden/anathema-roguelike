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
package com.anathema_roguelike.main;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Random;

import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display;
import com.anathema_roguelike.main.display.DungeonMap;
import com.anathema_roguelike.main.events.GameTurnEvent;
import com.anathema_roguelike.main.input.Input;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.UserInterface;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SplashScreen;
import com.anathema_roguelike.main.utilities.Latch;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.google.common.eventbus.EventBus;

import squidpony.squidgrid.gui.gdx.SColor;

public class Game extends ApplicationAdapter {
	
	private State state;
    private EventBus eventBus;
    private UserInterface ui;
    private Display display;
    private Random rand = new Random();
    private Input input;
    
    private final Latch latch = new Latch();
    
    protected boolean quit;
    
    private static Game instance = null;

	public static void main(String[] args) {
		
		Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread arg0, Throwable arg1) {
				System.out.println("Thread " + arg0);
				arg1.printStackTrace();
			}
		});
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
   		config.width = UIConfig.TERM_WIDTH * UIConfig.CELL_WIDTH;
    	config.height = UIConfig.TERM_HEIGHT * UIConfig.CELL_HEIGHT;
    	config.title = "Anathema";
    	config.vSyncEnabled = false;
        config.foregroundFPS = 0;
        config.backgroundFPS = 30;
        config.resizable = false;
        //config.addIcon(path, fileType);
    	
    	new LwjglApplication(Game.getInstance(), config);
    	
    	Game.getInstance().start();
    }
	
	protected Game() {
		
	}
	
	@Override
	public void create() {
		super.create();
		
		input = new Input();
		display = new Display(input);
		
        eventBus = new EventBus();
        ui = new UserInterface();
        
        latch.release();
	}
	
    public static Game getInstance() {
       if(instance == null) {
          instance = new Game();
       }
       return instance;
    }
	
	public State getState() {
		return state;
	}
	
	public UserInterface getUserInterface() {
		return ui;
	}
	
	public DungeonMap getMap() {
		return ui.getMap();
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public Input getInput() {
		return input;
	}
	
	public void quit() {
        display.quit();
    }
	
	private void start() {
		
		latch.await();
		
		new SplashScreen().run();
		
        state = new State();
        state.init();
        
        ui.init(state);
        
		while(true) {
			display.lock();
			
			state.computeNextState();
			eventBus.post(new GameTurnEvent());
			
			display.unlock();
		}
	}
	
	@Override
	public void render() {
		display.lock();
		
		SColor bgColor = Color.BLACK;
		
		Gdx.gl.glClearColor(bgColor.r / 255.0f, bgColor.g / 255.0f, bgColor.b / 255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        ui.render();
		
        if(input.getSquidInput().hasNext()) {
        	input.getSquidInput().next();
        }
        
		display.draw();
		
		display.unlock();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		if(input != null && input.getMouse() != null) {
			input.getMouse().reinitialize(
				(float) width / UIConfig.TERM_WIDTH,
				(float) height / (UIConfig.TERM_HEIGHT),
				UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, 0, 0
			);
		}
	}
	
	public long getElapsedTime() {
		if(state == null) {
			return 0;
		} else {
			return state.getElapsedTime();
		}
	}
	
	public long getTurn() {
		
		if(state == null || state.getPlayer() == null) {
			return 0;
		} else {
			return state.getPlayer().getTurn();
		}
	}	
}
