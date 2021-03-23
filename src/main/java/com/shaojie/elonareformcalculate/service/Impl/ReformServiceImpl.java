package com.shaojie.elonareformcalculate.service.Impl;

import com.shaojie.elonareformcalculate.enums.PropertyEnum;
import com.shaojie.elonareformcalculate.model.po.Equipment;
import com.shaojie.elonareformcalculate.service.IReformService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author waaaaagh
 * @since 2021/3/19 16:20
 */
@Service
public class ReformServiceImpl implements IReformService {

    @Override
    public Equipment calculateMomian(Equipment target, Equipment source) {
        target.setMoMian(source.getMoMian().divide(new BigDecimal("2"), 2, BigDecimal.ROUND_DOWN).add(source.getMoMian()));
        return target;
    }

    @Override
    public boolean isMax(Equipment target, Integer type, Integer propertyCode) {
        PropertyEnum propertyEnum = PropertyEnum.getByCode(propertyCode);
        switch (Objects.requireNonNull(propertyEnum)) {
            case MO_MIAN:
            case MO_MIAN_PRO:
                if (target.getMoMian().compareTo(propertyEnum.getMaxValue()) < 0) {
                    return true;
                }
                break;
            case ZHUI_SHANG:
            case ZHUI_SHANG_PRO:
                if (target.getZhuiShang().compareTo(propertyEnum.getMaxValue()) < 0) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
