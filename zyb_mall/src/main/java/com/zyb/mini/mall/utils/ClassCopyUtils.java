package com.zyb.mini.mall.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tanxin
 * @date 2019/4/23
 * @desc
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassCopyUtils {
    /**
     * 取得null属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * A(src) 复制到 B(target)
     * 复制属性时跳过null
     *
     * @param src    被copy的对象
     * @param target copy后的对象
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }


    /**
     * A(src) 复制到 B(target)
     * 复制属性时不跳过null
     *
     * @param src    被copy的对象
     * @param target copy后的对象
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }
}
