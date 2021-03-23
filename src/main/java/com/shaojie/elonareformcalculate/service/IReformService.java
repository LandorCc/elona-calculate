package com.shaojie.elonareformcalculate.service;

import com.shaojie.elonareformcalculate.model.po.Equipment;

public interface IReformService {

    /**
     * 计算魔免/物免词条
     * @param target 被改造装备
     * @param source 被消耗的材料
     * @return 改造后的装备
     */
   Equipment calculateMomian(Equipment target,Equipment source);

    /**
     * 判断该装备待改造词条是否达到上限
     * @param target 目标装备
     * @param type 是否虚空装备, 0否,1是
     * @param propertyCode 待改造词条标识code
     * @return true达到上限不能再次改造, false未达到上限
     */
   boolean isMax(Equipment target,Integer type,Integer propertyCode);
}
