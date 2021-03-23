package com.shaojie.elonareformcalculate.enums;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author waaaaagh
 * @since 2021/3/19 16:20
 * <p>
 * 属性枚举
 */
public enum PropertyEnum {

    /**
     * 魔免/物免,普通装备
     */
    MO_MIAN(0, "魔免/物免",new BigDecimal("19.50")),
    /**
     * 魔免/物免,虚空装备
     */
    MO_MIAN_PRO(0, "魔免/物免",new BigDecimal("22.50")),

    /**
     * 追伤,普通装备
     */
    ZHUI_SHANG(1, "追伤",new BigDecimal("68")),
    /**
     * 追伤,虚空装备
     */
    ZHUI_SHANG_PRO(1, "追伤",new BigDecimal("80"));

    @Getter
    private final Integer code;
    @Getter
    private final String name;
    @Getter
    private final BigDecimal maxValue;

    PropertyEnum(Integer code, String name ,BigDecimal maxValue) {
        this.code = code;
        this.name = name;
        this.maxValue = maxValue;
    }

    public static PropertyEnum getByCode(Integer code) {
        for (PropertyEnum propertyEnum : values()) {
            if (propertyEnum.getCode().equals(code)) {
                return propertyEnum;
            }
        }
        return null;
    }
}
