package com.shaojie.elonareformcalculate.service;

import com.shaojie.elonareformcalculate.model.po.Equipment;

import java.util.List;

public interface IReformService {
    List<List<Equipment>> calculateMomian(List<Equipment> rawEquipment, int reformNum,int sonDeep,int dadyDeep);


    List<Equipment> calculateBlue(List<Equipment> equipmentList, double threshold,int deep);

    List<List<Equipment>> calculateMomian2(List<Equipment> rawEquipment, int reformNum, int sonDeep, int dadyDeep);
}
