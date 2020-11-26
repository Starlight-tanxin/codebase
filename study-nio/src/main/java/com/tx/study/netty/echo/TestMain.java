package com.tx.study.netty.echo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/24 11:25
 */
public class TestMain {

    private static List<String> getImage(String str) {
        List<String> tmp = new ArrayList<String>();
        String regex = "<img src=\"([^\"]+?)\" />";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        System.out.println(str);
        while (matcher.find()) {
            tmp.add(matcher.group(1).toString());
        }
        return tmp;
    }

    public static void main(String[] args) {
//        String str = "2020-11-29";
//        String regex = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(null);
//        System.out.println(str);
//        while (matcher.find()) {
//            System.out.println(matcher.matches());
//
//        }
        String ids = "1,2,3,4,5,6, ";
        String trim = ids.trim();
        int length = trim.length()-1;
        System.out.println(length);
        int lastIndexOf = trim.lastIndexOf(",");
        System.out.println(lastIndexOf);
        System.out.println(length == lastIndexOf);
        String s = ids.substring(0, lastIndexOf);
        System.out.println(s);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(10);
        integerList.add(99);
        integerList.add(100);
        integerList.add(101);
        integerList.add(102);

        List<Integer> collect = integerList.stream().filter(e -> e > 100 || e == 99)
                .filter(e-> e != 102).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}
