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

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

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
import squidpony.squidgrid.gui.gdx.SquidLayers;
import squidpony.squidgrid.gui.gdx.TextCellFactory;


public class Display extends RenderSurface {
	
	public enum DisplayLayer { DUNGEON_BACKGROUND, UNUNSED, DUNGEON_FOREGROUND, DUNGEON_OVERLAY, UI_BACKGROUND, UI_FOREGROUND }
		
	private SpriteBatch batch;
	private Pixmap pixmap;
	private Texture tex;
	private Stage stage;
	private SquidLayers display;
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
    	
		stage = new Stage(new StretchViewport(getWidth() * cellWidth, (getHeight()) * cellHeight), batch);
		
//		TextCellFactory tcf = new TextCellFactory();
//		tcf.font(DefaultResources.getSmoothFont());
		
//		TextCellFactory tcf = DefaultResources.getStretchableFont().width(cellWidth).height(cellHeight).initBySize();
		
		
		
		//TextCellFactory tcf = DefaultResources.getStretchableFont();
		
		//tcf.height(cellHeight).initBySize();
		
		TextCellFactory tcf = new TextCellFactory();
		tcf.fontDistanceField("Inconsolata-LGC-Custom-distance.fnt", "Inconsolata-LGC-Custom-distance.png");
		
		display = new SquidLayers(UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, cellWidth, cellHeight, tcf);
		
		display.setTextSize(cellWidth, cellHeight + 1);
		display.setAnimationDuration(0.03f);
		display.setPosition(0, 0);
		
		
		for(int i = 0; i < DisplayLayer.values().length - 3; i++) {
			display.addExtraLayer();
		}
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, input.getSquidInput()));
		
		stage.addActor(display);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void put(DisplayLayer layer, int x, int y, char string, SColor color) {
		display.getLayer(layer.ordinal()).put(x, y, string, color);
	}
	
	public void quit() {
		Gdx.app.exit();
	}

	public void draw() {
		batch.begin();
		
		for(DisplayLayer layer : DisplayLayer.values()) {
			display.getLayer(layer.ordinal()).draw(batch, 1f);
			
			for(Outline o : new ArrayList<>(outlines.get(layer))) {
				o.render();
			}
		}
		outlines.clear();
		batch.end();
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
	
	public void lock() {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}
}
