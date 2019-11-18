package yich.download.local.picocli;

import picocli.CommandLine;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


abstract public class MyCommand<T> implements Callable<T> {
    private String toStr(Object value){
        return value == null ? null : String.valueOf(value);
    }

    abstract protected Class getClassType();

    protected Map<String, String> parameters() throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Field[] fields = getClassType().getDeclaredFields();

        String value;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].isAnnotationPresent(CommandLine.Option.class)) {
                value = toStr(fields[i].get(this));
                if (value != null) {
                    map.put(fields[i].getName(), value);
                }
            }
        }
        return map;
    }

    public T call(String[] opts){
        return CommandLine.call(this, opts);
    }


}
