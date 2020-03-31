package com.sparcsky.bsp.util;


import java.lang.management.ManagementFactory;

public class MemoryChecker {

    private static final long OFFSET = measureInternal(() -> {
    });

    /**
     * @return amount of memory allocated while executing provided {@link Runnable}
     */
    private static long measureInternal(final Runnable x) {
        final long now = getCurrentThreadAllocatedBytes();
        x.run();
        return getCurrentThreadAllocatedBytes() - now;
    }

    public static long measure(final Runnable x) {
        System.gc();
        final long mi = measureInternal(x);
        return mi - OFFSET;
    }

    @SuppressWarnings("restriction")
    private static long getCurrentThreadAllocatedBytes() {
        return ((com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean())
                .getThreadAllocatedBytes(Thread.currentThread().getId());
    }
}
