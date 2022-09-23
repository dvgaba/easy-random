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
package org.jeasy.random.randomizers.misc;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Locale;
import org.jeasy.random.randomizers.AbstractRandomizerTest;
import org.junit.jupiter.api.Test;

class LocaleRandomizerTest extends AbstractRandomizerTest<Locale> {

    @Test
    void shouldGenerateRandomLocale() {
        assertThat(new LocaleRandomizer().getRandomValue()).isIn(Locale.getAvailableLocales());
    }

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        BigDecimal javaVersion = new BigDecimal(System.getProperty("java.specification.version"));
        if (javaVersion.compareTo(new BigDecimal("18")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("mni", ""));
        } else if (javaVersion.compareTo(new BigDecimal("17")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CA"));
        } else if (javaVersion.compareTo(new BigDecimal("16")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CA"));
        } else if (javaVersion.compareTo(new BigDecimal("15")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CA"));
        } else if (javaVersion.compareTo(new BigDecimal("14")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CA"));
        } else if (javaVersion.compareTo(new BigDecimal("13")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("zh", "CN"));
        } else if (javaVersion.compareTo(new BigDecimal("11")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CK"));
        } else if (javaVersion.compareTo(new BigDecimal("9")) >= 0) {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("sw", "ke"));
        } else {
            assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("nl", "be"));
        }
    }
}
