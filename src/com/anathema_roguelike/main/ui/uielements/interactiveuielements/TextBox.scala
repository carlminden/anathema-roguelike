

package com.anathema_roguelike
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SquidInput

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class TextBox(location: Point, width: Int, height: Int, background: Float, text: Option[Message] = None, title: Option[String] = None)
  extends InteractiveUIElement[Void](location, width, height, background, true, title) {

  private var messages = new ListBuffer[Message]
  private var position: Int = 0

  text.foreach(t => setMessage(t))


  override protected def renderContent(): Unit = {

    (0 until Math.min(getHeight, messages.size - position)).foreach(i => {
      messages.get(i + position).render(getX, getY + i)
    })

    if (messages.size - 1 > getHeight - 1 + position) {
      renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, getHeight - 1, "\u25bc")
    }

    if (position > 0) {
      renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, 0, "\u25b2")
    }

    if (messages.size > getHeight) {
      renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, ((position.toFloat / (messages.size.toFloat - getHeight.toFloat)) * (getHeight - 3).toFloat + 1).toInt, "\u2588")
    }
  }

  def setPosition(position: Int): Unit = {
    this.position = position
  }

  def setMessage(text: Message): Unit = {
    messages = ListBuffer.from(Message.split(text, getWidth - 1).asScala)
  }

  def setMessages(messages: Iterable[Message]): Unit = {

    this.messages = ListBuffer.from(messages).flatMap(m => {
      Message.split(m, getWidth - 1).asScala
    })
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    key match {
      case SquidInput.PAGE_UP =>
        if (messages.size > getHeight) position = Utils.clamp(position - 5, 0, messages.size - getHeight)
      case SquidInput.PAGE_DOWN =>
        if (messages.size > getHeight) position = Utils.clamp(position + 5, 0, messages.size - getHeight)
      case _ =>
    }
  }

  override def processScrollEvent(amount: Int): Boolean = {
    if (messages.size > getHeight) position = Utils.clamp(position + amount, 0, messages.size - getHeight)
    true
  }

  override def registerMouseCallbacks(): Unit = {

  }
}
