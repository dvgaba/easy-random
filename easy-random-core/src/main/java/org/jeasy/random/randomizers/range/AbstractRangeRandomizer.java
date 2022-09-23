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

import org.jeasy.random.randomizers.AbstractRandomizer;

/**
 * Abstract class for range randomizers.
 *
 * @param <T> the type of objects in the defined range.
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public abstract class AbstractRangeRandomizer<T> extends AbstractRandomizer<T> {

    final T min;
    final T max;

    protected AbstractRangeRandomizer(final T min, final T max) {
        super();
        this.min = min != null ? min : getDefaultMinValue();
        this.max = max != null ? max : getDefaultMaxValue();
        checkValues();
    }

    protected AbstractRangeRandomizer(final T min, final T max, final long seed) {
        super(seed);
        this.min = min != null ? min : getDefaultMinValue();
        this.max = max != null ? max : getDefaultMaxValue();
        checkValues();
    }

    protected abstract void checkValues();

    protected abstract T getDefaultMinValue();

    protected abstract T getDefaultMaxValue();
}
