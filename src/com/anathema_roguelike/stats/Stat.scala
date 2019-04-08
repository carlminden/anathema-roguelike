

package com.anathema_roguelike
package stats

abstract class Stat[T](var obj: T) {
  def getObject: T = obj

  def getAmount: Double
}
