package com.example.gameserver.model;

public final class Authority {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String SCOPE_ADMIN = "hasAuthority('SCOPE_" + ROLE_ADMIN + "')";

    private Authority() {
    }
}
