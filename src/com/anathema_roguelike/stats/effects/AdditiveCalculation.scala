

package com.anathema_roguelike
package stats.effects

object AdditiveCalculation {
  def build(ac: AdditiveCalculation): AdditiveCalculation = ac

  def fixed(value: Double): AdditiveCalculation = () => value
}

trait AdditiveCalculation extends Calculation {}
