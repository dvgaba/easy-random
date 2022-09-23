/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.randomizers.range;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * Generate a random {@link LocalDate} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalDateRangeRandomizer extends AbstractRangeRandomizer<LocalDate> {

    /**
     * Create a new {@link LocalDateRangeRandomizer}.
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     */
    public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max) {
        super(min, max);
    }

    /**
     * Create a new {@link LocalDateRangeRandomizer}.
     *
     * @param min  min value (inclusive)
     * @param max  max value (exclusive)
     * @param seed initial seed
     */
    public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max, final long seed) {
        super(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected LocalDate getDefaultMinValue() {
        return LocalDate.MIN;
    }

    @Override
    protected LocalDate getDefaultMaxValue() {
        return LocalDate.MAX;
    }

    @Override
    public LocalDate getRandomValue() {
        long minEpochDay = min.getLong(ChronoField.EPOCH_DAY);
        long maxEpochDay = max.getLong(ChronoField.EPOCH_DAY);
        long randomEpochDay = (long) nextDouble(minEpochDay, maxEpochDay);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
