/*
 * *************************************************************************
 *  Copyright 2026 Danixd12
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *************************************************************************
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
