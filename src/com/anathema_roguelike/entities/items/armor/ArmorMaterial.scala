

package com.anathema_roguelike
package entities.items.armor

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.effects.{Effect, Modifier, MultiplicativeCalculation}
import com.anathema_roguelike.stats.itemstats.{AttenuationDefense, ConcealmentDefense, ItemStat, VeilDefense, Weight}

object ArmorMaterial {
  val CLOTH = "Cloth"
  val LEATHER = "Leather"
  val CHAINMAIL = "Chainmail"
  val PLATE = "Plate"
  val UMBRALSILK = "Umbralsilk"
  val DRAGONHIDE = "Dragonhide"
  val COLD_IRON = "Cold Iron"
  val BLACKSTEEL = "Blacksteel"
  val SHADOWEAVE = "Shadoweave"
  val SILENAI_CRYSTAL = "Silenai Crystal"
  val MITHRIL = "Mithril"
  val MAGEPLATE = "Mageplate"
}

class ArmorMaterial(name: String, weight: Double) extends ArmorProperty {

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect(this, List(
      new Modifier[ConcealmentDefense](MultiplicativeCalculation.build(() => getConcealment)),
      new Modifier[AttenuationDefense](MultiplicativeCalculation.build(() => getAttenuation)),
      new Modifier[VeilDefense](MultiplicativeCalculation.build(() => getVeil)),
      new Modifier[Weight](MultiplicativeCalculation.build(() => getWeight))
    ))
  }
}
