package com.anathema_roguelike
package entities.items.armor

import com.anathema_roguelike.entities.items.{Item, ItemPropertyCache}
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.Color
import squidpony.squidgrid.gui.gdx.SColor
import com.anathema_roguelike.entities.characters.Character

abstract class Armor(armorType: ArmorType, material: ArmorMaterial, location: Either[Location, Character]) extends Item(location) {


  applyEffect(armorType.getEffect)
  applyEffect(material.getEffect)

  def this(armorType: String, material: String, location: Either[Location, Character]) {
    this(
      ItemPropertyCache.getProperty(classOf[ArmorType], armorType),
      ItemPropertyCache.getProperty(classOf[ArmorMaterial], material),
      location
    )
  }

  def this(armorType: String, material: ArmorMaterial, location: Either[Location, Character]) {
    this(ItemPropertyCache.getProperty(classOf[ArmorType], armorType), material, location)
  }

  def this(armorType: ArmorType, material: String, location: Either[Location, Character]) {
    this(armorType, ItemPropertyCache.getProperty(classOf[ArmorMaterial], material), location)
  }

  def getType: ArmorType = armorType

  def getMaterial: ArmorMaterial = material

  override def toString: String = material.getName + " " + armorType.getName

  def getColor: SColor = material.getName match {
    case ArmorMaterial.UMBRALSILK | ArmorMaterial.SHADOWEAVE | ArmorMaterial.BLACKSTEEL =>
      Color.DARK_GRAY
    case ArmorMaterial.CLOTH | ArmorMaterial.SILENAI_CRYSTAL =>
      Color.WHITE
    case ArmorMaterial.LEATHER | ArmorMaterial.DRAGONHIDE =>
      Color.LIGHT_BROWN
    case ArmorMaterial.CHAINMAIL | ArmorMaterial.COLD_IRON | ArmorMaterial.MITHRIL | ArmorMaterial.MAGEPLATE | ArmorMaterial.PLATE =>
      Color.GRAY
    case _ =>
      Color.ERROR
  }
}
