package d.studio.hypercommand.command.core;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import d.studio.hypercommand.command.types.Arg;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public final class CommandDescriptor {

    private final HashMap<String, SubCommandDescriptor> subcommandList = new HashMap<>();

    public final String name;
    public final String[] aliases;
    public final String perms;
    public final String description;
    public final boolean requiresConfirmation;

    private final Object instancedClass;

    private Method parentCommand;

    public CommandDescriptor(String name, String description, String[] aliases, String perms, boolean requiresConfirmation, Object instancedClass) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.perms = perms;
        this.requiresConfirmation = requiresConfirmation;
        this.instancedClass = instancedClass;
    }

    public void addSubcommand(String name, SubCommandDescriptor desc) {
        this.subcommandList.put(name, desc);
    }

    public void setParentCommand(Method parentCommand) {

        if (this.parentCommand != null)
            throw new RuntimeException("Parent command already set!");
        else
            this.parentCommand = parentCommand;

    }

    public void executeSubCommand(String name, CommandContext ctx) {
        try {

            if (existsSubCommand(name)) {

                Field ct = instancedClass.getClass().getDeclaredField("ctx");
                ct.setAccessible(true);
                ct.set(instancedClass, ctx);

                SubCommandDescriptor subCommandDescriptor = subcommandList.get(name);

                Object[] parameters = parseParameters(subcommandList.get(name).method, ctx);

                subCommandDescriptor.method.invoke(instancedClass, parameters);

            } else {
                System.out.println("Not found! " + name);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    public void executeParentCommand(CommandContext ctx) {

        try {

            if (parentCommand != null) {

                Object[] parameters = parseParameters(parentCommand, ctx);

                parentCommand.invoke(instancedClass, parameters);

            } else {
                System.out.println("-");
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private Object[] parseParameters(Method method, CommandContext ctx) {

        Object[] parameters = new Object[method.getParameters().length];

        for (int i = 0; i < parameters.length; i++) {

            Parameter param = method.getParameters()[i];

            // TODO params
            if (param.isAnnotationPresent(Arg.class)) {

            }
                //parameters[i] = ctx.get(i+1, param.getType());
                //parameters[i] = ctx.get();

        }
        return parameters;
    }

    public HashMap<String, SubCommandDescriptor> getSubcommandList() {
        return subcommandList;
    }

    public boolean existsSubCommand(String name) {
        return this.subcommandList.containsKey(name);
    }

    public record SubCommandDescriptor(String name, String perms, int params, Method method) {}

}
