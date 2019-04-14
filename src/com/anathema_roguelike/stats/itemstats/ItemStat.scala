package com.anathema_roguelike
package stats.itemstats

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.Stat

abstract class ItemStat(val item: Item) extends Stat[Item](item) {
  protected var base = 0.0

  override def getAmount: Double = base

  def setScore(base: Int): Unit = {
    this.base = base
  }
}

