package com.base.core.utils;

import com.base.core.annotation.IgnoreZeorValueCheck;

import java.lang.reflect.Field;

/**
 * 遍历对象中的所有属性，判断属性是否为默认值，当所有属性都为默认值，则认为该对象为空对象。
 * 判断包括引用类型和基本类型的判断：
 * 引用类型：
 *      如果属性值 == null，认为为空；
 *      如果属性值 != null，则判断该属性是否是BaseEntity及其子类对象，然后调用对象的isEmptyObject()方法
 *      如果属性值 != null && 不是BaseEntity及其子类对象，则认为非空
 *  基本类型：
 *      与基本类型的零值比较(排除boolean类型)，非零值属性认为非空。
 * @author Xu
 */
public class BaseEntity {
    public boolean isEmptyObject() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            boolean isEmpty = true;
            //忽略类似this$0的变量
            if (field.getName().contains("$")) continue;
            
            // 访问私有变量
            field.setAccessible(true);
            // 变量类型
            Class<?> type = field.getType();

            // 字段上标注了@IgnoreZeorValueCheck注解的属性跳过
            IgnoreZeorValueCheck annotation = field.getAnnotation(IgnoreZeorValueCheck.class);
            if (annotation != null) continue;
            
            // 引用类型变量
            if (Object.class.isAssignableFrom(type)) {
                Object o = null;
                try {
                    o = field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                
                if (o == null) {
                    isEmpty = true;
                } else if (BaseEntity.class.isAssignableFrom(type)) {
                    isEmpty = ((BaseEntity) o).isEmptyObject() && isEmpty;
                    if (!isEmpty) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            
            // 基本类型变量
            try {
                switch (type.getName()) {
                    case "int":
                        int v = field.getInt(this);
                        if (v != 0) return false;
                        break;
                    case "double":
                        double d = field.getDouble(this);
                        if (d != 0D) return false;
                        break;
                    case "long":
                        long l = field.getLong(this);
                        if (l != 0L) return false;
                        break;
                    case "short":
                        short s = field.getShort(this);
                        if (s != 0) return false;
                        break;
                    case "byte":
                        byte b = field.getByte(this);
                        if (b != 0) return false;
                        break;
                    case "boolean":
                        break;
                    case "char":
                        char c = field.getChar(this);
                        if (c != 0) return false;
                        break;
                    case "float":
                        float f = field.getFloat(this);
                        if (f != 0F) return false;
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
        }
        
        return true;
    }
}
