package d.studio.hypercommand.command.core;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import d.studio.hypercommand.command.CommandAdapter;
import d.studio.hypercommand.command.types.Command;
import d.studio.hypercommand.command.types.ParentCommand;
import d.studio.hypercommand.command.types.SubCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandRegistry {

    public static JavaPlugin base;

    public static void registerCommand(Class<?> commandClass){

        if (!commandClass.isAnnotationPresent(Command.class))
            throw new RuntimeException("Class is not a command");

        Command c = commandClass.getAnnotation(Command.class);

        CommandDescriptor cmdDescriptor;

        try {
            cmdDescriptor = new CommandDescriptor(c.name(), c.description(), c.aliases(), c.permissions(), c.requiresConfirmation(), commandClass.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (Method method : commandClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(ParentCommand.class)) {
                cmdDescriptor.setParentCommand(method);
            } else if (method.isAnnotationPresent(SubCommand.class)){
                SubCommand subCommand = method.getAnnotation(SubCommand.class);
                cmdDescriptor.addSubcommand(subCommand.name(), new CommandDescriptor.SubCommandDescriptor(subCommand.name(), subCommand.permissions(), method.getParameters().length, method));
            }

        }

        registerCommandInternal(c.name(), cmdDescriptor);

    }

    private static void registerCommandInternal(String name, CommandDescriptor desc) {

        CommandAdapter commandAdapter = new CommandAdapter(desc);

        base.getCommandRegistry().registerCommand(commandAdapter);

        System.out.println("Registered command -> " + name);

    }


}
