package me.dragon.base.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.ResultTransformer;

public final class ToBeanResultTransformSafety
        implements ResultTransformer
{
    private static final long serialVersionUID = 1L;
    private final Class resultClass;

    public ToBeanResultTransformSafety(Class resultClass)
    {
        if (resultClass == null)
            throw new IllegalArgumentException("resultClass cannot be null");
        this.resultClass = resultClass;
    }

    public Object transformTuple(Object[] tuple, String[] aliases)
    {
        Object result = null;
        try
        {
            result = this.resultClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = getClassAndSuperFields(this.resultClass);

        for (int i = 0; i < aliases.length; i++) {
            try {
                Field filed = getFiledByColumnName(fields, aliases[i]);

                if (filed == null) {
                    continue;
                }
                if ((filed.getType() != null) &&
                        (filed.getType() == Integer.class)) {
                    Integer intValue = null;

                    if (StringUtils.isNotEmpty(String.valueOf(tuple[i]))) {
                        intValue = new Integer(String.valueOf(tuple[i]));
                    }
                    setter(result, filed.getName(), intValue, filed.getType());
                } else if (filed.getType() == Long.class) {
                    Long longValue = null;
                    if (StringUtils.isNotEmpty(String.valueOf(tuple[i]))) {
                        longValue = new Long(String.valueOf(tuple[i]));
                    }
                    setter(result, filed.getName(), longValue, filed.getType());
                } else if (filed.getType() == Double.class) { //新加
                    Double longValue = null;
                    if(tuple[i]!=null){
                        if (StringUtils.isNotEmpty(String.valueOf(tuple[i]))) {
                            longValue = new Double(String.valueOf(tuple[i]));
                        }
                        setter(result, filed.getName(), longValue, filed.getType());
                    }

                } else {
                    setter(result, filed.getName(), tuple[i], filed.getType());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Field getFiledByColumnName(Field[] fields, String alias)
    {
        if ((fields == null) || (fields.length == 0)) {
            throw new RuntimeException(
                    "[JAVA REFLECT ERROR] Cann't find any declared field in Class: " +
                            this.resultClass.getName());
        }

        String colName = alias.replaceAll("_", "");
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(colName)) {
                return field;
            }
        }
        return null;
    }

    private Field[] getClassAndSuperFields(Class c)
    {
        if (c == null) {
            return null;
        }
        List l = new ArrayList();
        Field[] selfFields = c.getDeclaredFields();
        for (Field field : selfFields) {
            l.add(field);
        }
        while (c.getSuperclass() != null) {
            Class superClass = c.getSuperclass();
            selfFields = superClass.getDeclaredFields();
            for (Field field : selfFields) {
                l.add(field);
            }
            c = superClass;
        }
        Field[] fields = new Field[l.size()];
        for (int i = 0; i < l.size(); i++) {
            fields[i] = ((Field)l.get(i));
        }
        return fields;
    }

    public List transformList(List collection)
    {
        return collection;
    }

    public static void setter(Object obj, String attr, Object value, Class<?> type)
    {
        String methodName = "set" + attr.substring(0, 1).toUpperCase() +
                attr.substring(1);
        if ((value instanceof Character))
            value = value.toString();
        try
        {
            Method method = obj.getClass().getMethod(methodName, new Class[] { type });
            method.invoke(obj, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}