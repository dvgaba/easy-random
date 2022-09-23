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
package org.jeasy.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import org.junit.jupiter.api.Test;

public class FieldNameMatchingWithRegex {

    public class Foo {

        private String name1;
        private String name2;
        private String nickname;
        private String job;
        private Bar bar;

        public Foo() {}

        public String getName1() {
            return this.name1;
        }

        public String getName2() {
            return this.name2;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getJob() {
            return this.job;
        }

        public Bar getBar() {
            return this.bar;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }
    }

    public class Bar {

        private String name;
        private String address;

        public Bar() {}

        public String getName() {
            return this.name;
        }

        public String getAddress() {
            return this.address;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Test
    void testFieldDefinitionWithNameAsRegexp() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(named("name.*").and(ofType(String.class)), () -> "foo");
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Foo foo = easyRandom.nextObject(Foo.class);

        // then
        assertThat(foo.getName1()).isEqualTo("foo");
        assertThat(foo.getName2()).isEqualTo("foo");
        assertThat(foo.getBar().getName()).isEqualTo("foo");

        assertThat(foo.getNickname()).isNotEqualTo("foo");
        assertThat(foo.getJob()).isNotEqualTo("foo");
        assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
    }

    // non regression test
    @Test
    void testFieldDefinitionWithNameNotAsRegexp() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(named("name").and(ofType(String.class)), () -> "foo");
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Foo foo = easyRandom.nextObject(Foo.class);

        // then
        assertThat(foo.getBar().getName()).isEqualTo("foo");

        assertThat(foo.getName1()).isNotEqualTo("foo");
        assertThat(foo.getName2()).isNotEqualTo("foo");
        assertThat(foo.getNickname()).isNotEqualTo("foo");
        assertThat(foo.getJob()).isNotEqualTo("foo");
        assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
    }
}
