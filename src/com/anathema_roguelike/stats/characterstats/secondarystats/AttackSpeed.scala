

package com.anathema_roguelike
package stats.characterstats.secondarystats

import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.stats.itemstats.WeaponSpeed
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.Stat.CharacterStat

class AttackSpeed(character: Character) extends CharacterStat(character) {
  override def getAmount: Double = {
    getObject.getInventory.getSlot(classOf[PrimaryWeapon]).getEquippedItem.get.getStatAmount[WeaponSpeed]
  }
}