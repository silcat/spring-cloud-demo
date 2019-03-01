package com.example.common.configuration.monitor;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.sampler.SamplerProperties;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义采样率（最小采样率为万分之一）
 */
public class DemoPercentageBasedSampler implements Sampler {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final BitSet sampleDecisions;
    private final SamplerProperties configuration;

    public DemoPercentageBasedSampler(SamplerProperties configuration) {
        int outOf100 = (int) (configuration.getPercentage() * 10000.0f);
        this.sampleDecisions = randomBitSet(10000, outOf100, new Random());
        this.configuration = configuration;
    }

    @Override
    public boolean isSampled(Span currentSpan) {
        if (this.configuration.getPercentage() == 0 || currentSpan == null) {
            return false;
        } else if (this.configuration.getPercentage() == 1.0f) {
            return true;
        }
        synchronized (this) {
            final int i = this.counter.getAndIncrement();
            boolean result = this.sampleDecisions.get(i);
            if (i == 9999) {
                this.counter.set(0);
            }
            return result;
        }
    }

    public BitSet randomBitSet(int size, int cardinality, Random rnd) {
        BitSet result = new BitSet(size);
        int[] chosen = new int[cardinality];
        int i;
        for (i = 0; i < cardinality; ++i) {
            chosen[i] = i;
            result.set(i);
        }
        for (; i < size; ++i) {
            int j = rnd.nextInt(i + 1);
            if (j < cardinality) {
                result.clear(chosen[j]);
                result.set(i);
                chosen[j] = i;
            }
        }
        return result;
    }
}
