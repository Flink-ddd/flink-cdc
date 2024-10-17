package com.panda.cdc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanUtils
 * @author muxh
 * @desc 对象实体转化工具
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <E, T> T convertBean(E source, Class<T> targetClass) {
        try {
            T t = targetClass.newInstance();
            copyProperties(source, t);
            return t;
        } catch (ReflectiveOperationException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    public static <E, T> List<T> convertList(List<E> source, Class<T> elementTargetClass) {
        if (source == null) {
            return null;
        }
        try {
            List<T> ls = new ArrayList<>();
            for (E s : source) {
                T t = elementTargetClass.newInstance();
                copyProperties(s, t);
                ls.add(t);
            }
            return ls;
        } catch (ReflectiveOperationException e) {
            throw new ClassCastException(e.getMessage());
        }
    }
}
