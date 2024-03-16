package com.x_tornado10.pvpevent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    // initialize placeholder lists
    private final HashMap<String, placeholder_type> placeholders;
    private final HashMap<String, String> fixed_placeholders;
    public MessageHandler() {
        placeholders = new HashMap<>();
        placeholders.put("%time_elapsed%", placeholder_type.PROVIDED);
        fixed_placeholders = new HashMap<>();
    }

    // replace placeholders in the message if there are any
    @Nonnull
    private String apply_placeholders(@Nonnull String message, @Nonnull String placeholder) {
        // check if the message contains a placeholder regex, if not return the message unchanged
        if (!message.contains("%")) return message;
        String result = message;
        // iterate through known placeholders to check if the message contains one
        for (Map.Entry<String, placeholder_type> entry : placeholders.entrySet()) {
            String key = entry.getKey();
            if (message.contains(key)) {
                // check if the placeholder is a fixed value or is dynamic (provided as argument)
                switch (entry.getValue()) {
                    case PROVIDED -> {
                        // replace the placeholder with the provided replacement
                        result = result.replaceAll(key, placeholder);
                    }
                    case FIXED -> {
                        // replace the placeholder with a fixed value specified in fixed_placeholders
                        result = result.replaceAll(key, fixed_placeholders.get(key));
                    }
                }
            }
        }
        return result;
    }
}
