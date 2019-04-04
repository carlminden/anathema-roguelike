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
package com.anathema_roguelike.fov;

import java.util.HashMap;
import java.util.Map.Entry;

import com.anathema_roguelike.entities.Entity;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;
import com.google.common.collect.HashBasedTable;
import com.google.common.eventbus.Subscribe;

import squidpony.squidgrid.FOV;
import squidpony.squidmath.PerlinNoise;

public class LightLevels extends FOVProcessor {
	
	private HashBasedTable<Entity, DirectionVector, Double> lightLevels = HashBasedTable.<Entity, DirectionVector, Double>create();
	private HashMap<Entity, Point> lastSeen = new HashMap<>();
	private HashMap<Entity, Boolean> dirty = new HashMap<>();
	
	private HashMap<Integer, float[][]> computedLightLevels = new HashMap<>();
	
	private Environment level;
	
	public static double anitmateLight(double light, double x, double y) {
		double turnTime = (Game.getInstance().getDisplay().getRenderTime());
		
		double noise = PerlinNoise.noise(x, y, Math.sin(Math.floor(turnTime / 75 % 75) / 75 * Math.PI) * 13);
		
		light += (Math.sin((turnTime + (noise * 1000))/ (100 * Math.PI))) / 18 + noise / 18;
		
		light = Utils.clamp(light, 0.0, 1.0);
		
		return light;
	}
	
	public LightLevels(int width, int height, Environment level) {
		super(width, height, level.getFOVResistances());
		
		computedLightLevels.put(Direction.UP, new float[width][height]);
		computedLightLevels.put(Direction.DOWN, new float[width][height]);
		computedLightLevels.put(Direction.RIGHT, new float[width][height]);
		computedLightLevels.put(Direction.LEFT, new float[width][height]);
		
		this.level = level;
		level.getEventBus().register(this);
	}

	@Override
	protected void visit(Entity entity, int x, int y, double light) {
		Point position = new Point(x, y);
		
		if(light > 0) {
			int directionToSource = Direction.of(entity.getPosition(), position);
			
			for(int direction : Direction.DIRECTIONS_4) {
				if((directionToSource & direction) != 0 || level.getFOVResistances()[x][y] != 1) {
					DirectionVector dv = new DirectionVector(position, direction);
					
					lightLevels.put(entity, dv, light);
					computedLightLevels.get(direction)[x][y] += light;
				}
			}
		}
	}
	
	public void recomputeLightLevels() {
		for(Entity entity : level.getEntities(Entity.class)) {
			if(entity.getLightEmission() > 0 && !entity.getPosition().equals(lastSeen.get(entity)) || isDirty(entity)) {
				
				for(Entry<DirectionVector, Double> entry : lightLevels.row(entity).entrySet()) {
					Point p = entry.getKey().getPosition();
					int direction = entry.getKey().getDirection();
					computedLightLevels.get(direction)[p.getX()][p.getY()] -= entry.getValue();
				}
				
				lightLevels.row(entity).clear();
				doFOV(entity, entity.getLightEmission(), 0, 360, new FOV(FOV.RIPPLE_TIGHT));
				lastSeen.put(entity, entity.getPosition());
				dirty.put(entity, false);
			}
		}
	}
	
	public void entityRemoved(Entity entity) {
		for(Entry<DirectionVector, Double> entry : lightLevels.row(entity).entrySet()) {
			Point p = entry.getKey().getPosition();
			int direction = entry.getKey().getDirection();
			computedLightLevels.get(direction)[p.getX()][p.getY()] -= entry.getValue();
		}
		
		lightLevels.row(entity).clear();
		lastSeen.remove(entity);
		dirty.remove(entity);
	}
	
	private boolean isDirty(Entity entity) {
		Boolean isDirty = dirty.get(entity);
		
		if(isDirty == null) {
			return false;
		} else {
			return isDirty;
		}
	}
	
	public float get(int x, int y, int direction) {
		
		float ret = 0f;
		
		for(int d : Direction.DIRECTIONS_4) {
			if(direction == 0 || (direction & d) != 0) {
				ret = Math.min(1, Math.max(ret, computedLightLevels.get(d)[x][y]));
			}
		}
		
		return ret;
	}
	
	public float get(int x, int y) {
		return get(x, y, 0);
	}
	
	public float get(Point point, int direction) {
		return get(point.getX(), point.getY(), direction);
	}
	
	public float get(Point point) {
		return get(point.getX(), point.getY(), 0);
	}
	
	public float get(DirectionVector dv) {
		return get(dv.getPosition().getX(), dv.getPosition().getY(), dv.getDirection());
	}
	
	@Subscribe
	public void processObstructionChangedEvent(ObstructionChangedEvent e) {
		Point p = e.getPosition();
		for(Entity entity : level.getEntities(Entity.class)) {
			if(p.squareDistance(entity.getPosition()) < (entity.getLightEmission() * entity.getLightEmission())) {
				dirty.put(entity, true);
			}
		}
		recomputeLightLevels();
	}
}
