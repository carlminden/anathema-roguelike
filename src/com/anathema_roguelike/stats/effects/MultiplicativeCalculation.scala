package com.anathema_roguelike
package stats.effects

object MultiplicativeCalculation {
  def build(mc: MultiplicativeCalculation): MultiplicativeCalculation = mc

  def fixed(value: Double): MultiplicativeCalculation = () => value
}

trait MultiplicativeCalculation extends Calculation {}