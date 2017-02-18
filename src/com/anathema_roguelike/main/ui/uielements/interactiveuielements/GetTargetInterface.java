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

import java.util.Optional;

import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.animations.Blink;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.Border;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.datastructures.CircularArrayList;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class GetTargetInterface extends InteractiveUIElement<Point> {
	
	private Border mapBorder;
	private CircularArrayList<Point> potentialTargets;
	private int index = 0;
	private TargetingStrategy strategy;
	private Ability ability;
	
	//TODO: the whole animation system needs to be rebuilt
	private Blink animation = new Blink(Optional.of(new VisualRepresentation('X', Color.RED)), DungeonLayer.FOREGROUND);
	
	public GetTargetInterface(Ability ability, TargetingStrategy strategy) {
		super(0, UIConfig.MAP_START_Y, UIConfig.DUNGEON_MAP_WIDTH, UIConfig.DUNGEON_MAP_HEIGHT, true, 0f);
		
		this.ability = ability;
		this.strategy = strategy;
		
		potentialTargets = new CircularArrayList<Point>(strategy.getValidTargetPoints(ability.getCharacter()));
		
		mapBorder = new Border(Game.getInstance().getUserInterface().getMapPlaceholder(), getTitleString());
		
		String instructions = "Select a target within " + Utils.getName(strategy.getRange()) + " for " + Utils.getName(ability);
		
		Game.getInstance().getUserInterface().addMessage(new Message(instructions, Color.INSTRUCTIONS));
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		switch(key) {
        case SquidInput.ENTER:
        case ' ':
        	setResult(potentialTargets.get(index));
        	Game.getInstance().getState().getCurrentLevel().removeEntity(animation);
        	finish();
        	return;
        case 'j':
        case SquidInput.DOWN_ARROW:
        case 'h':
        case SquidInput.LEFT_ARROW:
        	index++;
        	animation.reset();
        	return;
        case 'k':
        case SquidInput.UP_ARROW:
        case 'l':
        case SquidInput.RIGHT_ARROW:
        	index--;
        	animation.reset();
            return;
        default:
        	return;
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		return false;
	}
	
	private String getTitleString() {
		return "Current target: " + potentialTargets.get(index);
	}

	@Override
	protected void renderContent() {
		
		Point currentlyTargeted = potentialTargets.get(index);
		
		mapBorder.setTitle(getTitleString());
		mapBorder.render();
		
		Game.getInstance().getState().getCurrentLevel().moveEntityTo(animation, currentlyTargeted);
	}
	
	@Override
	public Point run() {
		
		Game.getInstance().getState().getCurrentLevel().addEntity(animation, new Point(0, 0));
		
		return super.run();
	}

	@Override
	public void registerMouseCallbacks() {
		// TODO Auto-generated method stub
	}
}
