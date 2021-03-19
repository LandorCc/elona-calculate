package com.shaojie.elonareformcalculate.utils;

import cn.hutool.core.bean.BeanUtil;


/**
 * describe: 对象属性拷贝
 *
 * @author xiezhongyong
 * @date 2019/02/27
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 对象copy返回
     * @param source
     * @param target
     * @return
     */
    public static <T> T copy(Object source, T target) {
        BeanUtil.copyProperties(source, target);
        return target;
    }


}