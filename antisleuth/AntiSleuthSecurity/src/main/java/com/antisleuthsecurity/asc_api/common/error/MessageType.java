/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.common.error;

import java.io.Serializable;

public enum MessageType implements Serializable {
	ERROR, WARNING, INFO, FATAL;
}
