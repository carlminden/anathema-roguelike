package com.anathema_roguelike.items;

public class AmuletFactory extends ItemFactory<Amulet> {
	
	public AmuletFactory() {
		
	}
	
	@Override
	public Class<? extends ItemType<? extends Amulet>> getSupportedType() {
		return Amulet.class;
	}
	
	@Override
	public Amulet generate() {
		return new Amulet("Enchanted Amulet of Stuff");
	}
}