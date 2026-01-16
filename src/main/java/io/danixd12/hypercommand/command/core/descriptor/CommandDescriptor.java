/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.danixd12.hypercommand.command.core.descriptor;

import java.util.HashMap;

public final class CommandDescriptor extends AbstractCommandDescriptor {

    private final HashMap<String, SubCommandDescriptor> subcommandList = new HashMap<>();

    public CommandDescriptor(String name, String description, String[] aliases, String perms, boolean requiresConfirmation, Object instancedClass) {

        super(name, description, aliases, perms, requiresConfirmation);

    }

    public void addSubcommand(String name, SubCommandDescriptor desc) {
        this.subcommandList.put(name, desc);
    }

    public HashMap<String, SubCommandDescriptor> getSubcommandList() {
        return subcommandList;
    }

}
