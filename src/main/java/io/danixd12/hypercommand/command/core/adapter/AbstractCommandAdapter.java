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

package io.danixd12.hypercommand.command.core.adapter;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import io.danixd12.hypercommand.command.core.CommandArgumentParser;
import io.danixd12.hypercommand.command.core.descriptor.AbstractCommandDescriptor;
import io.danixd12.hypercommand.command.types.Arg;

import javax.annotation.Nonnull;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public abstract class AbstractCommandAdapter extends CommandBase {

    private final AbstractCommandDescriptor descriptor;

    private final ArrayList<CommandArgumentParser> registeredArgs = new ArrayList<>();

    public AbstractCommandAdapter(AbstractCommandDescriptor descriptor) {

        super(descriptor.name, descriptor.description, descriptor.requiresConfirmation);

        this.descriptor = descriptor;

        if (descriptor.perms != null && !descriptor.perms.isEmpty()) {
            this.requirePermission(descriptor.perms);
        }

        if (descriptor.aliases != null && descriptor.aliases.length > 0) {
            this.addAliases(descriptor.aliases);
        }

        this.registerArguments();

    }

    private void registerArguments() {

        Parameter[] params = descriptor.getMethod().getParameters();

        for (Parameter param : params) {

            if (!param.isAnnotationPresent(Arg.class))
                continue;

            Arg argAnnotation = param.getAnnotation(Arg.class);

            CommandArgumentParser parser;
            if (argAnnotation.required()) {
                parser = createRequiredArgFor(param.getName(), param.getType());
            } else {
                parser = createOptionalArgFor(param.getName(), param.getType());
            }

            if (parser != null)
                this.registeredArgs.add(parser);

        }

    }

    private CommandArgumentParser createRequiredArgFor(String name, Class<?> type) {

        RequiredArg<?> requiredArg = null;

        if (type == String.class) {
            requiredArg = this.withRequiredArg(name, "Argument: " + name, ArgTypes.STRING);
        } else if (type == int.class || type == Integer.class) {
            requiredArg = this.withRequiredArg(name, "Argument: " + name, ArgTypes.INTEGER);
        } else if (type == double.class || type == Double.class) {
            requiredArg = this.withRequiredArg(name, "Argument: " + name, ArgTypes.DOUBLE);
        } else if (type == float.class || type == Float.class) {
            requiredArg = this.withRequiredArg(name, "Argument: " + name, ArgTypes.FLOAT);
        } else if (type == boolean.class || type == Boolean.class) {
            requiredArg = this.withRequiredArg(name, "Argument: " + name, ArgTypes.BOOLEAN);
        }

        return requiredArg != null ? new CommandArgumentParser(requiredArg, CommandArgumentParser.ArgumentType.REQUIRED) : null;

    }

    private CommandArgumentParser createOptionalArgFor(String name, Class<?> type) {

        OptionalArg<?> optionalArg = null;

        if (type == String.class) {
            optionalArg = this.withOptionalArg(name, "Argument: " + name, ArgTypes.STRING);
        } else if (type == int.class || type == Integer.class) {
            optionalArg = this.withOptionalArg(name, "Argument: " + name, ArgTypes.INTEGER);
        } else if (type == double.class || type == Double.class) {
            optionalArg = this.withOptionalArg(name, "Argument: " + name, ArgTypes.DOUBLE);
        } else if (type == float.class || type == Float.class) {
            optionalArg = this.withOptionalArg(name, "Argument: " + name, ArgTypes.FLOAT);
        } else if (type == boolean.class || type == Boolean.class) {
            optionalArg = this.withOptionalArg(name, "Argument: " + name, ArgTypes.BOOLEAN);
        }

        return optionalArg != null ? new CommandArgumentParser(optionalArg, CommandArgumentParser.ArgumentType.OPTIONAL) : null;

    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        descriptor.execute(commandContext, registeredArgs);
    }

}
