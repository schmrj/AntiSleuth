/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest.requests;

import java.io.Serializable;

import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;

public class SendMessageRequest extends ASRequest implements Serializable {
	private MessageParts msgParts = null;

	public MessageParts getMsgParts() {
		return msgParts;
	}

	public void setMsgParts(MessageParts msgParts) {
		this.msgParts = msgParts;
	}

}
