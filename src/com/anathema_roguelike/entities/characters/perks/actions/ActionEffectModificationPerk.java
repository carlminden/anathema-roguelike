package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.perks.actions.modifications.ActionEffectModification;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public class ActionEffectModificationPerk<T extends Targetable, A extends TargetedAction<T, ?>> extends ActionModificationPerk<A> {
	
	private ArrayList<ActionEffectModification<T, ?>> modifications;
	
	@SafeVarargs
	public ActionEffectModificationPerk(String name, Class<A> actionType, ActionEffectModification<T, ?> ...modifications) {
		super(name, actionType);
		
		this.modifications = new ArrayList<>(Arrays.asList(modifications));
	}
	
	@SuppressWarnings("unchecked")
	public TargetEffect<? extends T, ?> modify(A action, TargetEffect<T, ?> te) {
		if(requirementsMet()) {
			for(ActionEffectModification<T, ?> aem : modifications) {
				if(aem.getType().isAssignableFrom(te.getTargetType())) {
					te = aem.getClass().cast(aem).modify(te);
				}
			}
		}

		return te;
	}
	
	public static void main(String[] args) {
		
//		new ActionEffectModificationPerk<>("test", TargetedAction.class,
//			new ActionEffectModification<Character, CharacterStat>(Character.class) {
//
//			@Override
//			protected Effect<Character, CharacterStat> modify(Effect<Character, CharacterStat> effect) {
//				effect.addModifier(new Modifier<Character, Strength>(Strength.class, AdditiveCalculation.fixed(3.0)));
//				return effect;
//			}},
//			new ActionEffectModification<Character, CharacterStat>(Character.class) {
//
//				@Override
//				protected Effect<Character, CharacterStat> modify(Effect<Character, CharacterStat> effect) {
//					effect.addModifier(new Modifier<Character, Strength>(Strength.class, AdditiveCalculation.fixed(3.0)));
//					return effect;
//				}}
//			).modify(new BasicAttack(new Orc(null, null), new Orc(null, null)), new TargetEffect<Character, CharacterStat>(Character.class) {
//		});
	}
}
