package com.eventBooker.security.utils;

import java.util.List;

public class SecurityUtils {
    private SecurityUtils(){}
    public static final List<String> END_POINTS = List.of("api/v1/auth","api/v1/organizer");
    public static final String JWT_PREFIX = "Bearer ";
}
