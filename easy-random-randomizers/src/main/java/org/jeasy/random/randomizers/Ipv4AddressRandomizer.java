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
package org.jeasy.random.randomizers;

import java.util.Locale;
import org.jeasy.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random IPv4 addresses.
 *
 * @author Michael Düsterhus
 */
public class Ipv4AddressRandomizer extends FakerBasedRandomizer<String> {

    /**
     * Create a new {@link Ipv4AddressRandomizer}.
     */
    public Ipv4AddressRandomizer() {}

    /**
     * Create a new {@link Ipv4AddressRandomizer}.
     *
     * @param seed
     *          the initial seed
     */
    public Ipv4AddressRandomizer(long seed) {
        super(seed);
    }

    /**
     * Create a new {@link Ipv4AddressRandomizer}.
     *
     * @param seed
     *          the initial seed
     * @param locale
     *          the locale to use
     */
    public Ipv4AddressRandomizer(final long seed, final Locale locale) {
        super(seed, locale);
    }

    @Override
    public String getRandomValue() {
        return faker.internet().ipV4Address();
    }
}
