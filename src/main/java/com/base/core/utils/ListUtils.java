package com.base.core.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 集合工具
 * @author YiMing on 2017/3/18.
 */
public class ListUtils {

    /**
     * 将List集合转成Map
     * @param collection 待转集合
     * @param keyPropertyName 集合中属性名称
     * @return
     */
    public static Map<String, ?> extractToMap(Collection<?> collection, String keyPropertyName) {
        return extractToMap(collection, keyPropertyName, null);
    }

    public static Map<String, ?> extractToMap(Collection<?> collection, String keyPropertyName, String valuePropertyName) {
        HashMap map = new HashMap(collection.size());
        try {
            Iterator e = collection.iterator();
            while(e.hasNext()) {
                Object obj = e.next();
                Object key = PropertyUtils.getProperty(obj, keyPropertyName);
                if(StringUtils.isNotBlank(valuePropertyName)) {
                    map.put(String.valueOf(key), obj);
                }
                map.put(key, PropertyUtils.getProperty(obj, valuePropertyName));
            }

            return map;
        } catch (Exception var6) {
            throw Reflections.convertReflectionExceptionToUnchecked(var6);
        }
    }

    public static List<Object> extractToList(Collection<?> collection, String propertyName) {
        ArrayList list = new ArrayList(collection.size());

        try {
            Iterator e = collection.iterator();

            while(e.hasNext()) {
                Object obj = e.next();
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }

            return list;
        } catch (Exception var5) {
            throw Reflections.convertReflectionExceptionToUnchecked(var5);
        }
    }

    public static List<String> extractToStringList(Collection<?> collection, String propertyName) {
        ArrayList list = new ArrayList(collection.size());

        try {
            Iterator e = collection.iterator();

            while(e.hasNext()) {
                Object obj = e.next();
                Object o = PropertyUtils.getProperty(obj, propertyName);
                if(o != null) {
                    list.add(o.toString());
                }
            }

            return list;
        } catch (Exception var6) {
            throw Reflections.convertReflectionExceptionToUnchecked(var6);
        }
    }

    public static Object[] extractToArray(Collection<?> collection, String propertyName) {
        return extractToList(collection, propertyName).toArray();
    }

    public static String[] extractToStringArray(Collection<?> collection, String propertyName) {
        List list = extractToStringList(collection, propertyName);
        String[] array = new String[list.size()];
        return (String[])list.toArray(array);
    }

    public static String extractToString(Collection<?> collection, String propertyName, String separator) {
        List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    public static String convertToString(Collection<?> collection, String separator) {
        return StringUtils.join(collection, separator);
    }

    public static String convertToString(Collection<?> collection, String prefix, String postfix) {
        StringBuilder builder = new StringBuilder();
        Iterator var4 = collection.iterator();

        while(var4.hasNext()) {
            Object o = var4.next();
            builder.append(prefix).append(o).append(postfix);
        }

        return builder.toString();
    }

    public static <T> T getFirst(Collection<T> collection) {
        return isEmpty(collection)?null:collection.iterator().next();
    }

    public static <T> Object getLast(Collection<T> collection) {
        if(isEmpty(collection)) {
            return null;
        } else if(collection instanceof List) {
            List iterator1 = (List)collection;
            return iterator1.get(iterator1.size() - 1);
        } else {
            Iterator iterator = collection.iterator();

            Object current;
            do {
                current = iterator.next();
            } while(iterator.hasNext());

            return current;
        }
    }

    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        ArrayList result = new ArrayList(a);
        result.addAll(b);
        return result;
    }

    public static <T> List<T> subtract(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList(a);
        Iterator var3 = b.iterator();

        while(var3.hasNext()) {
            Object element = var3.next();
            list.remove(element);
        }

        return list;
    }

    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList();
        Iterator var3 = a.iterator();

        while(var3.hasNext()) {
            Object element = var3.next();
            if(b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public static <T> List<T> merge(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList(a);
        Iterator var3 = b.iterator();

        while(var3.hasNext()) {
            Object element = var3.next();
            if(!a.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static boolean isSame(List one, List two) {
        if (one == null && two == null) {
            return true;
        } else if (one != null && two != null) {
            if (one.size() != two.size()) {
                return false;
            } else {
                for (int i=0; i<one.size(); i++) {
                    if (!one.get(i).equals(two.get(i))) return false;
                }
                return true;
            }
        } else {
            return false;
        }
    }
}

