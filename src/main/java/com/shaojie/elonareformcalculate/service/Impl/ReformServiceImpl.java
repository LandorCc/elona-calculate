package com.shaojie.elonareformcalculate.service.Impl;

import com.shaojie.elonareformcalculate.model.po.Equipment;
import com.shaojie.elonareformcalculate.service.IReformService;
import com.shaojie.elonareformcalculate.utils.BeanUtils;
import com.shaojie.elonareformcalculate.utils.LinkedList;
import com.shaojie.elonareformcalculate.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReformServiceImpl implements IReformService {
    /**
     * 处理逻辑：
     * 1、得到dadys,该dadys是符合小于19.5的最佳对象，dadys可能包含多个最佳对象（仅当最佳对象值相等才会出现这种情况），保留这些最佳对象。
     * 2、从readyEquipment中删除dadys所涉及到的所有装备。
     * 3、根据readyEquipment得到sonss，sonss不限大小，是越大越好。同样，sonss中可能同dadys一样有多个最佳对象，但只需要一个，所以得到改造材料最少的最佳对象。
     * 4、将 sonss 和 dadys 进行改造，dadys是本体，sonss是材料垫子。
     * 5、重复步骤1->2->4，直到改造次数达到上限或者本次重复得到的值小于或等于上次重复得到的值，该值就是最终结果。
     * 注：
     *
     * @param rawEquipment 原始装备集合，不会有任何改变
     * @param reformNum    改造次数
     * @param sonDeep      得到sonss所进行的改造次数
     * @param dadyDeep     得到dadys所进行的改造次数
     * @return 装备最终集合，集合顺序代表着改造顺序
     */
    @Override
    public List<List<Equipment>> calculateMomian(List<Equipment> rawEquipment, int reformNum, int sonDeep, int dadyDeep) {
        List<Equipment> readyEquipment = new ArrayList<>();
        rawEquipment.forEach(entity -> readyEquipment.add(BeanUtils.copy(entity, new Equipment())));
        List<Equipment> sonFinallyEquipment = new ArrayList<>();
        List<List<Equipment>> dadyFinallyEquipment = new ArrayList<>();
        List<List<Equipment>> sonss;
        List<List<List<Equipment>>> allDadys = new ArrayList<>();
        Equipment son;
        while (!StringUtils.isEmpty(readyEquipment)) {
            //正常来说，dadys中只有一条List,但有可能有多个，故用for循环遍历操作
            List<List<Equipment>> dadys = getDadys(rawEquipment, readyEquipment, dadyDeep);
            allDadys.add(dadys);
            //删除readyEquipment中的dady涉及到的设备
            for (List<Equipment> dady : dadys) {
                for (Equipment entity : dady) {
                    String name = entity.getName();
                    Equipment deleteFlag = readyEquipment.stream().filter(enetity -> enetity.getName().equals(name)).collect(Collectors.toList()).get(0);
                    readyEquipment.remove(deleteFlag);
                }
            }

        }
        if (readyEquipment.size() < 2) {
            for (List<Equipment> equipmentList : allDadys.get(allDadys.size() - 1)) {
                for (Equipment equipment : equipmentList) {
                    Equipment addFlag = rawEquipment.stream().filter(entity -> entity.getName().equals(equipment.getName())).collect(Collectors.toList()).get(0);
                    readyEquipment.add(addFlag);
                }
            }
            allDadys.remove(allDadys.get(allDadys.size()-1));
        }
        //sons只需要一个,所以得到涉及到设备最少的sons
        sonss = getSons(rawEquipment, readyEquipment, sonDeep);
        List<Equipment> sons = new ArrayList<>();
        int index2 = 99;
        for (List<Equipment> list : sonss) {
            if (list.size() < index2) {
                sons = list;
                index2 = list.size();
            }
        }
        //删除readyEquipment中的son涉及到的设备
        for (Equipment entity : sons) {
            String name = entity.getName();
            Equipment deleteFlag = readyEquipment.stream().filter(enetity -> enetity.getName().equals(name)).collect(Collectors.toList()).get(0);
            readyEquipment.remove(deleteFlag);
            sonFinallyEquipment.add(deleteFlag);
        }

        son = sons.get(0);
        double theLast = 0;
        for (int i = allDadys.size() - 1; i >= 0; i--) {
            List<Equipment> servant = new ArrayList<>();
            reformNum--;
            List<List<Equipment>> dadys = allDadys.get(i);
            for (List<Equipment> family : dadys) {
                Equipment dady = family.get(0);
                son = cook(dady, son);
                if (theLast < dady.getMomian() || reformNum < 0) {
                    theLast = dady.getMomian();
                } else {
                    break;
                }
                //每运算一次增加所有涉及到的设备到最终显示
                servant.addAll(family);
            }
            List<Equipment> bb = new ArrayList<>();
            for (Equipment equipment : servant) {
                Equipment aa = rawEquipment.stream().filter(entity -> entity.getName().equals(equipment.getName())).collect(Collectors.toList()).get(0);
                bb.add(aa);
            }
            dadyFinallyEquipment.add(bb);
        }

        System.out.println("—————————————————son———————————————————————————");
        sonFinallyEquipment.forEach(System.out::println);
        System.out.println("—————————————————dady———————————————————————————");
        for (List<Equipment> list : dadyFinallyEquipment) {
            System.out.println("------");
            for (Equipment equipment : list) {
                System.out.println(equipment);
            }
        }
        System.out.println(theLast);
        List<Equipment> river = new ArrayList<>();
        river.add(new Equipment("-----", 0.0));
        dadyFinallyEquipment.add(river);
        dadyFinallyEquipment.add(sonFinallyEquipment);
        dadyFinallyEquipment.get(0).get(0).setMomian(theLast);
        return dadyFinallyEquipment;
    }


    @Override
    public List<Equipment> calculateBlue(List<Equipment> equipmentList, double threshold, int deep) {
        List<Equipment> rawEquipment = new ArrayList<>();
        rawEquipment.add(new Equipment("A", 6));
        rawEquipment.add(new Equipment("B", 4));
        rawEquipment.add(new Equipment("C", 8));
        rawEquipment.add(new Equipment("D", 10));
        rawEquipment.add(new Equipment("E", 4));
        rawEquipment.add(new Equipment("F", 11));
        rawEquipment.add(new Equipment("G", 10));
        rawEquipment.add(new Equipment("H", 1));
        rawEquipment.add(new Equipment("I", 9));
//        rawEquipment.add(new Equipment("M",10));
//        rawEquipment.add(new Equipment("N",2));

//
//        rawEquipment.add(new Equipment("A",9));
//        rawEquipment.add(new Equipment("B",6));
//        rawEquipment.add(new Equipment("C",8));
//        rawEquipment.add(new Equipment("D",8));
//        rawEquipment.add(new Equipment("E",11));
//        rawEquipment.add(new Equipment("F",10));
//
//        List<Equipment> readyEquipment = new ArrayList<>();
//        rawEquipment.forEach(entity -> readyEquipment.add(BeanUtils.copy(entity,new Equipment())));
//        List<Equipment> mainnFinallyEquipment = new ArrayList<>();
//        List<Equipment> otherFinallyEquipment = new ArrayList<>();
//
//
//        List<List<Equipment>> son = getSons(rawEquipment,readyEquipment,deep);
//
//        List<List<Equipment>> dadys = getDadys(rawEquipment,readyEquipment,deep);
//        List<Equipment> servant = new ArrayList<>();
//        for (List<Equipment> family : dadys) {
//            Equipment dady = family.get(0);
//            cook(dady,son);
//            servant.addAll(family);
//        }
//        for (Equipment equipment : servant) {
//            otherFinallyEquipment.addAll(rawEquipment.stream().filter(entity -> entity.getName().equals(equipment.getName())).collect(Collectors.toList()));
//        }


        return null;
    }

    @Override
    public List<List<Equipment>> calculateMomian2(List<Equipment> rawEquipment, int reformNum, int sonDeep, int dadyDeep) {
        List<Equipment> readyEquipment = new ArrayList<>();
        rawEquipment.forEach(entity -> readyEquipment.add(BeanUtils.copy(entity, new Equipment())));
        List<List<Equipment>> dadys = getDadys(rawEquipment, readyEquipment, dadyDeep);
        return dadys;
    }


    /**
     * 得到最佳dadys,且含有材料。以蓝色整数为例，最佳dady是12，readyEquipment产生的组合中，有可能有组合是多个12，有组合只有一个12，该方法中调用bestDady，
     * 以便得到有最多12的组合
     */
    public List<List<Equipment>> getDadys(List<Equipment> rawEquipment, List<Equipment> readyEquipment, int deep) {
        //对每个方案的结果与其他对象再进行方案穷举，每穷举一次 deep--
        List<Map.Entry<Equipment, Equipment>> dog = new ArrayList<>();

        //将list集合中元素穷举，每穷举一次消耗一个deep，每穷举一次后，对得到的方案集合元素逐个提取，将提取的方案元素放入list中并删除list中涉及到方案元素的元素，将方案元素放入最终方案集合dog中。之后再次消耗deep，进行重复工作
        if (deep == 1) {
            for (Equipment equipment : readyEquipment) {
                Map.Entry<Equipment,Equipment> entryMap = new AbstractMap.SimpleEntry<>(equipment, null);
                dog.add(entryMap);
            }
        } else {
            dog = iteration(readyEquipment, deep);
        }
        return myBestDady(rawEquipment, dog);
    }

    /**
     * 得到dog中最佳方案
     */
    public List<List<Equipment>> myBestDady(List<Equipment> rawEquipment, List<Map.Entry<Equipment, Equipment>> dog) {

        //得到最佳dady，如果dog中有复数最佳dady，则找出最多最佳dady的dady组合
        List<Equipment> bestDadyList = new ArrayList<>();
        int blue = 0;
        double momian = 0;
        if (rawEquipment.get(0).getBlue() != 0) {
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {

                if (equipmentMap2.getKey().getBlue() < 13 && equipmentMap2.getKey().getBlue() > blue) {
                    blue = equipmentMap2.getKey().getBlue();
                }
            }
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {
                if (equipmentMap2.getKey().getBlue() == blue) {
                    bestDadyList.add(equipmentMap2.getKey());
                }
            }
        } else if (rawEquipment.get(0).getMomian() != 0) {
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {

                if (equipmentMap2.getKey().getMomian() < 19.50 && equipmentMap2.getKey().getMomian() > momian) {
                    momian = equipmentMap2.getKey().getMomian();
                }
            }
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {
                if (equipmentMap2.getKey().getMomian() == momian) {
                    bestDadyList.add(equipmentMap2.getKey());
                }
            }
        }

        return getBest(bestDadyList, dog);
    }

//    public static Equipment getSon(List<Equipment> rawEquipment,List<Equipment> readyEquipment,List<Equipment> finallyEquipmentList,int deep) {
//        List<Equipment> finallylist = new ArrayList<>();
//        List<Map.Entry<Equipment,Equipment>> dog = new ArrayList<>();
//        //将list集合中元素穷举，每穷举一次消耗一个deep，每穷举一次后，对得到的方案集合元素逐个提取，将提取的方案元素放入list中并删除list中涉及到方案元素的元素，将方案元素放入最终方案集合dog中。之后再次消耗deep，进行重复工作
//        iteration(readyEquipment,dog,rawEquipment,finallylist,deep);
//        int index = 0;
//        double index2 = 0;
//        Equipment equipment = null;
//        Equipment equipment2 = null;
//        for (Map.Entry<Equipment, Equipment> equipmentEntry : dog) {
//            if (rawEquipment.get(0).getBlue() != 0) {
//                if (equipmentEntry.getKey().getBlue()/2 > index/2 || equipmentEntry.getKey().getBlue()%2 == 0) {
//                    if (equipmentEntry.getKey().getBlue()/2 > index/2) {
//                        index = equipmentEntry.getKey().getBlue();
//                        equipment = equipmentEntry.getKey();
//                        equipment2 = equipmentEntry.getValue();
//
//                    }
//                    if (equipmentEntry.getKey().getBlue()%2 ==0 && equipmentEntry.getKey().getBlue()/2 >= index/2) {
//                        index = equipmentEntry.getKey().getBlue();
//                        equipment = equipmentEntry.getKey();
//                        equipment2 = equipmentEntry.getValue();
//                    }
//                }
//            } else if (rawEquipment.get(0).getMomian() != 0) {
//                if (equipmentEntry.getKey().getMomian() > index2) {
//                    equipment = equipmentEntry.getKey();
//                    index2 = equipment.getMomian();
//                    equipment2 = equipmentEntry.getValue();
//                }
//            }
//        }
//        assert equipment2 != null;
//        String name2 = equipment2.getName();
//        Equipment deleteFlag2 = readyEquipment.stream().filter(enetity -> enetity.getName().equals(name2)).collect(Collectors.toList()).get(0);
//        readyEquipment.remove(deleteFlag2);
//
//        String name = equipment.getName();
//        Equipment deleteFlag = readyEquipment.stream().filter(enetity -> enetity.getName().equals(name)).collect(Collectors.toList()).get(0);
//        readyEquipment.remove(deleteFlag);
//
//        finallyEquipmentList.add(rawEquipment.stream().filter(entity -> entity.getName().equals(name)).collect(Collectors.toList()).get(0));
//        finallyEquipmentList.add(rawEquipment.stream().filter(entity -> entity.getName().equals(name2)).collect(Collectors.toList()).get(0));
//
//        return equipment;
//    }

    public List<List<Equipment>> getSons(List<Equipment> rawEquipment, List<Equipment> readyEquipment, int deep) {
        List<Equipment> finallylist = new ArrayList<>();
        //将list集合中元素穷举，每穷举一次消耗一个deep，每穷举一次后，对得到的方案集合元素逐个提取，将提取的方案元素放入list中并删除list中涉及到方案元素的元素，将方案元素放入最终方案集合dog中。之后再次消耗deep，进行重复工作
        List<Map.Entry<Equipment, Equipment>> dog = iteration(readyEquipment, deep);
        return myBestSon(rawEquipment, dog);
    }

    public List<List<Equipment>> myBestSon(List<Equipment> rawEquipment, List<Map.Entry<Equipment, Equipment>> dog) {

        //得到最佳son，如果dog中有复数最佳son，则找出最多最佳son的son组合
        List<Equipment> bestSonList = new ArrayList<>();
        double index = 0;
        if (rawEquipment.get(0).getBlue() != 0) {
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {

                if (equipmentMap2.getKey().getBlue() > index) {
                    index = equipmentMap2.getKey().getBlue();
                }
            }
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {
                if (equipmentMap2.getKey().getBlue() == index) {
                    bestSonList.add(equipmentMap2.getKey());
                }
            }
        } else if (rawEquipment.get(0).getMomian() != 0) {
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {

                if (equipmentMap2.getKey().getMomian() > index) {
                    index = equipmentMap2.getKey().getMomian();
                }
            }
            for (Map.Entry<Equipment, Equipment> equipmentMap2 : dog) {
                if (equipmentMap2.getKey().getMomian() == index) {
                    bestSonList.add(equipmentMap2.getKey());
                }
            }
        }

        return getBest(bestSonList, dog);
    }


    /**
     * 得到每个最佳设备所涉及到的材料设备并放入集合，这些集合再放入最佳组合集合，处理最佳组合集合，得到所有涉及的设备不重复的最佳组合
     *
     * @param bestList 最佳设备集合
     * @param dog      所有方案集合
     * @return 所有涉及的设备不重复的最佳组合
     */
    public List<List<Equipment>> getBest(List<Equipment> bestList, List<Map.Entry<Equipment, Equipment>> dog) {
        //提取出每个最佳所涉及到的equipment，每个最佳涉及的equipment单独成一个集合
        List<List<Equipment>> element = new ArrayList<>();
        for (Equipment entity : bestList) {
            List<Equipment> eList = new ArrayList<>();
            eList.add(entity);
            Equipment e = BeanUtils.copy(entity, new Equipment());
            boolean flag = true;
            //有个bug,当有如下数据，12，8，6时，并设最佳dady是 12，最佳dady设涉及的材料是8，但 6+12/12=12,此时，循环会把6也当成材料
            do {
                for (Map.Entry<Equipment, Equipment> equipmentEntry : dog) {
                    if (equipmentEntry.getKey().equals(e)) {
                        eList.add(equipmentEntry.getValue());
                        e = equipmentEntry.getValue();
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
            } while (flag);
            element.add(eList);
        }
        //处理element,element是待处理的最佳组合
        List<List<List<Equipment>>> grandeFather = new ArrayList<>();
        List<Equipment> rmn = new ArrayList<>();
        rmn.add(null);
        //放入dadys的基准，每个index基准必放入dadys中
        for (List<Equipment> index : element) {
            List<List<Equipment>> es = new ArrayList<>();
            es.add(index);
            //检索element中的每个集合
            for (List<Equipment> list : element) {
                List<Equipment> intersection = null;
                list.removeAll(rmn);
                //检索dadys中的每个集合，如果与element当前集合无交集，则将element当前集合的第一个元素放入dadys的一个集合中
                for (List<Equipment> e : es) {
                    if (e != null) {
                        e.removeAll(rmn);
                        intersection = e.stream()
                                .filter(item -> !StringUtils.isEmpty(list.stream().filter(item2 -> item2.getName().equals(item.getName())).collect(Collectors.toList())))
                                .collect(Collectors.toList());
                    } else {
                        continue;
                    }

                    if (!StringUtils.isEmpty(intersection)) {
                        break;
                    }
                }
                if (StringUtils.isEmpty(intersection)) {
                    es.add(list);
                }
            }
            grandeFather.add(es);
        }

        int index2 = 0;
        List<List<Equipment>> ee = null;
        for (List<List<Equipment>> ff : grandeFather) {
            if (ff.size() > index2) {
                index2 = ff.size();
                ee = ff;
            }
        }
        return ee;
    }

    /**
     * 对equipmentMap中的的方案元素逐个提取，方案元素放入list中，并删除list中涉及到方案元素的元素，再将方案元素加入最终方案元素集合dog中
     *
     * @param readyEquipment
     * @param deep
     */
    public List<Map.Entry<Equipment, Equipment>> iteration(List<Equipment> readyEquipment, int deep) {

        List<Map.Entry<Equipment, Equipment>> dog = new ArrayList<>();
        Map<Equipment, Equipment> equipmentMap = exhaustivity(readyEquipment);
        for (Equipment equipment : readyEquipment) {
            equipmentMap.put(equipment, null);
        }
        for (Map.Entry<Equipment, Equipment> entry : equipmentMap.entrySet()) {
            //放入所有方案集合中,放入前判断dog中是否保存了相同key的map，如果有，则将待保存key的usedFlag加1
            for (Map.Entry<Equipment, Equipment> equipmentEntry : dog) {
                if (equipmentEntry.getKey().equals(entry.getKey())) {
                    entry.getKey().setUsedFlag(entry.getKey().getUsedFlag() + 1);
                }
            }
            dog.add(entry);


        }
        deep--;
        if (deep < 1) {
            return dog;
        }
        for (Map.Entry<Equipment, Equipment> entity : equipmentMap.entrySet()) {
            List<Equipment> list = new ArrayList<>(readyEquipment);
            if (entity.getKey().getBlue() != 0) {
                if (entity.getKey().getBlue() < 13) {
                    readyIterration(entity, equipmentMap, list, dog);
                    //递归
                    iteration(list, deep);
                }
            } else if (entity.getKey().getMomian() != 0) {
                if (entity.getKey().getMomian() < 19.50) {
                    readyIterration(entity, equipmentMap, list, dog);
                    //递归
                    iteration(list, deep);
                }
            }
        }
        return dog;
    }

    public void readyIterration(Map.Entry<Equipment, Equipment> entity, Map<Equipment, Equipment> equipmentMap, List<Equipment> list, List<Map.Entry<Equipment, Equipment>> dog) {
        Equipment key = entity.getKey();
        Map.Entry<Equipment, Equipment> entry = new AbstractMap.SimpleEntry<>(key, equipmentMap.get(key));
//        //删除list中得到key的材料
//        cleanList(list,key,equipmentMap);
        //转化为链表集合
        List<LinkedList<Equipment>> linkedLists = new ArrayList<>();
        for (Map.Entry<Equipment, Equipment> entry2 : equipmentMap.entrySet()) {
            LinkedList<Equipment> linkedList = new LinkedList<>();
            Equipment value2 = entry2.getValue();
            while (true) {
                int i = 0;
                for (Map.Entry<Equipment, Equipment> entry3 : equipmentMap.entrySet()) {
                    if (entry3.getKey().equals(value2)) {
                        linkedList.add(entry3.getKey());
                        value2 = entry3.getValue();
                        i++;
                        break;
                    }
                }
                if (i == 0) {
                    break;
                }
            }
            linkedLists.add(linkedList);
        }
        //删除涉及到key的所有元素
        for (LinkedList<Equipment> linkedList : linkedLists) {
            if (StringUtils.isEmpty(linkedList.show().stream().filter(entity2 -> entity2.getName().equals(key.getName())).collect(Collectors.toList()))) {
                for (Equipment equipment : linkedList.show()) {
                    list.remove(equipment);
                }
            }
        }

        //将方案再次放入list中，准备进行下次穷举成map
        list.add(key);
        //放入所有方案集合中,放入前判断dog中是否保存了相同名称key的map，如果有，则将待保存key的usedFlag加1
        for (Map.Entry<Equipment, Equipment> equipmentEntry : dog) {
            if (equipmentEntry.getKey().getName().equals(entry.getKey().getName())) {
                entry.getKey().setUsedFlag(entry.getKey().getUsedFlag() + 1);
            }
        }
        dog.add(entry);

    }

    /**
     * 删除readyEquipment中的被去除对象和得到该对象所消耗的所有材料
     *
     * @param readyEquipment 操作对象集合
     * @param son            被去除对象
     * @param equipmentMap   组合方案
     */
    public void cleanList(List<Equipment> readyEquipment, Equipment son, Map<Equipment, Equipment> equipmentMap) {
        Equipment key = son;
        Equipment value;
        String name = key.getName();
        Equipment deleteFlag = readyEquipment.stream().filter(entity -> entity.getName().equals(name)).collect(Collectors.toList()).get(0);
        readyEquipment.remove(deleteFlag);

        boolean flag = true;
        while (equipmentMap.containsKey(key)) {

            value = equipmentMap.get(key);
            String valueName = value.getName();
            Equipment valueDeleteFlag = readyEquipment.stream().filter(entity -> entity.getName().equals(valueName)).collect(Collectors.toList()).get(0);
            readyEquipment.remove(valueDeleteFlag);
            key = value;

        }

    }


    public Equipment cook(Equipment dady, Equipment son) {
        if (dady.getBlue() != 0) {
            dady.setBlue(dady.getBlue() + son.getBlue() / 2);
        } else if (dady.getMomian() != 0) {
            double result = dady.getMomian() + son.getMomian() / 2;
            BigDecimal bigDecimal = new BigDecimal(result);
            result = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            dady.setMomian(result);
        }
        return dady;
    }

    /**
     * 穷举出所有搭配,并保存在Map中，key保存的是主体，value保存垫子
     *
     * @param equipmentList
     * @return 结果map
     */
    public Map<Equipment, Equipment> exhaustivity(List<Equipment> equipmentList) {
        Map<Equipment, Equipment> allResult = new LinkedHashMap<>();
        if (equipmentList.get(0).getBlue() != 0) {
            if (equipmentList.size() < 2) {
                return new HashMap<>();
            }
            equipmentList.forEach(entity -> {
                if (entity.getBlue() < 13) {
                    for (Equipment equipment : equipmentList) {
                        if (entity.equals(equipment)) {
                            continue;
                        }
                        Equipment entity2 = BeanUtils.copy(entity, new Equipment()); //主体
                        //equipmentList1.get(i),equipment1.getMomian()
                        entity2.setBlue(entity2.getBlue() + equipment.getBlue() / 2);

                        for (Map.Entry<Equipment, Equipment> map : allResult.entrySet()) {
                            if (map.getKey().getName().equals(entity2.getName())) {
                                entity2.setUsedFlag(entity2.getUsedFlag() + 1);
                            }
                        }

                        allResult.put(entity2, equipment);
                    }
                }
            });
        } else if (equipmentList.get(0).getMomian() != 0) {
            if (equipmentList.size() < 2) {
                return new HashMap<>();
            }
            equipmentList.forEach(entity -> {
                if (entity.getMomian() < 19.50) {
                    for (Equipment equipment : equipmentList) {
                        if (entity.equals(equipment)) {
                            continue;
                        }
                        Equipment entity2 = BeanUtils.copy(entity, new Equipment()); //主体


                        int result = (int) (entity2.getMomian() * 100) + (int) (equipment.getMomian() * 100) / 2;
                        double b = (double) result / 100;
                        entity2.setMomian(b);

                        for (Map.Entry<Equipment, Equipment> map : allResult.entrySet()) {
                            if (map.getKey().equals(entity2)) {
                                entity2.setUsedFlag(entity2.getUsedFlag() + 1);
                            }
                        }

                        allResult.put(entity2, equipment);
                    }
                }
            });
        }

        return allResult;

    }
}
