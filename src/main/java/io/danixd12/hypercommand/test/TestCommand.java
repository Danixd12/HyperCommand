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

package io.danixd12.hypercommand.test;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import io.danixd12.hypercommand.command.types.Arg;
import io.danixd12.hypercommand.command.types.Command;
import io.danixd12.hypercommand.command.types.ParentCommand;
import io.danixd12.hypercommand.command.types.SubCommand;

@Command(
        name = "test",
        aliases = {"t", "t1"},
        permissions = "cmd.test"
)
public class TestCommand {

    CommandContext ctx;

    @ParentCommand
    void parent() {

        ctx.sendMessage(Message.raw(
                "Hello " + ctx.sender() + ", from parent!"
        ));

    }

    @SubCommand(
            name = "subtest",
            permissions = "cmd.subtest"
    )
    void hello(@Arg                  String name,
               @Arg(required = true) int age) {

        ctx.sendMessage(Message.raw(
                "Hello " + name + ", you are " + age + " years old"
        ));

    }

}
