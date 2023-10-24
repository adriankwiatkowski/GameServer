package com.example.gameserver.model;

public final class Authority {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String ADMIN_SCOPE = "hasAuthority('SCOPE_" + ADMIN_ROLE + "')";

    private Authority() {
    }
}
