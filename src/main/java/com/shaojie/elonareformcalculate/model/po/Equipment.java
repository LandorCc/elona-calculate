package com.shaojie.elonareformcalculate.model.po;

import com.shaojie.elonareformcalculate.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author waaaaagh
 * @since 2021/3/19 15:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    private String id = StringUtils.getUUID();

    private Integer usedFlag = 0;

    /**
     * 是否虚空装备:0否,1是
     */
    private Integer type;
    /**
     * 装备名称
     */
    private String name;
    /**
     * 魔免词条
     */
    private BigDecimal moMian = BigDecimal.ZERO;
    /**
     * 追伤词条
     */
    private BigDecimal zhuiShang = BigDecimal.ZERO;
    /**
     * 整数型蓝色词条
     */
    private Integer blue = 0;
}


