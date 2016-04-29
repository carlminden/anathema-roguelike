/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.main.ui.messages;

import java.util.Arrays;
import java.util.Iterator;

import com.google.common.collect.EvictingQueue;

public class MessageBuffer implements Iterable<Message> {
	
	private int width;
	private int height;
	private int maxSize = 200;
	
	private String lastString;
	private Message lastMessage;
	
	EvictingQueue<Message> queue;
	
	public MessageBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.queue = EvictingQueue.create(maxSize);
	}
	
	public void addMessage(Message message) {
		
		if(lastString != null && lastString.equals(message.toString())) {
			lastMessage.repeat();
		} else {
			for(Message m : Message.split(message, width)) {
				queue.add(m);
				lastMessage = m;
				lastString = m.toString();
			}
		}
	}

	@Override
	public Iterator<Message> iterator() {
		return queue.iterator();
	}
	
	public int size() {
		return queue.size();
	}
	
	public Message[] toArray() {
		return queue.toArray(new Message[0]);
	}
	
	public Message[] getVisibleMessages() {
		
		int end = maxSize - queue.remainingCapacity();
		int start = Math.max(0, end - height);
		
		return Arrays.copyOfRange(toArray(), start, end, Message[].class);
	}
}