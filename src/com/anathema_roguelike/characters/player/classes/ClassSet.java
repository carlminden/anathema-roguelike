package com.anathema_roguelike.characters.player.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Collections2;

public class ClassSet {
	
	private static AutoClassToInstanceMap<CharacterClass> classes = new AutoClassToInstanceMap<>(CharacterClass.class);
	
	private Character character;
	private HashMap<Class<? extends CharacterClass>, Integer> classLevels = new HashMap<>();
	
	public ClassSet(Character character) {
		
		this.character = character;
		
		Utils.getSubclasses(CharacterClass.class).forEach(c -> {
			classLevels.put(c, 0);
		});
	}
	
	public int getClassLevels(Class<? extends CharacterClass> characterClass) {
		return classLevels.get(characterClass);
	}
	
	public void grantClassLevel(Class<? extends CharacterClass> characterClass) {
		int newLevel = getClassLevels(characterClass) + 1;
		
		classes.get(characterClass).getLevel(newLevel).grant(character);
		classLevels.put(characterClass, newLevel);
		
		character.levelUp();
	}
	
	public Collection<Class<? extends CharacterClass>> getClasses() {
		return Collections2.filter(classLevels.entrySet(), (e) -> e.getValue() > 0).stream().map(e -> e.getKey()).collect(Collectors.toList());
	}
}
