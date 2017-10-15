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
package com.anathema_roguelike.main.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.animations.Animation;
import com.anathema_roguelike.main.input.Input;
import com.anathema_roguelike.main.ui.UIConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.common.collect.ArrayListMultimap;

import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidgrid.gui.gdx.SparseLayers;
import squidpony.squidgrid.gui.gdx.SparseTextMap;
import squidpony.squidgrid.gui.gdx.TextCellFactory;


public class Display extends RenderSurface {
	
	public enum DisplayLayer { DUNGEON_LIGHT, DUNGEON_BACKGROUND, DUNGEON_FOREGROUND, DUNGEON_OVERLAY, UI_BACKGROUND, UI_FOREGROUND, DEBUG }
	
	private long renderTime = System.currentTimeMillis();
	private SpriteBatch batch;
	private Pixmap pixmap;
	private Texture tex;
	
	private Stage gameStage;
	private SparseLayers gameDisplay;
	
	private Stage uiStage;
	private SparseLayers uiDisplay;
	
	private Stage debugStage;
	private SparseLayers debugDisplay;
	
	private TextCellFactory gameTextCellFactory = new TextCellFactory();
	private TextCellFactory uiTextCellFactory = new TextCellFactory();
	
	private HashMap<DisplayLayer, SparseTextMap> layers = new HashMap<>();
	private HashMap<DisplayLayer, SparseLayers> layerGroups = new HashMap<>();
	
	private ReentrantLock lock = new ReentrantLock();
	
	private ArrayListMultimap<DisplayLayer, Outline> outlines = ArrayListMultimap.create();
	
	public Display(Input input) {
		super(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT);
		
		int cellWidth = UIConfig.CELL_WIDTH;
		int cellHeight = UIConfig.CELL_HEIGHT;
        
		batch = new SpriteBatch();
		pixmap = new Pixmap(256,128, Pixmap.Format.RGBA8888);
    	pixmap.setColor(1, 1, 1, 1);
    	pixmap.fill();
    	tex = new Texture(pixmap);
    	
    	gameStage = new Stage(new StretchViewport(getWidth() * cellWidth, (getHeight()) * cellHeight), batch);
    	uiStage = new Stage(new StretchViewport(getWidth() * cellWidth, (getHeight()) * cellHeight), batch);
    	debugStage = new Stage(new StretchViewport(getWidth() * cellWidth, (getHeight()) * cellHeight), batch);
		
		gameTextCellFactory.fontDistanceField("Inconsolata-LGC-Custom-distance.fnt", "Inconsolata-LGC-Custom-distance.png");
		
		gameDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, gameTextCellFactory);
		gameDisplay.font.tweakWidth(cellWidth * 1.1f).tweakHeight(cellHeight * 1.1f).initBySize();
		gameDisplay.setPosition(0, 0);
		
		uiTextCellFactory.fontDistanceField("Inconsolata-LGC-Custom-distance.fnt", "Inconsolata-LGC-Custom-distance.png");
		
		uiDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, uiTextCellFactory);
		uiDisplay.font.tweakWidth(cellWidth).tweakHeight(cellHeight * 1f).initBySize();
		uiDisplay.setPosition(0, 0);
		
		debugDisplay = new SparseLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, uiTextCellFactory);
		debugDisplay.font.tweakWidth(cellWidth).tweakHeight(cellHeight * 1f).initBySize();
		debugDisplay.setPosition(0, 0);
		
		
		layers.put(DisplayLayer.DUNGEON_LIGHT, gameDisplay.addLayer(DisplayLayer.DUNGEON_LIGHT.ordinal()));
		layerGroups.put(DisplayLayer.DUNGEON_LIGHT, gameDisplay);
		
		layers.put(DisplayLayer.DUNGEON_BACKGROUND, gameDisplay.addLayer(DisplayLayer.DUNGEON_BACKGROUND.ordinal()));
		layerGroups.put(DisplayLayer.DUNGEON_BACKGROUND, gameDisplay);
		
		layers.put(DisplayLayer.DUNGEON_FOREGROUND, gameDisplay.addLayer(DisplayLayer.DUNGEON_FOREGROUND.ordinal()));
		layerGroups.put(DisplayLayer.DUNGEON_FOREGROUND, gameDisplay);
		
		layers.put(DisplayLayer.DUNGEON_OVERLAY, gameDisplay.addLayer(DisplayLayer.DUNGEON_OVERLAY.ordinal()));
		layerGroups.put(DisplayLayer.DUNGEON_OVERLAY, gameDisplay);
		
		layers.put(DisplayLayer.UI_BACKGROUND, uiDisplay.addLayer(DisplayLayer.UI_BACKGROUND.ordinal()));
		layerGroups.put(DisplayLayer.UI_BACKGROUND, uiDisplay);
		
		layers.put(DisplayLayer.UI_FOREGROUND, uiDisplay.addLayer(DisplayLayer.UI_FOREGROUND.ordinal()));
		layerGroups.put(DisplayLayer.UI_FOREGROUND, uiDisplay);
		
		layers.put(DisplayLayer.DEBUG, debugDisplay.addLayer(DisplayLayer.DEBUG.ordinal()));
		layerGroups.put(DisplayLayer.DEBUG, debugDisplay);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(gameStage, input.getSquidInput()));
		
		gameStage.addActor(gameDisplay);
		uiStage.addActor(uiDisplay);
		debugStage.addActor(debugDisplay);
		
	}
	
	public void put(DisplayLayer layer, int x, int y, char string, SColor color) {
		layers.get(layer).place(x, y, string, color);
	}
	
	public SparseTextMap getLayer(DisplayLayer layer) {
		return layers.get(layer);
	}
	
	public SparseLayers getLayerGroup(DisplayLayer layer) {
		return layerGroups.get(layer);
	}
	
	public void quit() {
		Gdx.app.exit();
	}

	public void draw() {
		renderTime = System.currentTimeMillis();
		
		gameStage.act();
		gameStage.getViewport().apply(false);
		gameStage.draw();
		gameDisplay.clear();
		
		batch.begin();
		
		for(DisplayLayer layer : DisplayLayer.values()) {
			for(Outline o : new ArrayList<>(outlines.get(layer))) {
				o.render();
			}
		}
		outlines.clear();
		batch.end();
		
		uiStage.act();
		uiStage.getViewport().apply(false);
		uiStage.draw();
		
		if(Config.DEBUG) {
			debugStage.act();
			debugStage.getViewport().apply(false);
			debugStage.draw();
			debugDisplay.clear();
		}
		
		Gdx.graphics.setTitle("Anathema");
	}
	
	public long getRenderTime() {
		return renderTime;
	}
	
	public void renderOutline(DisplayLayer layer, Outline outline) {
		outlines.put(layer, outline);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, SColor color) {
		
		y1 = (getHeight() * UIConfig.CELL_HEIGHT) - y1;
		y2 = (getHeight() * UIConfig.CELL_HEIGHT) - y2;
		
		int length = (int) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		int angle = (int) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		
		batch.setColor(color.r, color.g, color.b, color.a);
		batch.draw(tex, x1, y1, 0f, 1f, length, 1f, 1f, 1f, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}
	
	public void addAnimation(Animation animation) {
		animation.create(DisplayLayer.DUNGEON_OVERLAY);
	}
	
	public void clear(DisplayLayer layer) {
		layers.get(layer).clear();
	}
	
	public void lock() {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}
}
