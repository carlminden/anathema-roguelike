package com.anathema_roguelike
package entities.items.weapons

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.{ItemFactory, ItemPropertyCache, ItemType}
import com.anathema_roguelike.entities.items.weapons.types.WeaponType
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.Utils

import scala.collection.JavaConverters._

class WeaponFactory() extends ItemFactory[Weapon] {
  Utils.getSubclasses[WeaponType]().foreach(t => {

    addFactory(new ItemFactory[Weapon]() {
      override def getSupportedType: Class[_ <: ItemType[_ <: Weapon]] = t

      override def generate(location: Either[Location, Character]): Weapon = {
        val itemType = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(t).asScala)
        val material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(itemType.getMaterialType).asScala)

        new Weapon(itemType, material, location)
      }
    })
  })

  override def getSupportedType: Class[_ <: ItemType[_ <: Weapon]] = classOf[WeaponType]
}
