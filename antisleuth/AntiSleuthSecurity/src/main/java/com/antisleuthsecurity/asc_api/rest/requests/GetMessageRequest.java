/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest.requests;

/**
 * @author Bob Schmidinger, schmrj@comcast.net
 * 
 */
public class GetMessageRequest extends ASRequest {
	private Integer messageId = null;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

}
