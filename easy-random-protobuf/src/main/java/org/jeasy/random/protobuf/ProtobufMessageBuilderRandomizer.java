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
package org.jeasy.random.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.RandomizerContext;

/** Generate a random Protobuf {@link Message.Builder}. */
public class ProtobufMessageBuilderRandomizer implements ContextAwareRandomizer<Builder> {

  private final ProtobufMessageRandomizer protobufMessageRandomizer;
  private RandomizerContext context;

  public ProtobufMessageBuilderRandomizer(
      Class<Message.Builder> messageBuilderClass,
      EasyRandom easyRandom,
      EasyRandomParameters parameters) {
    this.protobufMessageRandomizer =
        new ProtobufMessageRandomizer(
            retrieveMessageClassFromBuilderClass(messageBuilderClass), easyRandom, parameters);
  }

  @SuppressWarnings("unchecked")
  private static Class<Message> retrieveMessageClassFromBuilderClass(
      Class<Message.Builder> messageBuilderClass) {
    return (Class<Message>) messageBuilderClass.getEnclosingClass();
  }

  @Override
  public Message.Builder getRandomValue() {
    return protobufMessageRandomizer.getRandomValue().toBuilder();
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }

  @Override
  public void setRandomizerContext(RandomizerContext context) {
    this.context = context;
    protobufMessageRandomizer.setRandomizerContext(context);
  }
}
