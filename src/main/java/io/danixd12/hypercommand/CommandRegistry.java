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

package io.danixd12.hypercommand;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import io.danixd12.hypercommand.exceptions.CommandRegistrationException;
import io.danixd12.hypercommand.core.adapter.CommandAdapter;
import io.danixd12.hypercommand.core.descriptor.CommandDescriptor;
import io.danixd12.hypercommand.core.descriptor.SubCommandDescriptor;
import io.danixd12.hypercommand.types.Command;
import io.danixd12.hypercommand.types.ParentCommand;
import io.danixd12.hypercommand.types.SubCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandRegistry {

    private static JavaPlugin base;

    public static void registerCommand(Class<?> commandClass){

        if (!commandClass.isAnnotationPresent(Command.class))
            throw new RuntimeException("Class provided is not a command class!");

        Command parentCommand = commandClass.getAnnotation(Command.class);

        Object commandInstance;

        try {
            commandInstance = commandClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        CommandDescriptor parentDescriptor = findParentCommand(commandClass, commandInstance, parentCommand);

        if (parentDescriptor != null)
            addSubCommands(commandClass, commandInstance, parentDescriptor);
        else
            throw new CommandRegistrationException(parentCommand.name());

        registerCommandInternal(parentDescriptor);

    }

    private static void addSubCommands(Class<?> commandClass, Object commandInstance, CommandDescriptor parentDescriptor) {

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(SubCommand.class)){

                SubCommand subCommand = method.getAnnotation(SubCommand.class);

                parentDescriptor.addSubcommand(
                        new SubCommandDescriptor(
                                subCommand.name(),
                                subCommand.description(),
                                subCommand.aliases(),
                                subCommand.permissions(),
                                subCommand.requiresConfirmation(),
                                commandInstance,
                                method
                        )
                );

            }

        }

    }

    private static CommandDescriptor findParentCommand(Class<?> commandClass, Object commandInstance, Command command) {

        CommandDescriptor parentDescriptor = null;

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(ParentCommand.class)) {

                parentDescriptor = new CommandDescriptor(
                        command.name(),
                        command.description(),
                        command.aliases(), 
                        command.permissions(),
                        command.requiresConfirmation(),
                        commandInstance,
                        method
                );

                break;

            }
        }

        return parentDescriptor;

    }

    private static void registerCommandInternal(CommandDescriptor desc) {

        CommandAdapter commandAdapter = new CommandAdapter(desc);

        base.getCommandRegistry().registerCommand(commandAdapter);

        System.out.println("Registered command -> " + desc.getName());

    }

    public static void setBasePlugin(JavaPlugin plugin) {

        if (base != null)
            throw new RuntimeException("Base plugin already initialized!");

        base = plugin;

    }

}
