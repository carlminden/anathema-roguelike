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
	
	public enum Layer {FOG_OF_WAR_FOREGROUND, FOG_OF_WAR_BACKGROUND, LIT_FOG_OF_WAR_FOREGROUND,
		LIT_FOG_OF_WAR_BACKGROUND, DUNGEON_FOREGROUND, DUNGEON_BACKGROUND, NORMAL, NPCS, PLAYER}
	private State state;
	
	private HashMap<Layer, DisplayBuffer> dungeonLayers = new HashMap<>();
	
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
		
		for(Layer layer : Layer.values()) {
			dungeonLayers.put(layer, new DisplayBuffer(dungeonWidth, dungeonHeight));
		}
	}
	
	@Override
	public void render() {
		
		for(Layer layer : Layer.values()) {
			dungeonLayers.get(layer).clear();
		}
		
		state.render();
		
		Player player = Game.getInstance().getState().getPlayer();
		LightLevels lightLevels = state.getCurrentLevel().getLightLevels();
		
		
		BufferMask visibility = player.getCurrentVisibility();
		
		DisplayBuffer visibleForeground = dungeonLayers.get(Layer.DUNGEON_FOREGROUND);
		DisplayBuffer visibleBackground = dungeonLayers.get(Layer.DUNGEON_BACKGROUND);
		
		visibleForeground.compose(dungeonLayers.get(Layer.NORMAL));
		
		DisplayBuffer litFogOfWarForeground = dungeonLayers.get(Layer.LIT_FOG_OF_WAR_FOREGROUND);
		DisplayBuffer litFogOfWarBackground = dungeonLayers.get(Layer.LIT_FOG_OF_WAR_BACKGROUND);
		
		litFogOfWarForeground.transform(new TotalLightShader(lightLevels));
		litFogOfWarForeground.applyMask(visibility);
		
		litFogOfWarBackground.transform(new TotalLightShader(lightLevels));
		litFogOfWarBackground.applyMask(visibility);
		
		DisplayBuffer fogOfWarForeground = Game.getInstance().getState().getCurrentLevel().getFogOfWarForeground();
		
		DisplayBuffer newFogOfWarForeground = dungeonLayers.get(Layer.FOG_OF_WAR_FOREGROUND);
		newFogOfWarForeground.applyMask(visibility);
		
		fogOfWarForeground.compose(litFogOfWarForeground);
		fogOfWarForeground.compose(newFogOfWarForeground, new DisplayCellTransformation() {
			
			@Override
			public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
				return new DisplayCell(string, Color.factory.dimmer(color), display);
			}
		});
		
		DisplayBuffer fogOfWarBackground = Game.getInstance().getState().getCurrentLevel().getFogOfWarBackground();
		
		DisplayBuffer newFogOfWarBackground = dungeonLayers.get(Layer.FOG_OF_WAR_BACKGROUND);
		fogOfWarBackground.applyMask(visibility);
		
		fogOfWarBackground.compose(litFogOfWarBackground);
		fogOfWarBackground.compose(newFogOfWarBackground, new DisplayCellTransformation() {
			
			@Override
			public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
				return new DisplayCell(string, Color.factory.dimmer(color), display);
			}
		});
		
		visibleForeground.compose(dungeonLayers.get(Layer.NPCS));
		
		visibleForeground.transform(new VisibleLightForegroundShader(lightLevels));
		visibleForeground.applyMask(visibility);
		
		visibleForeground.compose(dungeonLayers.get(Layer.PLAYER));
		
		for(int i = 0; i < dungeonWidth; i++) {
			for(int j = 0; j < dungeonHeight; j++) {
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_BACKGROUND, getX() + i, getY() + j, ' ', Color.BLACK);
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_FOREGROUND, getX() + i, getY() + j, ' ', Color.BLACK);
				Game.getInstance().getDisplay().renderChar(DisplayLayer.DUNGEON_OVERLAY, getX() + i, getY() + j, ' ', Color.BLACK);
			}
		}
		
		fogOfWarBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX(), getY(), getWidth(), getHeight());
		fogOfWarForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX(), getY(), getWidth(), getHeight());
		
		visibleBackground.transform(new VisibleLightBackgroundShader(lightLevels));
		visibleBackground.applyMask(visibility);
		
		visibleBackground.render(DisplayLayer.DUNGEON_BACKGROUND, getX(), getY(), getWidth(), getHeight());
		visibleForeground.render(DisplayLayer.DUNGEON_FOREGROUND, getX(), getY(), getWidth(), getHeight());
		
		renderFoVs();
	}
	
	public void renderFoVs() {
		
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
	
	public void renderEntity(Layer layer, Entity entity) {
		
		int x = entity.getX();
		int y = entity.getY();
		VisualRepresentation rep = entity.getRepresentation();
		
		if(rep == null) {
			renderVisualRepresentation(layer, x, y, new VisualRepresentation('X', Color.ERROR));
		} else {
			renderVisualRepresentation(layer, x, y, rep);
		}
	}
	
	public void renderVisualRepresentation(Layer layer, int x, int y, VisualRepresentation rep) {
		renderChar(layer, x, y, rep.getChar(), rep.getColor());
	}
	
	public void renderChar(Layer layer, int x, int y, char string, SColor color) {
		dungeonLayers.get(layer).get(x, y).setChar(string);
		dungeonLayers.get(layer).get(x, y).setColor(color);
	}
	
	public void renderChar(Layer layer, int x, int y, char string) {
		renderChar(layer, x, y, string, Color.WHITE);
	}
	
	public void renderString(Layer layer, int x, int y, String string, SColor color) {
		for(int i = 0; i < string.length(); i++) {
			renderChar(layer, x + i, y, string.charAt(i), color);
		}
	}

	public void renderString(Layer layer, int x, int y, String string) {
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
