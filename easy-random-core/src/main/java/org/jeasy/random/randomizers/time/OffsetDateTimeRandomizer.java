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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.jeasy.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random {@link OffsetDateTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class OffsetDateTimeRandomizer implements Randomizer<OffsetDateTime> {

    private LocalDateRandomizer localDateRandomizer;
    private LocalTimeRandomizer localTimeRandomizer;
    private ZoneOffsetRandomizer zoneOffsetRandomizer;

    /**
     * Create a new {@link OffsetDateTimeRandomizer}.
     */
    public OffsetDateTimeRandomizer() {
        localDateRandomizer = new LocalDateRandomizer();
        localTimeRandomizer = new LocalTimeRandomizer();
        zoneOffsetRandomizer = new ZoneOffsetRandomizer();
    }

    /**
     * Create a new {@link OffsetDateTimeRandomizer}.
     *
     * @param seed initial seed
     */
    public OffsetDateTimeRandomizer(final long seed) {
        localDateRandomizer = new LocalDateRandomizer(seed);
        localTimeRandomizer = new LocalTimeRandomizer(seed);
        zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
    }

    @Override
    public OffsetDateTime getRandomValue() {
        LocalDate randomLocalDate = localDateRandomizer.getRandomValue();
        LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
        ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
        return OffsetDateTime.of(randomLocalDate, randomLocalTime, randomZoneOffset);
    }

    public void setLocalDateRandomizer(final LocalDateRandomizer localDateRandomizer) {
        this.localDateRandomizer = localDateRandomizer;
    }

    public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
        this.localTimeRandomizer = localTimeRandomizer;
    }

    public void setZoneOffsetRandomizer(final ZoneOffsetRandomizer zoneOffsetRandomizer) {
        this.zoneOffsetRandomizer = zoneOffsetRandomizer;
    }
}
