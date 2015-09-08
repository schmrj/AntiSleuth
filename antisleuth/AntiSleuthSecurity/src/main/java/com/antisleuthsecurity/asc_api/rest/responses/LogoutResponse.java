package com.antisleuthsecurity.asc_api.rest.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LogoutResponse extends ASResponse {

}
