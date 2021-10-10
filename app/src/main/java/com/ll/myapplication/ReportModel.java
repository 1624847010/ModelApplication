package com.ll.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ll
 * @CreateTime: 2021/10/09 09:51
 */
public class ReportModel {

    public static void main(String[] args) {
        Map<String,String> a = new HashMap<>();
        a.entrySet();
                a.keySet();
    }
    public void getReport() {
        //创建原型
        Report reportPrototype = new Report();
        //耗费资源的操作
        reportPrototype.loadData();

        //使用原型对象构建新的对象
        Report reportWithTitle = (Report) reportPrototype.copy();
        List<String> reportContent = reportWithTitle.getContents();
        reportContent.add(0, "《江城子·密州出猎》");
        reportContent.add(1, "----------------------------------------------------------");

        for (String s : reportContent) {
            System.out.println(s);
        }
    }

}

class Report implements Prototype {
    private List<String> parts;

    public Report() {
        this.parts = new ArrayList<>();
    }

    public Report(List<String> parts) {
        this.parts = parts;
    }

    //耗时的数据加载操作
    public void loadData() {
        parts.clear();
        parts.add("老夫聊发少年狂，左牵黄，右擎苍，锦帽貂裘，千骑卷平冈。");
        parts.add("为报倾城随太守，亲射虎，看孙郎。");
        parts.add("酒酣胸胆尚开张，鬓微霜，又何妨！持节云中，何日遣冯唐？");
        parts.add("会挽雕弓如满月，西北望，射天狼。");
    }

    public List<String> getContents() {
        return parts;
    }

    @Override
    public Prototype copy() {
        List<String> cloneList = new ArrayList<>(parts);
        return new Report(cloneList);
    }
}


interface Prototype {
    Prototype copy();
}
