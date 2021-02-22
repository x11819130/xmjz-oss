package com.xmjz.oss.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * java.util.Objects 的扩展
 *
 * @author chengz
 */
public class Objects {

    public static <T extends Collection<?>> T requireNonEmpty(T obj) {
        if (obj.isEmpty()) {
            throw new EmptyCollectionException();
        }
        return obj;
    }

    public static <T extends Collection<?>> T requireNonEmpty(T obj, String message) {
        if (obj.isEmpty()) {
            throw new EmptyCollectionException(message);
        }
        return obj;
    }

    public static <T extends Collection<?>> T requireNonEmpty(T obj, Supplier<String> messageSupplier) {
        if (obj.isEmpty()) {
            throw new EmptyCollectionException(messageSupplier.get());
        }
        return obj;
    }

    @SafeVarargs
    public static <T> boolean isIn(T o, T... os) {
        for (T oo : os) {
            if (o.equals(oo)) {
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    public static <T> boolean isNotIn(T o, T... os) {
        for (T oo : os) {
            if (o.equals(oo)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(Collection<?> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isNotEmpty(Object... ids) {
        return ids != null && ids.length != 0;
    }

    public static boolean isExistNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj != null) {
                return true;
            }
        }
        return false;
    }


    public static <T> List<T> oneToList(T one) {
        return new ArrayList<T>(1) {{
            if (one != null) {
                add(one);
            }
        }};
    }

    public static String[] toStrings(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return new String[0];
        } else {
            if (objects[0] instanceof String) {
                return (String[]) objects;
            } else {
                return Arrays.stream(objects).map(String::valueOf).toArray(String[]::new);
            }
        }
    }

    static class EmptyCollectionException extends RuntimeException {
        public EmptyCollectionException() {
            super();
        }

        public EmptyCollectionException(String s) {
            super(s);
        }
    }

    public static <T> T defaultIfNull(T obj, T defaultValue) {
        return obj == null ? defaultValue : obj;
    }
}
