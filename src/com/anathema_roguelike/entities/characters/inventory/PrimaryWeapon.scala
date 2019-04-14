

package com.anathema_roguelike
package entities.characters.inventory

import com.anathema_roguelike.entities.items.weapons.Weapon
import com.anathema_roguelike.entities.items.weapons.natural_weapons.Unarmed
import com.anathema_roguelike.entities.characters.Character

class PrimaryWeapon(character: Character) extends SingleSlot[Weapon](character) {
  override protected def getDefaultItem = new Unarmed(Right(character))
}