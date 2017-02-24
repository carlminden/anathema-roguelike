package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.resources.RecentMotion;
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat;

public class Visibility extends SecondaryStat {

	public Visibility(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		double concealment = getObject().getStatAmount(Concealment.class);
		double motion = getObject().getStatAmount(RecentMotion.class);
		
		double light = getObject().getEnvironment().getLightLevels().get(getObject().getPosition());
		
		return 25 + Math.pow(20 * light, 1.75) - concealment + motion;
		
	}
	
	public VisibilityLevel getVisibilityLevel() {
		double stealth = getAmount();
		
		if(stealth < 25) {
			return VisibilityLevel.IMPERCEPTIBLE;
		} else if(stealth < 50) {
			return VisibilityLevel.CONCEALED;
		} else if(stealth < 75) {
			return VisibilityLevel.PARTIALLYCONCEALED;
		} else if(stealth < 100) {
			return VisibilityLevel.VISIBLE;
		} else {
			return VisibilityLevel.EXPOSED;
		}
	}
}
