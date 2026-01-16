/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.danixd12.hypercommand.command.core;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;

public record CommandArgumentParser(Object arg, ArgumentType type) {

    public Object getRawValue(CommandContext ctx) {

        return switch (type) {
            case REQUIRED -> ((RequiredArg<?>) arg).get(ctx);
            case OPTIONAL -> ((OptionalArg<?>) arg).get(ctx);
            case DEFAULT -> ((DefaultArg<?>) arg).get(ctx);
        };

    }

    public enum ArgumentType {
        REQUIRED,
        OPTIONAL,
        DEFAULT
    }

}
