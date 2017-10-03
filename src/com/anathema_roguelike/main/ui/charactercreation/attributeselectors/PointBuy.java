package com.anathema_roguelike.main.ui.charactercreation.attributeselectors;

import com.anathema_roguelike.characters.Player;

public class PointBuy extends AttributeSelector {

	@Override
	public void selectScores(Player player) {
		new PointBuyScreen("Set your Ability Scores", player).run();
	}
}
