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

import com.anathema_roguelike.characters.NPC;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.fov.LightLevels;
import com.anathema_roguelike.fov.TotalLightShader;
import com.anathema_roguelike.fov.VisibleLightBackgroundShader;
import com.anathema_roguelike.fov.VisibleLightForegroundShader;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.State;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.uielements.Rectangular;

import squidpony.squidgrid.gui.gdx.SColor;

public class DungeonMap implements Renderable, Rectangular {
	
	public enum DungeonLayer {FOG_OF_WAR_LIGHT, FOG_OF_WAR_BACKGROUND, FOG_OF_WAR_FOREGROUND, LIT_FOG_OF_WAR_LIGHT, LIT_FOG_OF_WAR_BACKGROUND,
		LIT_FOG_OF_WAR_FOREGROUND, LIGHT, BACKGROUND, FOREGROUND, NORMAL, NPCS, PLAYER}
	private State state;
	
	private HashMap<DungeonLayer, DisplayBuffer> dungeonLayers = new HashMap<>();
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int dungeonWidth;
	private int dungeonHeight;
	
	public DungeonMap(int x, int y, State state) {
		
		this.state = state;
		this.x = x;
		this.y = y;
		this.width = UIConfig.DUNGEON_MAP_WIDTH;
		this.height = UIConfig.DUNGEON_MAP_HEIGHT;
		this.dungeonWidth = Config.DUNGEON_WIDTH;
		this.dungeonHeight = Config.DUNGEON_HEIGHT;
		
		for(DungeonLayer layer : DungeonLayer.values()) {
			dungeonLayers.put(layer, new DisplayBuffer(dungeonWidth, dungeonHeight));
		}
	}
	
	@Override
	public void render() {
		
		for(DungeonLayer layer : DungeonLayer.values()) {
			dungeonLayers.get(layer).clear();
		}
		
		state.render();
		
		Player player = Game.getInstance().getState().getPlayer();
		LightLevels lightLevels = state.getCurrentLevel().getLightLevels();
		
		
		BufferMask visibility = player.getCurrentVisibility();
		
		DisplayBuffer visibleLight = dungeonLayers.get(DungeonLayer.LIGHT);
		DisplayBuffer visibleBackground = dungeonLayers.get(DungeonLayer.BACKGROUND);
		DisplayBuffer visibleForeground = dungeonLayers.get(DungeonLayer.FOREGROUND);
		
		visibleForeground.compose(dungeonLayers.get(DungeonLayer.NORMAL));
		
		DisplayBuffer fogOfWarLight = generateLayer(lightLevels, visibility, visibleLight,
				dungeonLayers.get(DungeonLayer.LIT_FOG_OF_WAR_LIGHT),
				Game.getInstance().getState().getCurrentLevel().getFogOfWarLight(),
				dungeonLayers.get(DungeonLayer.FOG_OF_WAR_LIGHT));
		
		DisplayBuffer fogOfWarBackground = generateLayer(lightLevels, visibility, visibleBackground,
				dungeonLayers.get(DungeonLayer.LIT_FOG_OF_WAR_BACKGROUND),
				Game.getInstance().getState().getCurrentLevel().getFogOfWarBackground(),
				dungeonLayers.get(DungeonLayer.FOG_OF_WAR_BACKGROUND));
		
		DisplayBuffer fogOfWarForeground = generateLayer(lightLevels, visibility, visibleForeground,
				dungeonLayers.get(DungeonLayer.LIT_FOG_OF_WAR_FOREGROUND),
				Game.getInstance().getState().getCurrentLevel().getFogOfWarForeground(),
				dungeonLayers.get(DungeonLayer.FOG_OF_WAR_FOREGROUND));
		
		visibleForeground.compose(dungeonLayers.get(DungeonLayer.NPCS));
		
		visibleBackground.transform(new VisibleLightForegroundShader(lightLevels));
		visibleBackground.applyMask(visibility);
		
		visibleForeground.transform(new VisibleLightForegroundShader(lightLevels));
		visibleForeground.applyMask(visibility);
		
		visibleForeground.compose(dungeonLayers.get(DungeonLayer.PLAYER));
		
		for(int i = 0; i < dungeonWidth; i++) {
			for(int j = 0; j < dungeonHeight; j++) {
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_LIGHT, getX() + i, getY() + j, ' ', Color.BLACK);
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_BACKGROUND, getX() + i, getY() + j, ' ', Color.BLACK);
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_FOREGROUND, getX() + i, getY() + j, ' ', Color.BLACK);
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_OVERLAY, getX() + i, getY() + j, ' ', Color.BLACK);
			}
		}
		
		fogOfWarLight.render(DisplayLayer.DUNGEON_LIGHT, getX(), getY(), getWidth(), getHeight());
		fogOfWarBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX(), getY(), getWidth(), getHeight());
		fogOfWarForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX(), getY(), getWidth(), getHeight());
		
		visibleLight.transform(new VisibleLightBackgroundShader(lightLevels));
		visibleLight.applyMask(visibility);
		
		visibleLight.render(DisplayLayer.DUNGEON_LIGHT, getX(), getY(), getWidth(), getHeight());
		visibleBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX(), getY(), getWidth(), getHeight());
		visibleForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX(), getY(), getWidth(), getHeight());
		
		renderFoVs();
	}
	
	private DisplayBuffer generateLayer(LightLevels lightLevels, BufferMask visibility, DisplayBuffer visible,
			DisplayBuffer litFogOfWar, DisplayBuffer fogOfWar, DisplayBuffer newFogOfWar) {
		
		litFogOfWar.transform(new TotalLightShader(lightLevels));
		litFogOfWar.applyMask(visibility);
		
		newFogOfWar.applyMask(visibility);
		
		fogOfWar.compose(litFogOfWar);
		fogOfWar.compose(newFogOfWar, new DisplayCellTransformation() {
			
			@Override
			public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
				return new DisplayCell(string, Color.factory.dimmer(color), display);
			}
		});
		
		return fogOfWar;
	}
	
	private void renderFoVs() {
		
		Player player = Game.getInstance().getState().getPlayer();
		BufferMask visibility = player.getCurrentVisibility();
		
		BufferMask enemyUnawareVision = new BufferMask(dungeonWidth, dungeonHeight);
		BufferMask enemyAlertedVision = new BufferMask(dungeonWidth, dungeonHeight);
		BufferMask enemyDetectedVision = new BufferMask(dungeonWidth, dungeonHeight);
		
		
		for(NPC character : state.getCurrentLevel().getEntities(NPC.class)) {
			if(player.canSee(character)) {
				if(character.canSee(player)) {
					enemyDetectedVision.or(character.getCurrentVisibility());
				} else {
					if(character.getMostInterestingStimulus() == null) {
						enemyUnawareVision.or(character.getCurrentVisibility());
					} else {
						enemyAlertedVision.or(character.getCurrentVisibility());
					}
				}
			}
		}
		
		enemyUnawareVision.nand(enemyAlertedVision);
		enemyUnawareVision.nand(enemyDetectedVision);
		
		enemyAlertedVision.nand(enemyDetectedVision);
		
		renderFoVOverlay(visibility, enemyUnawareVision, Color.UNAWARE);
		renderFoVOverlay(visibility, enemyAlertedVision, Color.ALERTED);
		renderFoVOverlay(visibility, enemyDetectedVision, Color.DETECTED);
		
		for(NPC character : state.getCurrentLevel().getEntities(NPC.class)) {
			if(player.canSee(character)) {
				if(!character.canSee(player) && character.getMostInterestingStimulus() != null) {
					
					//TODO needs work
					
					Point p = character.getMostInterestingStimulus().getPosition();
					
					if(p != null) {
						Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_OVERLAY, getX() + p.getX(), getY() + p.getY(), '@', Color.ALERTED);
					}
				}
			}
		}
	}
	
	private void renderFoVOverlay(BufferMask playerVision, BufferMask enemyFoV, SColor color) {
		enemyFoV.and(playerVision);
		
		Game.getInstance().getDisplay().renderOutline(DisplayLayer.DUNGEON_OVERLAY, new Outline(new Point(getX(), getY()), enemyFoV, color));
	}
	
	public void renderEntity(DungeonLayer layer, Entity entity) {
		
		int x = entity.getX();
		int y = entity.getY();
		VisualRepresentation rep = entity.getRepresentation();
		
		if(rep == null) {
			renderVisualRepresentation(layer, x, y, new VisualRepresentation('X', Color.ERROR));
		} else {
			renderVisualRepresentation(layer, x, y, rep);
		}
	}
	
	public void renderVisualRepresentation(DungeonLayer layer, int x, int y, VisualRepresentation rep) {
		renderChar(layer, x, y, rep.getChar(), rep.getColor());
	}
	
	public void renderChar(DungeonLayer layer, int x, int y, char string, SColor color) {
		dungeonLayers.get(layer).get(x, y).setChar(string);
		dungeonLayers.get(layer).get(x, y).setColor(color);
	}
	
	public void renderChar(DungeonLayer layer, int x, int y, char string) {
		renderChar(layer, x, y, string, Color.WHITE);
	}
	
	public void renderString(DungeonLayer layer, int x, int y, String string, SColor color) {
		for(int i = 0; i < string.length(); i++) {
			renderChar(layer, x + i, y, string.charAt(i), color);
		}
	}

	public void renderString(DungeonLayer layer, int x, int y, String string) {
		renderString(layer, x, y, string, Color.WHITE);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
