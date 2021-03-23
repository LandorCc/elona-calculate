package com.shaojie.elonareformcalculate.controller;


import com.shaojie.elonareformcalculate.service.IReformService;
import com.shaojie.elonareformcalculate.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reform")
@Api(tags = {"计算接口"})
public class ReformController {

    @Resource
    private IReformService reformService;

    @PostMapping("/route")
    public String route(HttpServletRequest request) {
        String checkType = request.getParameter("checkType");
        switch (checkType) {
            case "momian" : return "reformMomian";
            case "momian2" : return "reformMomian2";
            case "naixing" : return "#";
            case "fushang" : return "#2";
            default:return "error";
        }

    }


//    @PostMapping("/calculateMomian")
//    public String calculateMomian(HttpServletRequest request, Model model) {
//        int reformNum = Integer.parseInt(request.getParameter("reformNum"));
//        int sonDeep = Integer.parseInt(request.getParameter("sonDeep"));
//        int dadyDeep = Integer.parseInt(request.getParameter("dadyDeep"));
//        List<Equipment> equipmentList = new ArrayList<>();
//        for (int i = 1; i < 99; i++) {
//            String name = request.getParameter("name(" + i + ")");
//            String valueStr = request.getParameter("value(" + i + ")");
//            if (name == null || valueStr == null) {
//                continue;
//            }
//            Equipment equipment = new Equipment();
//            equipment.setName(name);
//            equipment.setMomian(Double.parseDouble(valueStr));
//            equipmentList.add(equipment);
//        }
//        List<List<Equipment>> finallyEquipment = reformService.calculateMomian(equipmentList, reformNum, sonDeep, dadyDeep);
//        List<List<Equipment>> sonEquipment;
//        List<List<Equipment>> dadyEquipment = new ArrayList<>();
//        for (List<Equipment> list : finallyEquipment) {
//            if (!StringUtils.isEmpty(list) && !list.get(0).getName().equals("-----")) {
//                dadyEquipment.add(list);
//            } else {
//                dadyEquipment.add(list);
//                break;
//            }
//        }
//        finallyEquipment.removeAll(dadyEquipment);
//        sonEquipment = finallyEquipment;
//        model.addAttribute("dadyEquipment",dadyEquipment);
//        model.addAttribute("sonEquipment",sonEquipment);
//        return "result";
//    }
//
//    @PostMapping("calculateMomian2")
//    public String calulateMomian2(HttpServletRequest request,Model model) {
//        int reformNum = Integer.parseInt(request.getParameter("reformNum"));
//        int sonDeep = Integer.parseInt(request.getParameter("sonDeep"));
//        int dadyDeep = Integer.parseInt(request.getParameter("dadyDeep"));
//        List<Equipment> equipmentList = new ArrayList<>();
//        for (int i = 1; i < 99; i++) {
//            String name = request.getParameter("name(" + i + ")");
//            String valueStr = request.getParameter("value(" + i + ")");
//            if (name == null || valueStr == null) {
//                continue;
//            }
//            Equipment equipment = new Equipment();
//            equipment.setName(name);
//            equipment.setMomian(Double.parseDouble(valueStr));
//            equipmentList.add(equipment);
//        }
//        List<List<Equipment>> finallyEquipment = reformService.calculateMomian2(equipmentList, reformNum, sonDeep, dadyDeep);
//
//
//
//        return "result";
//    }
//
//    @ApiOperation("计算蓝色")
//    @GetMapping("/calculateBlue")
//    public String calculateBlue(@RequestParam(value = "requipmentList") List<Equipment> equipmentList,
//                                @RequestParam(value = "threshold") double threshold,
//                                @RequestParam(value = "deep") int deep) {
//        this.reformService.calculateBlue(equipmentList, threshold, deep);
//        return null;
//    }


}



