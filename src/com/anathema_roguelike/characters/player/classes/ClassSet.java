package com.anathema_roguelike.characters.player.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Collections2;

public class ClassSet {
	
	private static AutoClassToInstanceMap<PlayerClass> classes = new AutoClassToInstanceMap<>(PlayerClass.class);
	
	private Character character;
	private HashMap<Class<? extends PlayerClass>, Integer> classLevels = new HashMap<>();
	
	public ClassSet(Character character) {
		
		this.character = character;
		
		Utils.getSubclasses(PlayerClass.class).forEach(c -> {
			classLevels.put(c, 0);
		});
	}
	
	public int getClassLevels(Class<? extends PlayerClass> playerClass) {
		return classLevels.get(playerClass);
	}
	
	public void grantClassLevel(Class<? extends PlayerClass> playerClass) {
		int newLevel = getClassLevels(playerClass) + 1;
		
		classes.get(playerClass).getLevel(newLevel).grant(character);
		classLevels.put(playerClass, newLevel);
	}
	
	public Collection<Class<? extends PlayerClass>> getClasses() {
		return Collections2.filter(classLevels.entrySet(), (e) -> e.getValue() > 0).stream().map(e -> e.getKey()).collect(Collectors.toList());
	}
}
