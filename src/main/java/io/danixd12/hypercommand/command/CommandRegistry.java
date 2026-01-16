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

package io.danixd12.hypercommand.command;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import io.danixd12.hypercommand.command.core.adapter.CommandAdapter;
import io.danixd12.hypercommand.command.core.descriptor.CommandDescriptor;
import io.danixd12.hypercommand.command.core.descriptor.SubCommandDescriptor;
import io.danixd12.hypercommand.command.types.Command;
import io.danixd12.hypercommand.command.types.ParentCommand;
import io.danixd12.hypercommand.command.types.SubCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandRegistry {

    private static JavaPlugin base;

    public static void registerCommand(Class<?> commandClass){

        if (!commandClass.isAnnotationPresent(Command.class))
            throw new RuntimeException("Class is not a command");

        Command c = commandClass.getAnnotation(Command.class);

        CommandDescriptor cmdDescriptor;

        Object clazz;
        try {
            clazz = commandClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        cmdDescriptor = new CommandDescriptor(c.name(), c.description(), c.aliases(), c.permissions(), c.requiresConfirmation(), clazz);

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(ParentCommand.class)) {
                cmdDescriptor.setMethod(method);
            } else if (method.isAnnotationPresent(SubCommand.class)){
                SubCommand subCommand = method.getAnnotation(SubCommand.class);
                cmdDescriptor.addSubcommand(subCommand.name(),
                        new SubCommandDescriptor(subCommand.name(), subCommand.description(), subCommand.aliases(), subCommand.permissions(), subCommand.requiresConfirmation(), method));
            }

        }

        registerCommandInternal(c.name(), cmdDescriptor);

    }

    private static void registerCommandInternal(String name, CommandDescriptor desc) {

        CommandAdapter commandAdapter = new CommandAdapter(desc);

        base.getCommandRegistry().registerCommand(commandAdapter);

        System.out.println("Registered command -> " + name);

    }

    public static void setBasePlugin(JavaPlugin plugin) {

        if (base != null)
            throw new RuntimeException("Base plugin already initialized!");

        base = plugin;

    }

}
