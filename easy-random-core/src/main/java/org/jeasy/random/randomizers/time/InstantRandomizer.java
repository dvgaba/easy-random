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
package org.jeasy.random.randomizers.time;

import java.time.Instant;
import java.util.Date;
import org.jeasy.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random {@link Instant}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class InstantRandomizer implements Randomizer<Instant> {

    private final DateRandomizer dateRandomizer;

    /**
     * Create a new {@link InstantRandomizer}.
     */
    public InstantRandomizer() {
        dateRandomizer = new DateRandomizer();
    }

    /**
     * Create a new {@link InstantRandomizer}.
     *
     * @param seed initial seed
     */
    public InstantRandomizer(final long seed) {
        dateRandomizer = new DateRandomizer(seed);
    }

    @Override
    public Instant getRandomValue() {
        Date randomDate = dateRandomizer.getRandomValue();
        return Instant.ofEpochMilli(randomDate.getTime());
    }
}
