package com.antisleuthsecurity.asc_api.rest.responses;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SendMessageResponse extends ASResponse implements Serializable {

}
