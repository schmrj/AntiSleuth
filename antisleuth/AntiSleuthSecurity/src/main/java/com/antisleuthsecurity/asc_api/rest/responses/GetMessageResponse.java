package com.antisleuthsecurity.asc_api.rest.responses;

import java.util.TreeMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GetMessageResponse extends ASResponse {
	private TreeMap<Integer, MessageParts> msgs = new TreeMap<Integer, MessageParts>();

	public TreeMap<Integer, MessageParts> getMsgs() {
		return msgs;
	}

	public void setMsgs(TreeMap<Integer, MessageParts> msgs) {
		this.msgs = msgs;
	}

	public void addMsg(Integer msgId, MessageParts message) {
		this.msgs.put(msgId, message);
	}
}
