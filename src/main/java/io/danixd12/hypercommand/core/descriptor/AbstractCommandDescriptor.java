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

package io.danixd12.hypercommand.core.descriptor;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import io.danixd12.hypercommand.core.CommandArgumentParser;
import io.danixd12.hypercommand.core.CommandMeta;
import io.danixd12.hypercommand.exceptions.CommandExecutionException;
import io.danixd12.hypercommand.utils.ArgumentConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public sealed abstract class AbstractCommandDescriptor extends CommandMeta permits CommandDescriptor, SubCommandDescriptor {

    private final Object commandInstance;

    private final Method method;

    public AbstractCommandDescriptor(CommandMeta meta, Object commandInstance, Method method) {

        super(meta);

        this.commandInstance = commandInstance;

        this.method = method;
        this.method.setAccessible(true);

    }

    public final void execute(CommandContext ctx, List<CommandArgumentParser> args) {

        try {

            for (Field field : commandInstance.getClass().getDeclaredFields()) {

                if (field.getType() != CommandContext.class)
                    continue;

                field.setAccessible(true);
                field.set(commandInstance, ctx);

                break;

            }

            Object[] parameters = ArgumentConverter.parseParameters(method, ctx, args);

            method.invoke(commandInstance, parameters);

        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new CommandExecutionException(name, ex.getCause());
        }

    }

    public Method getMethod() {
        return method;
    }

}
