package com.anathema_roguelike
package entities.items

import com.anathema_roguelike.entities.items.armor.ArmorFactory
import com.anathema_roguelike.entities.items.weapons.WeaponFactory

class AnyItemFactory() extends ItemFactory[Item] {
  addFactory(new WeaponFactory)
  addFactory(new ArmorFactory)
  addFactory(new AmuletFactory)

  override def getSupportedType: Class[_ <: ItemType[_ <: Item]] = classOf[AnyItem]
}
