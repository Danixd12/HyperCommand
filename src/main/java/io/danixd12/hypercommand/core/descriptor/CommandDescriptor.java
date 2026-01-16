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

import java.lang.reflect.Method;
import java.util.HashMap;

public final class CommandDescriptor extends AbstractCommandDescriptor {

    private final HashMap<String, SubCommandDescriptor> subcommandList = new HashMap<>();

    public CommandDescriptor(String name, String description, String[] aliases, String perms, boolean requiresConfirmation, Object commandInstance, Method method) {

        super(name, description, aliases, perms, requiresConfirmation, commandInstance, method);

    }

    public void addSubcommand(SubCommandDescriptor desc) {

        if (subcommandList.containsKey(desc.name))
            throw new IllegalStateException("Duplicated command name for: " + desc.name);

        this.subcommandList.put(desc.name, desc);

    }

    public HashMap<String, SubCommandDescriptor> getSubcommandList() {
        return subcommandList;
    }

}
