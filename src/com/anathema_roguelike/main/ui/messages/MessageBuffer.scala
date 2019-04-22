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
package main.ui.messages

import com.google.common.collect.EvictingQueue
import scala.collection.JavaConverters._

class MessageBuffer(width: Int, height: Int) {


  private val maxSize = 200
  private var lastString: Option[String] = None
  private var lastMessage: Option[Message] = None

  private val queue = EvictingQueue.create[Message](maxSize)

  def addMessage(message: Message): Unit = {
    if(lastMessage.isDefined && lastMessage.contains(message)) {
      lastMessage.get.repeat()
    } else {
      for (m <- Message.split(message, width)) {
        queue.add(m)
        lastMessage = m
        lastString = m.toString
      }
    }
  }

  def size = queue.size()

  def toIterable: Iterable[Message] = queue.asScala

  def getVisibleMessages: Array[Message] = {
    val end = maxSize - queue.remainingCapacity
    val start = Math.max(0, end - height)

    toIterable.toArray.slice(start, end)
  }
}