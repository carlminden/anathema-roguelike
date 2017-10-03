package com.anathema_roguelike.main.ui.charactercreation.attributeselectors;

import java.util.Collection;

import javax.annotation.Nullable;

import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MenuScreen;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.MenuValues;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.google.common.base.Function;

public class PointBuyScreen extends MenuScreen<Class<? extends Attribute>, PointBuyMenu> {
	
	private static Message instructionsMessage;
	private static Collection<Class<? extends Attribute>> attributes = Utils.getSubclasses(Attribute.class);
	
	private PointBuyMenu menu;
	private Player player;
	
	
	static {
		instructionsMessage = new Message("You have ");
		instructionsMessage.appendMessage("15", Color.GREEN);
		instructionsMessage.appendMessage(" remaining points to allocate to your Ability scores");
	}
	

	public PointBuyScreen(String title, Player player) {
		super(title, attributes, instructionsMessage, false);
		
		this.player = player;
	}
	
	protected void updateCurrentValues() {
		
		Message instructionsMessage = new Message("You have ");
		instructionsMessage.appendMessage(Integer.toString(menu.getPoints()), Color.GREEN);
		instructionsMessage.appendMessage(" remaining points to allocate to your Ability scores");
		
		setInstructionsMessage(instructionsMessage);
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		super.processKeyEvent(key, alt, ctrl, shift);
		updateCurrentValues();
	}

	@Override
	protected PointBuyMenu createMenu(int x, int y, int width, int height,
			Collection<Class<? extends Attribute>> choices, boolean cancellable, float background) {
		menu = new PointBuyMenu(x, y, width, height, cancellable, 1, player);
		
		TextBox currentValues = new MenuValues<Class<? extends Attribute>>(menu, 18, 0, attributes.size(), new Function<Class<? extends Attribute>, Message>() {

			@Override
			public Message apply(@Nullable Class<? extends Attribute> attribute) {
				int current = (int) menu.getPlayer().getStatAmount(attribute);
				
				return new Message(Integer.toString(current), Color.GREEN);
			}
		}, 1.0f);
		
		addUIElement(currentValues);

		return menu;
	}
}