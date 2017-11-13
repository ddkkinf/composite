package dev.argent.java8;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureJoin {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        long start = System.currentTimeMillis();
        List<String> list = IntStream.rangeClosed(1, 100).boxed()
                                     .map(integer -> CompletableFuture.supplyAsync(new ValueProvideTask(integer), executorService))
                                     .collect(Collectors.toList()).stream()
                                     .map(future -> future.thenApply(Object::toString)
                                                          .exceptionally(throwable -> {
                                                              System.out.println("throwable.getMessage() = " + throwable.getMessage());
                                                              return null;
                                                          }))
                                     .map(CompletableFuture::join)
                                     .filter(Objects::nonNull)
                                     .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("duration = " + (end - start));
        System.out.println("list = " + list);
        executorService.shutdown();
    }

    private static class ValueProvideTask implements Supplier<Integer> {
        private final Integer value;

        public ValueProvideTask(Integer value) {
            this.value = value;
        }

        @Override
        public Integer get() {
            long ms = 1000L;
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                // ignore
            }
            if (value % 50 == 0) {
                throw new RuntimeException("When failed.");
            }
            return value;
        }
    }
}
