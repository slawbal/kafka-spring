package com.sb.kafka.kafkaexample;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Await {

    public static <T> Stream<Collection<T>> awaitForList(Supplier<Collection<T>> supplier,
                                                         Duration ofMillis) throws InterruptedException {
        return awaitForList(supplier, ofMillis, Duration.ofMillis(100));
    }

    public static <T> Stream<Collection<T>> awaitForList(Supplier<Collection<T>> supplier,
                                                         Duration ofMillis,
                                                         Duration repeat) throws InterruptedException {
        long start = System.currentTimeMillis();
        Collection<T> ticket;
        do {
            ticket = supplier.get();
            Thread.sleep(repeat.toMillis());
        } while (!isOutOfTime(ofMillis, start) && isEmpty(ticket));
        return Stream.of(ticket);
    }

    public static <T> Stream<T> awaitFor(Supplier<T> supplier, Duration ofMillis) throws InterruptedException {
        return awaitFor(supplier, ofMillis, Duration.ofMillis(100));
    }

    public static <T> Stream<T> awaitFor(Supplier<T> supplier, Duration ofMillis,
                                         Duration repeat) throws InterruptedException {
        long start = System.currentTimeMillis();
        T ticket;
        do {
            ticket = supplier.get();
            Thread.sleep(repeat.toMillis());
        } while (!isOutOfTime(ofMillis, start) && ticket == null);
        return Stream.of(ticket);
    }

    private static boolean isOutOfTime(Duration ofMillis, long start) {
        return System.currentTimeMillis() - start > ofMillis.toMillis();
    }

    private static <T> boolean isEmpty(final Collection<T> ticket) {
        return ticket == null || ticket.isEmpty();
    }
}
