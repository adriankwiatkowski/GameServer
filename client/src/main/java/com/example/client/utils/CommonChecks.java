package com.example.client.utils;

import net.synedra.validatorfx.Check;

public class CommonChecks {
    public static void required(Check.Context context, String item) {
        String text = context.get(item);
        if (text == null || text.isEmpty()) {
            context.error("This field is required.");
        }
    }
}
