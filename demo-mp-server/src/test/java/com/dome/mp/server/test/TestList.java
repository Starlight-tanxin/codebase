package com.dome.mp.server.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TestList {

    @Data
    @EqualsAndHashCode(callSuper = false)
    static class Student {
        private Integer age;
        private String name;
        private Integer height;
    }


    public static void main(String[] args) {
        Map<String, String> table = new Hashtable<>();
        Student s1 = new Student();
        s1.setAge(18);
        s1.setName("Tx");
        s1.setHeight(170);
        Student s2 = new Student();
        s2.setAge(16);
        s2.setName("Xin");
        s2.setHeight(176);
        Student s3 = new Student();
        s3.setAge(20);
        s3.setName("XinTx");
        s3.setHeight(173);

        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.forEach(student -> System.out.println(JSON.toJSONString(student)));
        System.out.println("进行排序--------");
        studentList.sort((a, b) -> a.getAge() - b.getAge());
        studentList.forEach(student -> System.out.println(JSON.toJSONString(student)));

        System.out.println("进行排序--------");
        studentList = studentList.stream().sorted(Comparator.comparing(Student::getHeight).reversed()).collect(Collectors.toList());
        studentList.forEach(student -> System.out.println(JSON.toJSONString(student)));


        Map<String, String> testMap = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            testMap.put(String.valueOf(i), String.valueOf(i));
        }

        long s1_1 = System.currentTimeMillis();
        for (String s : testMap.keySet()) {
            testMap.get(s);
        }
        long s1_2 = System.currentTimeMillis();
        System.out.println(s1_2 - s1_1);

        long s2_1 = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : testMap.entrySet()) {
            entry.getValue();
        }
        long s2_2 = System.currentTimeMillis();
        System.out.println(s2_2 - s2_1);


        long s3_1 = System.currentTimeMillis();
        Iterator<Map.Entry<String, String>> iter = testMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            entry.getValue();
        }
        long s3_2 = System.currentTimeMillis();
        System.out.println(s3_2 - s3_1);

        LocalDate date  = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.toLocalDate().equals(date));
    }
}
