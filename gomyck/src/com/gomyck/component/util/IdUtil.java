package com.gomyck.component.util;

import java.util.UUID;

public class IdUtil {
	
	public static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }
	
}
