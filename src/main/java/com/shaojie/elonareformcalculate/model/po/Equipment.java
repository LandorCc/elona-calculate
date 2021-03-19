package com.shaojie.elonareformcalculate.model.po;

import com.shaojie.elonareformcalculate.utils.StringUtils;
import lombok.Data;

@Data
public class Equipment implements Comparable<Equipment> {


    private String id = StringUtils.getUUID();

    private int usedFlag = 0;

    private String name;
    /**
     * 魔免词条
     */
    private double momian;
    /**
     * 追伤词条
     */
    private double zhuishang;

    /**
     * 整数型蓝色词条
     */
    private int blue;
//    public boolean equals(Object o){
//
//        System.out.println("compare....");
//
//        if(o.getClass()!=getClass())
//            return false;
//        else{
//            Equipment a=(Equipment) o;
//            if(id.equals(a.id))
//                return true;
//            else
//                return false;
//        }
//    }
//
//    public int hashCode() {
//        return id.hashCode();
//    }


    @Override
    public int compareTo(Equipment o) {
        return 0;
    }

    public Equipment(double momian) {
        this.momian = momian;
    }

    public Equipment(String name,int blue) {
        this.name = name;
        this.blue = blue;
    }

    public Equipment(String name,double momian) {
        this.name = name;
        this.momian = momian;
    }
    public Equipment() {
    }
}

