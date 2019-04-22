/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike
package actors

object Duration {
  def INSTANT = new Duration(0)
  def EXTREMELY_SHORT = new Duration(1)
  def VERY_SHORT = new Duration(3)
  def SHORT = new Duration(5)
  def MEDIUM = new Duration(10)
  def LONG = new Duration(25)
  def VERY_LONG = new Duration(50)
  def EXTREMELY_LONG = new Duration(100)
  def PERMANENT = new Duration(Integer.MIN_VALUE)

  def copy(duration: Duration) = new Duration(duration.getInitialDuration)

  implicit def DurationToInt(duration: Duration): Int = duration.getInitialDuration
}

class Duration(var initialDuration: Int) extends Ordered[Duration] {
  private var remainingDuration: Double = initialDuration

  def getInitialDuration: Int = initialDuration

  def delay(amount: Double): Unit = {
    if(initialDuration != Duration.PERMANENT) {
      remainingDuration += amount
    }
  }

  def elapse(amount: Double): Unit = {
    if(initialDuration != Duration.PERMANENT) {
      remainingDuration -= amount
    }
  }

  def isExpired: Boolean = {
    if(initialDuration == Duration.PERMANENT) {
      false
    } else remainingDuration <= 0
  }

  def getRemaining: Double = {
    remainingDuration
  }

  override def compare(duration: Duration): Int = {
    val diff = getRemaining - duration.getRemaining

    if(diff > 0) {
      1
    } else if(diff < 0) {
      -1
    } else 0
  }

}