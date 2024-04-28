package com.base.core.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对象属性值比较
 * Created by 40338 on 2016/12/27.
 */
public class BeanUtil {
    /**
     * 获取属性不一致的值
     * @param oldBeanValue 历史数据
     * @param nowBeanValue 当前数据
     * @return different 不一样的属性及新老数据
     */
    public static List<String> getDiffValue(Object oldBeanValue, Object nowBeanValue) {
        if (oldBeanValue instanceof Collection) {
            return Collections.EMPTY_LIST;
        }
        List<String> different = Lists.newArrayList();
        Class<?> srcClass = oldBeanValue.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            String oldValue = getClassValue(oldBeanValue, nameKey) == null ? "无数据" : getClassValue(oldBeanValue, nameKey)
                    .toString();
            String nowValue = getClassValue(nowBeanValue, nameKey) == null ? "无数据" : getClassValue(nowBeanValue, nameKey)
                    .toString();
            if(!StringUtils.equals(oldValue, nowValue)) {
                different.add("{" + nameKey + ":{'old:'" + oldValue + "','new':'" + nowValue + "'}}");
            }
        }
        return different;
    }

    /**
     * 根据字段名称取值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID")
                        && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                        .equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取目标类属性
     * @param bean 目标类
     * @return properties bean的属性
     */
    public static Map<String, String> getProperty(Class bean, Map<String, String> properMain) {
        Map<String, String> properties = Maps.newHashMap();
        Field[] fields = bean.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if(properMain.containsKey(name)) {
                properties.put(name, properMain.get(name));
            }

        }
        return properties;
    }

    public static <T>T requestToBean(HttpServletRequest request, Class<T> beanClass) {
        try {
            T bean = beanClass.newInstance();
            Enumeration e = request.getParameterNames();
            while(e.hasMoreElements()){
                String name = (String)e.nextElement();
                String value = request.getParameter(name);
                if(StringUtils.isEmpty(value)) {
                    continue;
                }
                Field field = null;
                try {
                    field = beanClass.getDeclaredField(name);
                } catch (Exception e1) {
                    continue;
                }
                // String
                if(field.getGenericType().toString().equals("class java.lang.String")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{String.valueOf(value)});
                }

                // 如果类型是Integer
                if(field.getGenericType().toString().equals("class java.lang.Integer")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{Integer.valueOf(value)});
                }

                // 如果类型是Integer
                if(field.getGenericType().toString().equals("class java.lang.Long")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{Long.valueOf(value)});
                }

                // 如果类型是Double
                if(field.getGenericType().toString().equals("class java.lang.Double")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{Double.valueOf(value)});
                }

                // 如果类型是Boolean 是封装类
                if(field.getGenericType().toString().equals("class java.lang.Boolean")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{Boolean.valueOf(value)});
                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名

                // 如果类型是Date
                if(field.getGenericType().toString().equals("class java.util.Date")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{DateUtil.parase(value, "yyyy-MM-dd")});
                }

                // 如果类型是Short
                if(field.getGenericType().toString().equals("class java.lang.Sort")) {
                    Method m = (Method)beanClass.getMethod("set" + getMethodName(field.getName()), field.getType());
                    m.invoke(bean, new Object[]{Short.valueOf(value)});
                }
            }
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


}
