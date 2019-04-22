

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
package main.utilities.datastructures

import scala.collection.{IndexedSeqLike, IndexedSeqOptimized, mutable}


object ExtensibleIndexedSeq {
  def newBuilder[T]: mutable.Builder[T, ExtensibleIndexedSeq[T]] = new mutable.Builder[T, ExtensibleIndexedSeq[T]]() {

    var build: List[T] = List[T]()

    override def +=(elem: T): this.type = {
      build :+= elem

      this
    }

    override def clear(): Unit = {
      build = List[T]()
    }

    override def result(): ExtensibleIndexedSeq[T] = new ExtensibleIndexedSeq[T](build)
  }
}

class ExtensibleIndexedSeq[+T](initial: Iterable[T] = Vector[T]()) extends IndexedSeq[T] {

  private val vector: Vector[T] = initial.toVector

  protected def getVector: Vector[T] = vector

  override def length: Int = vector.length

  override def apply(i: Int): T = getVector(i)

  override def seq: IndexedSeq[T] = vector
}
