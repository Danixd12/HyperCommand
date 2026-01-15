package d.studio.hypercommand.command;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import d.studio.hypercommand.command.core.CommandDescriptor;

import javax.annotation.Nonnull;
import java.util.List;

public class CommandAdapter extends CommandBase {

    private final CommandDescriptor descriptor;
    private RequiredArg<List<String>> argsArgument;

    public CommandAdapter(CommandDescriptor descriptor) {

        super(descriptor.name, descriptor.description, descriptor.requiresConfirmation);

        this.descriptor = descriptor;

        if (descriptor.perms != null && !descriptor.perms.isEmpty()) {
            this.requirePermission(descriptor.perms);
        }

        if (descriptor.aliases != null && descriptor.aliases.length > 0) {
            this.addAliases(descriptor.aliases);
        }

        descriptor.getSubcommandList().forEach((name, _) -> {

            // TODO COMPLETE SUBCOMMANDS
            this.addSubCommand(new CommandBase(name) {
                @Override
                protected void executeSync(@Nonnull CommandContext commandContext) {
                    descriptor.executeSubCommand(name, commandContext);
                }
            });

        });

    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {

        descriptor.executeParentCommand(commandContext);

    }

}
