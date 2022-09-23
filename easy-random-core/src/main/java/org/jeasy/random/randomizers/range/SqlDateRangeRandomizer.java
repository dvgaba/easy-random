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

import java.sql.Date;

/**
 * Generate a random {@link java.sql.Date} in a given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SqlDateRangeRandomizer extends AbstractRangeRandomizer<Date> {

    /**
     * Create a new {@link SqlDateRangeRandomizer}.
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     */
    public SqlDateRangeRandomizer(final Date min, final Date max) {
        super(min, max);
    }

    /**
     * Create a new {@link SqlDateRangeRandomizer}.
     *
     * @param min  min value (inclusive)
     * @param max  max value (exclusive)
     * @param seed initial seed
     */
    public SqlDateRangeRandomizer(final Date min, final Date max, final long seed) {
        super(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.after(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected Date getDefaultMinValue() {
        return new Date(Long.MIN_VALUE);
    }

    @Override
    protected Date getDefaultMaxValue() {
        return new Date(Long.MAX_VALUE);
    }

    @Override
    public Date getRandomValue() {
        long minDateTime = min.getTime();
        long maxDateTime = max.getTime();
        long randomDateTime = (long) nextDouble(minDateTime, maxDateTime);
        return new Date(randomDateTime);
    }
}
