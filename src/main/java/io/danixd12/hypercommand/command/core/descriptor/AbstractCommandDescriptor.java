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

package io.danixd12.hypercommand.command.core.descriptor;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import io.danixd12.hypercommand.command.core.CommandArgumentParser;
import io.danixd12.hypercommand.command.utils.ArgumentConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractCommandDescriptor {

    public final String name;
    public final String[] aliases;
    public final String perms;
    public final String description;
    public final boolean requiresConfirmation;

    private Method method;

    public AbstractCommandDescriptor(String name, String description, String[] aliases, String perms, boolean requiresConfirmation) {

        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.perms = perms;

        this.requiresConfirmation = requiresConfirmation;

    }

    public void setMethod(Method method) {

        if (this.method != null) {
            throw new RuntimeException("Parent command already set!");
        } else {

            this.method = method;

            if (!this.method.canAccess(method))
                this.method.setAccessible(true);

        }

    }

    public Method getMethod() {
        return method;
    }

    public final void execute(CommandContext ctx, List<CommandArgumentParser> args) {

        try {

            for (Field field : method.getClass().getDeclaredFields()) {

                if (field.getType() != CommandContext.class)
                    continue;

                field.setAccessible(true);
                field.set(method, ctx);

                break;

            }

            Object[] parameters = ArgumentConverter.parseParameters(method, ctx, args);

            method.invoke(method, parameters);

        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

    }

}
