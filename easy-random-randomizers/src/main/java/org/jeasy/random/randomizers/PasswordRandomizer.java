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
 * A {@link Randomizer} that generates random passwords.
 *
 * @author https://github.com/JJ1216
 */
public class PasswordRandomizer extends FakerBasedRandomizer<String> {

    private boolean includeUppercase, includeSpecial;
    private int min, max;

    /**
     * Create a new {@link PasswordRandomizer}.
     */
    public PasswordRandomizer() {}

    /**
     * Create a new {@link PasswordRandomizer}.
     *
     * @param seed the initial seed
     */
    public PasswordRandomizer(long seed) {
        super(seed);
    }

    /**
     * Create a new {@link PasswordRandomizer}.
     *
     * @param seed   the initial seed
     * @param locale the locale to use
     */
    public PasswordRandomizer(final long seed, final Locale locale) {
        super(seed, locale);
    }

    /**
     * Create a new {@link PasswordRandomizer}.
     *
     * @param seed the initial seed
     * @param min  the minimum number of characters of passwords
     * @param max  the maximum number of characters of passwords
     */
    public PasswordRandomizer(final long seed, final int min, final int max) {
        super(seed);
        this.min = min;
        this.max = max;
    }

    /**
     * Create a new {@link PasswordRandomizer}.
     *
     * @param seed             the initial seed
     * @param min              the minimum number of characters of passwords
     * @param max              the maximum number of characters of passwords
     * @param includeUppercase true to generate passwords containing Uppercase Characters, false otherwise
     */
    public PasswordRandomizer(final long seed, final int min, final int max, final boolean includeUppercase) {
        super(seed);
        this.min = min;
        this.max = max;
        this.includeUppercase = includeUppercase;
    }

    /**
     * Create a new {@link PasswordRandomizer}.
     *
     * @param seed             the initial seed
     * @param min              the minimum number of characters of passwords
     * @param max              the maximum number of characters of passwords
     * @param includeUppercase true to generate passwords containing Uppercase Characters , false otherwise
     * @param includeSpecial   true to generate passwords containing Special Characters, false otherwise
     */
    public PasswordRandomizer(
        final long seed,
        final int min,
        final int max,
        final boolean includeUppercase,
        final boolean includeSpecial
    ) {
        super(seed);
        this.min = min;
        this.max = max;
        this.includeUppercase = includeUppercase;
        this.includeSpecial = includeSpecial;
    }

    @Override
    public String getRandomValue() {
        return faker.internet().password(min, max, includeUppercase, includeSpecial);
    }
}
