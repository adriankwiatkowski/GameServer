package com.example.gameserver.util;

public final class Authority {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    private static final String SCOPE_PREFIX = "SCOPE_";

    public static final String USER_SCOPE = SCOPE_PREFIX + USER_ROLE;
    public static final String ADMIN_SCOPE = SCOPE_PREFIX + ADMIN_ROLE;

    public static final String HAS_USER_SCOPE = "hasAuthority('" + USER_SCOPE + "')";
    public static final String HAS_ADMIN_SCOPE = "hasAuthority('" + ADMIN_SCOPE + "')";

    private Authority() {
    }
}
