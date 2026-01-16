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

package io.danixd12.hypercommand.core;

import java.util.Arrays;
import java.util.Objects;

public class CommandMeta implements Cloneable {

    protected final String name;
    protected final String description;
    protected final String[] aliases;
    protected final String perms;
    protected final boolean requiresConfirmation;

    public CommandMeta(String name, String description, String[] aliases, String perms, boolean requiresConfirmation) {

       this.name = name;
       this.description = description;
       this.aliases = aliases;
       this.perms = perms;
       this.requiresConfirmation = requiresConfirmation;

    }

    protected CommandMeta(CommandMeta meta) {

        this.name = meta.getName();
        this.description = meta.getDescription();
        this.aliases = meta.getAliases();
        this.perms = meta.getPerms();
        this.requiresConfirmation = meta.isConfirmationRequired();

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPerms() {
        return perms;
    }

    public boolean isConfirmationRequired() {
        return requiresConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommandMeta that = (CommandMeta) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public CommandMeta clone() {
        try {
            return (CommandMeta) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return  "- Command information -" +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nAliases: " + Arrays.toString(aliases) +
                "\nPermissions: " + perms +
                "\nRequires confirmation: " + requiresConfirmation;
    }

}
