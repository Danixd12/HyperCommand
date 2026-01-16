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

package io.danixd12.hypercommand.utils;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import io.danixd12.hypercommand.core.CommandArgumentParser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public abstract class ArgumentConverter {

    private ArgumentConverter() {}

    public static Object[] parseParameters(Method method, CommandContext ctx, List<CommandArgumentParser> args) {

        Parameter[] methodParams = method.getParameters();
        Object[] result = new Object[method.getParameters().length];

        for (int i = 0; i < result.length; i++) {

            Parameter param = methodParams[i];

            Class<?> paramType = param.getType();

            if (i < args.size()) {

                CommandArgumentParser argHolder = args.get(i);

                Object rawValue = argHolder.getRawValue(ctx);

                result[i] = convertValue(rawValue, paramType);

            } else {
                result[i] = getDefaultValue(paramType);
            }

        }

        return result;

    }

    public static Object convertValue(Object value, Class<?> targetType) {

        if (value == null) return getDefaultValue(targetType);
        if (targetType.isInstance(value)) return value;

        if (value instanceof Number num) {
            if (targetType == byte.class || targetType == Byte.class) return num.byteValue();
            if (targetType == int.class || targetType == Integer.class) return num.intValue();
            if (targetType == double.class || targetType == Double.class) return num.doubleValue();
            if (targetType == float.class || targetType == Float.class) return num.floatValue();
            if (targetType == long.class || targetType == Long.class) return num.longValue();
        }

        if (targetType == boolean.class || targetType == Boolean.class) {
            return value instanceof Boolean ? value : Boolean.parseBoolean(value.toString());
        }

        if (targetType == String.class) return value.toString();

        return value;

    }

    public static Object getDefaultValue(Class<?> type) {

        if (type == int.class || type == Integer.class) return 0;
        if (type == double.class || type == Double.class) return 0.0;
        if (type == float.class || type == Float.class) return 0.0f;
        if (type == long.class || type == Long.class) return 0L;
        if (type == boolean.class || type == Boolean.class) return false;

        return null;

    }

}
