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
package com.anathema_roguelike.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.anathema_roguelike.environment.terrain.Terrain;
import com.anathema_roguelike.environment.terrain.grounds.Stairs;
import com.anathema_roguelike.fov.LightLevels;
import com.anathema_roguelike.fov.LitFOVProcessor;
import com.anathema_roguelike.fov.ObstructionChangedEvent;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.display.DisplayBuffer;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils;
import com.anathema_roguelike.stats.locationstats.Opacity;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class Environment implements Renderable {
	
	private int depth;
	private int width = Config.DUNGEON_WIDTH;
	private int height = Config.DUNGEON_HEIGHT;
	private double[][] fovResistance = new double[width][height];
	private Stairs upStairs;
	private Stairs downStairs;
	
	private DisplayBuffer fogOfWarForeground;
	private DisplayBuffer fogOfWarBackground;
	private DisplayBuffer fogOfWarLight;
	private LightLevels lightLevels;
	private LitFOVProcessor litFOVProcessor;
	
	private Location[][] map = new Location[width][height];
	private ArrayList<Entity> entities = new ArrayList<>();
	
	private EventBus eventBus = new EventBus();
	
	public Environment(int depth) {
		this.depth = depth;
		
		fogOfWarForeground = new DisplayBuffer(width, height, false);
		fogOfWarBackground = new DisplayBuffer(width, height, false);
		fogOfWarLight = new DisplayBuffer(width, height, false);
		lightLevels = new LightLevels(width, height, this);
		litFOVProcessor = new LitFOVProcessor(width, height, fovResistance, lightLevels);
		
		eventBus.register(this);
	}
	
	public void init() {
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				fovResistance[i][j] = getLocation(i, j).getStatAmount(Opacity.class);
			}
		}
	}
	
	@Override
	public void render() {
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				Location location = getLocation(i, j);
				
				location.render(i, j);
			}
		}
		
		for(Entity entity : entities) {
			entity.render();
		}
	}
	
	public <T extends Entity> Collection<T> getEntities(final Class<T> cls) {
		return CollectionUtils.filterByClass(entities, cls);
	}
	
	public <T extends Entity> Collection<T> getEntitiesAt(final Point point, final Class<T> cls) {
		return CollectionUtils.filterByClass(entities, cls).stream().filter(e -> e.getPosition().equals(point)).collect(Collectors.toList());
	}
	
	public Collection<Entity> getEntitiesAt(final Point point) {
		return entities.stream().filter(e -> e.getPosition().equals(point)).collect(Collectors.toList());
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
		lightLevels.entityRemoved(entity);
		
		eventBus.unregister(entity);
	}
	
	public void addEntity(Entity entity, Point position) {
		entities.add(entity);
		
		entity.setDepth(depth);
		entity.setPosition(position);
		
		eventBus.register(entity);
	}
	
	public void moveEntityBy(Entity entity, int xOffset, int yOffset) {
		Point currentPosition = entity.getPosition();
		
		moveEntityTo(entity, new Point(xOffset + currentPosition.getX(), yOffset + currentPosition.getY()));
	}
	
	public void moveEntityTo(Entity entity, int x, int y) {
		moveEntityTo(entity, new Point(x, y));
	}

	public void moveEntityTo(Entity entity, Point position) {
		entity.setPosition(position);
	}
	
	public Location[][] getMap() {
		return map;
	}
	
	public void setTerrain(Terrain terrain, int x, int y) {
		map[x][y].setTerrain(terrain);
	}
	
	public Location getLocation(int x, int y) {
		return map[x][y];
	}
	
	public Location getLocation(Point position) {
		return getLocation(position.getX(), position.getY());
	}

	public Stairs getStairs(int direction) {
		
		if(direction == Direction.UP) {
			return upStairs;
		} else if(direction == Direction.DOWN) {
			return downStairs;
		}
		
		return null;
	}
	
	public void setUpStairs(Stairs upStairs) {
		this.upStairs = upStairs;
		Point position = upStairs.getPosition();
		
		map[position.getX()][position.getY()].setTerrain(upStairs);
	}

	public void setDownStairs(Stairs downStairs) {
		this.downStairs = downStairs;
		Point position = downStairs.getPosition();
		
		map[position.getX()][position.getY()].setTerrain(downStairs);
	}

	public int getDepth() {
		return depth;
	}
	
	public boolean isPassable(int x, int y) {
		return getLocation(x, y).isPassable();
	}

	public boolean isPassable(Point point) {
		return isPassable(point.getX(), point.getY());
	}
	
	public double[][] getFOVResistances() {
		return fovResistance;
	}
	
	public DisplayBuffer getFogOfWarForeground() {
		return fogOfWarForeground;
	}
	
	public DisplayBuffer getFogOfWarBackground() {
		return fogOfWarBackground;
	}
	
	public DisplayBuffer getFogOfWarLight() {
		return fogOfWarLight;
	}

	public LightLevels getLightLevels() {
		return lightLevels;
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public LitFOVProcessor getLitFOVProcessor() {
		return litFOVProcessor;
	}
	
	@Subscribe
	public void processObstructionChangedEvent(ObstructionChangedEvent e) {
		
		Point point = e.getPosition();
		
		Location location = getLocation(point.getX(), point.getY());
		
		fovResistance[point.getX()][point.getY()] = location.getStatAmount(Opacity.class);
	}
}
