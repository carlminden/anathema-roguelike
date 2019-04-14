

package com.anathema_roguelike
package entities.items.armor

import com.anathema_roguelike.entities.items.{Item, ItemType}
import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Effect, Modifier}
import com.anathema_roguelike.stats.itemstats.{AttenuationDefense, ConcealmentDefense, ItemStat, VeilDefense, Weight}

class ArmorType(name: String, weight: Double) extends ArmorProperty(name, weight) with ItemType[Armor] {

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect(
      this,List(
        new Modifier[ConcealmentDefense](AdditiveCalculation.build(() => getConcealment)),
        new Modifier[AttenuationDefense](AdditiveCalculation.build(() => getAttenuation)),
        new Modifier[VeilDefense](AdditiveCalculation.build(() => getVeil)),
        new Modifier[Weight](AdditiveCalculation.build(() => getWeight)))
      )
  }
}
