

package com.anathema_roguelike
package stats.effects

class FixedCalculation(var fixed: Double) extends Calculation {
  override def apply(): Double = fixed
}