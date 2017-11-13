package dev.argent.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class TreeMapSort {
    public static void main(String[] args) {
        TreeMap<Double, Boolean> treeMap = new TreeMap<>((o1, o2) -> o1.hashCode() % 100 > o2.hashCode() % 100 ? 1 : -1);
        for (int i = 0; i < 100; i++) {
            double k = ThreadLocalRandom.current().nextDouble();
            boolean v = ThreadLocalRandom.current().nextBoolean();
            treeMap.put(k, v);
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < treeMap.size(); i++) {
            list.add(treeMap.pollFirstEntry().getKey().hashCode());
        }
        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }
}
