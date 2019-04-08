package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Hearing;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Vision;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.entities.characters.Character;

import java.util.Collections;
import java.util.Optional;

public class WaitAction extends TargetedAction<Character> {

	public WaitAction(Character character) {
		super(character, character, Collections.singletonList(character), EnergyCost.STANDARD(character), new TargetEffect<Character, CharacterStat>(character.getClass(), "Waiting") {
			@Override
			public Optional<Effect<Character, CharacterStat>> getEffect() {
				return Optional.of(
					new Effect<>(Optional.of(this), new Duration(Duration.VERY_SHORT),
						new Modifier<>(Vision.class, AdditiveCalculation.fixed(5.0)),
						new Modifier<>(Hearing.class, AdditiveCalculation.fixed(5.0))
					)
				);
			}
		});
	}

	@Override
	protected void onTake() {
		
	}
}
