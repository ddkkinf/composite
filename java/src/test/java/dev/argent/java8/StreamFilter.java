package dev.argent.java8;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamFilter {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(1, 100).mapToObj(Integer::new).collect(Collectors.toList());
        List<Integer> list1 = list.stream()
                                    .filter(filterA())
                                    .filter(filterB())
                                    .collect(Collectors.toList());
        System.out.println("list1 = " + list1);
    }

    private static Predicate<Integer> filterB() {
        int count = 0;
        System.out.println("A = " + count++);
        return i -> true;
    }

    private static Predicate<Integer> filterA() {
        int count = 0;
        System.out.println("B = " + count++);
        return i -> true;
    }
}
