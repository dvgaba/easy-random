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
package org.jeasy.random.randomizers.text;

import java.nio.charset.Charset;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link String}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class StringRandomizer extends AbstractRandomizer<String> {

    private final CharacterRandomizer characterRandomizer;

    private int maxLength = EasyRandomParameters.DEFAULT_STRING_LENGTH_RANGE.getMax();
    private int minLength = EasyRandomParameters.DEFAULT_STRING_LENGTH_RANGE.getMin();

    /**
     * Create a new {@link StringRandomizer}.
     */
    public StringRandomizer() {
        super();
        characterRandomizer = new CharacterRandomizer();
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param charset to use
     */
    public StringRandomizer(final Charset charset) {
        characterRandomizer = new CharacterRandomizer(charset);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param maxLength of the String to generate
     */
    public StringRandomizer(int maxLength) {
        super();
        this.maxLength = maxLength;
        characterRandomizer = new CharacterRandomizer();
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param seed initial seed
     */
    public StringRandomizer(long seed) {
        super(seed);
        characterRandomizer = new CharacterRandomizer(seed);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param charset to use
     * @param seed    initial seed
     */
    public StringRandomizer(final Charset charset, final long seed) {
        super(seed);
        characterRandomizer = new CharacterRandomizer(charset, seed);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param maxLength of the String to generate
     * @param seed      initial seed
     */
    public StringRandomizer(final int maxLength, final long seed) {
        super(seed);
        this.maxLength = maxLength;
        characterRandomizer = new CharacterRandomizer(seed);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param maxLength of the String to generate
     * @param minLength of the String to generate
     * @param seed      initial seed
     */
    public StringRandomizer(final int minLength, final int maxLength, final long seed) {
        super(seed);
        this.maxLength = maxLength;
        this.minLength = minLength;
        characterRandomizer = new CharacterRandomizer(seed);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param charset   to use
     * @param maxLength of the String to generate
     * @param seed      initial seed
     */
    public StringRandomizer(final Charset charset, final int maxLength, final long seed) {
        super(seed);
        this.maxLength = maxLength;
        characterRandomizer = new CharacterRandomizer(charset, seed);
    }

    /**
     * Create a new {@link StringRandomizer}.
     *
     * @param charset   to use
     * @param maxLength of the String to generate
     * @param minLength of the String to generate
     * @param seed      initial seed
     */
    public StringRandomizer(final Charset charset, final int minLength, final int maxLength, final long seed) {
        super(seed);
        if (minLength > maxLength) {
            throw new IllegalArgumentException("minLength should be less than or equal to maxLength");
        }
        this.maxLength = maxLength;
        this.minLength = minLength;
        characterRandomizer = new CharacterRandomizer(charset, seed);
    }

    @Override
    public String getRandomValue() {
        int length = (int) nextDouble(minLength, maxLength);
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = characterRandomizer.getRandomValue();
        }
        return new String(chars);
    }
}
