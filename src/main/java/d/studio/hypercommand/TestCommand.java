package d.studio.hypercommand;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import d.studio.hypercommand.command.types.Arg;
import d.studio.hypercommand.command.types.Command;
import d.studio.hypercommand.command.types.ParentCommand;
import d.studio.hypercommand.command.types.SubCommand;

@Command(
        name = "test",
        aliases = {"t", "t1"},
        permissions = "cmd.test"
)
public class TestCommand {

    CommandContext ctx;

    @ParentCommand
    public void parent() {
        System.out.println("hello " + ctx.sender() + " from parent");
    }

    @SubCommand(
            name = "pepe",
            permissions = "cmd.pepe"
    )
    public void hello(@Arg String nombre, @Arg int edad) {
        System.out.println("hello " + ctx.sender().getDisplayName() + " from child tienes " + edad + " años");
    }

}
