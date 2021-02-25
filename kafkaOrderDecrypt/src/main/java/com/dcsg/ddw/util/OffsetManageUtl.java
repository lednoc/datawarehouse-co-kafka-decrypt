package com.dcsg.ddw.util;

import org.springframework.stereotype.Component;

@Component
public class OffsetManageUtl {
	
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
