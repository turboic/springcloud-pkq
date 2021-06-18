package org.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class Test {
    public static void main(String [] args){
        int one = 2;
        int two = 3;
        //亦或运算符
        //0010
        //0011
        //0001
        int three = one ^ two;
        System.err.println(three);
        List<String> list = new ArrayList<>();
        list.add("国泰金牛创新成长混合");
        list.add("农银汇理工业4.0灵活配置");
        list.add("易方达中小盘混合");
        list.add("中欧明睿新起点混合");
        list.add("天弘沪深300ETF联结A");
        list.add("汇添富价值精选混合A");
        list.add("景顺长城新兴成长混合");
        list.add("天弘中证食品饮料指数A");
        list.add("前海开源国家比较优势混合");
        list.add("诺安混合成长");
        list.add("中欧阿尔法混合C");
        list.add("中信保诚中证800有色指数（LOF）");
        list.add("兴全新视野灵活配置定期开放混合");
        list.add("天弘沪深300ETF联接C");
        list.add("招商中证白酒指数");
        list.add("国泰国证新能源汽车指数基金");
        list.add("信诚周期轮动");
        list.add("微信支付");
        list.add("信诚周期轮动");
        Map<String,String> map = new HashMap<>();
        map.put("国泰聚信价值优势混合C","-612.59");
        for(String s:list){
            String j = map.computeIfAbsent(s.charAt(0)+"",k-> 1.0*2+"---" + s);
            System.err.println(j);
        }
    }
}
