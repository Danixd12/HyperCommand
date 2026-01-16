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

package io.danixd12.hypercommand.utils;

import io.danixd12.hypercommand.core.CommandMeta;
import io.danixd12.hypercommand.core.descriptor.CommandDescriptor;
import io.danixd12.hypercommand.core.descriptor.SubCommandDescriptor;
import io.danixd12.hypercommand.types.Command;
import io.danixd12.hypercommand.types.ParentCommand;
import io.danixd12.hypercommand.types.SubCommand;

import java.lang.reflect.Method;

public final class RegistryHelper {

    private RegistryHelper() {}

    public static void addSubCommands(Class<?> commandClass, Object commandInstance, CommandDescriptor parentDescriptor) {

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(SubCommand.class)){

                SubCommand subCommand = method.getAnnotation(SubCommand.class);

                parentDescriptor.addSubcommand(
                        new SubCommandDescriptor(
                                new CommandMeta(
                                        subCommand.name(),
                                        subCommand.description(),
                                        subCommand.aliases(),
                                        subCommand.permissions(),
                                        subCommand.requiresConfirmation()
                                ),
                                commandInstance,
                                method
                        )
                );

            }

        }

    }

    public static CommandDescriptor findParentCommand(Class<?> commandClass, Object commandInstance, Command command) {

        CommandDescriptor parentDescriptor = null;

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(ParentCommand.class)) {

                parentDescriptor = new CommandDescriptor(
                        new CommandMeta(
                                command.name(),
                                command.description(),
                                command.aliases(),
                                command.permissions(),
                                command.requiresConfirmation()
                        ),
                        commandInstance,
                        method
                );

                break;

            }
        }

        return parentDescriptor;

    }

}
