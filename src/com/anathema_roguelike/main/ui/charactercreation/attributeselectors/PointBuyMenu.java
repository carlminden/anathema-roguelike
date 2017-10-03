package com.anathema_roguelike.main.ui.charactercreation.attributeselectors;

import java.util.Collection;

import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.Menu;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class PointBuyMenu extends Menu<Class<? extends Attribute>> {
	
	private static final int MAX_ATTRIBUTE_VALUE = 8;
	private static final int INITIAL_ATTRIBUTE_VALUE = 3;
	
	private int points = 15;
	private Player player;
	private static Collection<Class<? extends Attribute>> attributes = Utils.getSubclasses(Attribute.class);
	
	public PointBuyMenu(int x, int y, int width, int height, boolean centered, int spacing, Player player) {
		super(x, y, width, height, centered, spacing, false, 1.0f, attributes, "Finish");
		
		this.player = player;
		
		for(Class<? extends Attribute> abilityScore : attributes) {
			player.setAttributeScore(abilityScore, INITIAL_ATTRIBUTE_VALUE);
		}
		
		setOnSelectListener((Class<? extends Attribute> a) -> { });
	}
	
	public boolean isDone() {
		return points <= 0;
	}
	
	@Override
	public void finish() {
		if(isDone()) {
			super.finish();
		}
	}
	
	@Override
	protected void renderContent() {
		super.renderContent();
		for(int i = 0; i < getItems().size(); i++) {
			
			Class<? extends Attribute> attribute = getItem(i);
			int current = (int) player.getStatAmount(attribute);
			String text = getMenuItems().get(i).getText();
			
			if(current > 3) {
				renderString(DisplayLayer.UI_FOREGROUND, -2, (getSpacing() * i), "<");
			}
    		
    		if(current < 8 && points > 0) {
    			renderString(DisplayLayer.UI_FOREGROUND, text.length() + 2, (getSpacing() * i), ">");
    		}
        }
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		
		Class<? extends Attribute> attribute = getFocusedItem();
		
		if(attribute == null) {
			super.processKeyEvent(key, alt, ctrl, shift);
			
			return;
		}
		
		
		int current = (int) player.getStatAmount(attribute);
		
		switch (key) {
			case SquidInput.RIGHT_ARROW:
			case 'h':
				
				if(shift && current < MAX_ATTRIBUTE_VALUE) {
					
					int pointsToSpend = Math.min(MAX_ATTRIBUTE_VALUE - current, points);
					
					points = points - pointsToSpend;
					player.setAttributeScore(attribute, current + Math.min(MAX_ATTRIBUTE_VALUE - current, pointsToSpend));
				} else if(current < MAX_ATTRIBUTE_VALUE && points > 0) {
					points--;
					player.setAttributeScore(attribute, current + 1);
				}
				
				break;
			case SquidInput.LEFT_ARROW:
			case 'l':
				
				if(shift && current > INITIAL_ATTRIBUTE_VALUE) {
					points = points + Math.min(5, current - INITIAL_ATTRIBUTE_VALUE);
					player.setAttributeScore(attribute, current - Math.min(5, current - INITIAL_ATTRIBUTE_VALUE));
				} else if(current > INITIAL_ATTRIBUTE_VALUE) {
					points++;
					player.setAttributeScore(attribute, current - 1);
				}
				
				break;
	
			default:
				break;
		}
		
		super.processKeyEvent(key, alt, ctrl, shift);
	}

	public int getPoints() {
		return points;
	}

	public Player getPlayer() {
		return player;
	}
}