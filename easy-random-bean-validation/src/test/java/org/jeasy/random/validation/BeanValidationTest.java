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
package org.jeasy.random.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.BigDecimalRangeRandomizer;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.registry.CustomRandomizerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeanValidationTest {

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void sizeConstraintShouldNotBePropagatedToEmbeddedBeans() {
        EasyRandomParameters parameters = new EasyRandomParameters()
            .collectionSizeRange(11, 15)
            .stringLengthRange(16, 20);
        easyRandom = new EasyRandom(parameters);
        BeanValidationAnnotatedBean bean = easyRandom.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean.getSizedListEmbeddedBean().size()).isBetween(2, 10); // @Size constraint
        assertThat(bean.getSizedListEmbeddedBean())
            .allSatisfy(embeddedBean -> {
                assertThat(embeddedBean.getItems().size()).isBetween(11, 15);
                assertThat(embeddedBean.getItems())
                    .allSatisfy(stringItem -> assertThat(stringItem.length()).isBetween(16, 20));
            });
    }

    @Test
    void sizeConstraintShouldTakePrecedenceOverCollectionSizeRangeInEmbeddedBeans() {
        EasyRandomParameters parameters = new EasyRandomParameters()
            .collectionSizeRange(11, 15)
            .stringLengthRange(16, 20);
        easyRandom = new EasyRandom(parameters);
        BeanValidationAnnotatedBean bean = easyRandom.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean.getSizedListEmbeddedBean().size()).isBetween(2, 10); // @Size constraint
        assertThat(bean.getSizedListEmbeddedBean())
            .allSatisfy(embeddedBean -> {
                assertThat(embeddedBean.getItems().size()).isBetween(11, 15);
                assertThat(embeddedBean.getOtherItems().size()).isBetween(3, 5); // @Size(min = 3, max = 5) constraint should take precedence over collectionSizeRange(11, 15)
                assertThat(embeddedBean.getItems())
                    .allSatisfy(stringItem -> assertThat(stringItem.length()).isBetween(16, 20));
                assertThat(embeddedBean.getOtherItems())
                    .allSatisfy(stringItem -> assertThat(stringItem.length()).isBetween(16, 20));
            });
    }

    @Test
    void generatedValuesShouldBeValidAccordingToValidationConstraints() {
        BeanValidationAnnotatedBean bean = easyRandom.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean).isNotNull();

        assertThat(bean.isUnsupported()).isFalse(); // @AssertFalse boolean unsupported;

        assertThat(bean.isActive()).isTrue(); // @AssertTrue boolean active;

        assertThat(bean.getUnusedString()).isNull(); // @Null String unusedString;

        assertThat(bean.getUsername()).isNotNull(); // @NotNull String username;

        assertThat(bean.getBirthday()).isInThePast(); // @Past Date birthday;

        assertThat(bean.getBirthdayLocalDateTime()).isBefore(LocalDateTime.now()); // @Past LocalDateTime birthdayLocalDateTime;

        assertThat(bean.getPastOrPresent()).isBeforeOrEqualTo(new Date()); // @PastOrPresent Date pastOrPresent;

        assertThat(bean.getEventDate()).isInTheFuture(); // @Future Date eventDate;

        assertThat(bean.getEventLocalDateTime()).isAfter(LocalDateTime.now()); // @Future LocalDateTime eventLocalDateTime;

        assertThat(bean.getFutureOrPresent()).isAfterOrEqualTo(new Date()); // @FutureOrPresent Date eventDate;

        assertThat(bean.getPositive()).isPositive(); // @Positive int positive;

        assertThat(bean.getPositiveOrZero()).isNotNegative(); // @PositiveOrZero int positiveOrZero;

        assertThat(bean.getNegative()).isLessThan(0); // @Negative int negative;

        assertThat(bean.getNegativeOrZero()).isNotPositive(); // @NegativeOrZero int negativeOrZero;

        assertThat(bean.getPositiveLong()).isPositive(); // @Positive Long positive;

        assertThat(bean.getPositiveOrZeroLong()).isNotNegative(); // @PositiveOrZero Long positiveOrZero;

        assertThat(bean.getNegativeLong()).isLessThan(0); // @Negative Long negative;

        assertThat(bean.getNegativeOrZeroLong()).isLessThanOrEqualTo(0); // @NegativeOrZero Long negativeOrZero;

        assertThat(bean.getNotBlank()).isNotBlank(); // @NotBlank String notBlank;

        assertThat(bean.getEmail()).isNotBlank().contains(".", "@"); // @Email String email;

        assertThat(bean.getMaxQuantity()).isLessThanOrEqualTo(10); // @Max(10) int maxQuantity;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

        assertThat(bean.getMaxDiscount()).isLessThanOrEqualTo(new BigDecimal("30.00")); // @DecimalMax("30.00") BigDecimal maxDiscount;

        assertThat(bean.getMinDiscount()).isGreaterThanOrEqualTo(new BigDecimal("5.00")); // @DecimalMin("5.00") BigDecimal minDiscount;

        assertThat(bean.getDiscount()).isLessThanOrEqualTo(new BigDecimal("1.00")); // @DecimalMax("1.00") BigDecimal discount;
        assertThat(bean.getDiscount()).isGreaterThanOrEqualTo(new BigDecimal("0.01")); // @DecimalMin("0.01") BigDecimal discount;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

        assertThat(bean.getBriefMessage().length()).isBetween(2, 10); // @Size(min=2, max=10) String briefMessage;
        assertThat(bean.getSizedCollection().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedCollection;
        assertThat(bean.getSizedList().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedList;
        assertThat(bean.getSizedListEmbeddedBean().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedListEmbeddedBean;
        assertThat(bean.getSizedSet().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedSet;
        assertThat(bean.getSizedMap().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedMap;
        assertThat(bean.getSizedArray().length).isBetween(2, 10); // @Size(min=2, max=10) String sizedArray;
        assertThat(bean.getSizedString().length()).isBetween(2, 255); // @Size(min=2) String sizedString;

        assertThat(bean.getRegexString()).matches("[a-z]{4}");
    }

    @Test
    void generatedValuesShouldBeValidAccordingToValidationConstraintsOnMethod() {
        BeanValidationMethodAnnotatedBean bean = easyRandom.nextObject(BeanValidationMethodAnnotatedBean.class);

        assertThat(bean).isNotNull();

        assertThat(bean.isUnsupported()).isFalse(); // @AssertFalse boolean unsupported;

        assertThat(bean.isActive()).isTrue(); // @AssertTrue boolean active;

        assertThat(bean.getUnusedString()).isNull(); // @Null String unusedString;

        assertThat(bean.getUsername()).isNotNull(); // @NotNull String username;

        assertThat(bean.getBirthday()).isInThePast(); // @Past Date birthday;

        assertThat(bean.getBirthdayLocalDateTime()).isBefore(LocalDateTime.now()); // @Past LocalDateTime birthdayLocalDateTime;

        assertThat(bean.getPastOrPresent()).isBeforeOrEqualTo(new Date()); // @PastOrPresent Date pastOrPresent;

        assertThat(bean.getEventDate()).isInTheFuture(); // @Future Date eventDate;

        assertThat(bean.getEventLocalDateTime()).isAfter(LocalDateTime.now()); // @Future LocalDateTime eventLocalDateTime;

        assertThat(bean.getFutureOrPresent()).isAfterOrEqualTo(new Date()); // @FutureOrPresent Date eventDate;

        assertThat(bean.getPositive()).isPositive(); // @Positive int positive;

        assertThat(bean.getPositiveOrZero()).isNotNegative(); // @PositiveOrZero int positiveOrZero;

        assertThat(bean.getNegative()).isLessThan(0); // @Negative int negative;

        assertThat(bean.getNegativeOrZero()).isLessThanOrEqualTo(0); // @NegativeOrZero int negativeOrZero;

        assertThat(bean.getPositiveLong()).isPositive(); // @Positive Long positive;

        assertThat(bean.getPositiveOrZeroLong()).isNotNegative(); // @PositiveOrZero Long positiveOrZero;

        assertThat(bean.getNegativeLong()).isLessThan(0); // @Negative Long negative;

        assertThat(bean.getNegativeOrZeroLong()).isLessThanOrEqualTo(0); // @NegativeOrZero Long negativeOrZero;

        assertThat(bean.getNotBlank()).isNotBlank(); // @NotBlank String notBlank;

        assertThat(bean.getEmail()).isNotBlank().contains(".", "@"); // @Email String email;

        assertThat(bean.getMaxQuantity()).isLessThanOrEqualTo(10); // @Max(10) int maxQuantity;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

        assertThat(bean.getMaxDiscount()).isLessThanOrEqualTo(new BigDecimal("30.00")); // @DecimalMax("30.00") BigDecimal maxDiscount;

        assertThat(bean.getMinDiscount()).isGreaterThanOrEqualTo(new BigDecimal("5.00")); // @DecimalMin("5.00") BigDecimal minDiscount;

        assertThat(bean.getDiscount()).isLessThanOrEqualTo(new BigDecimal("1.00")); // @DecimalMax("1.00") BigDecimal discount;
        assertThat(bean.getDiscount()).isGreaterThanOrEqualTo(new BigDecimal("0.01")); // @DecimalMin("0.01") BigDecimal discount;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

        assertThat(bean.getBriefMessage().length()).isBetween(2, 10); // @Size(min=2, max=10) String briefMessage;
        assertThat(bean.getSizedCollection().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedCollection;
        assertThat(bean.getSizedList().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedList;
        assertThat(bean.getSizedListEmbeddedBean().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedListEmbeddedBean;
        assertThat(bean.getSizedSet().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedSet;
        assertThat(bean.getSizedMap().size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedMap;
        assertThat(bean.getSizedArray().length).isBetween(2, 10); // @Size(min=2, max=10) String sizedArray;
        assertThat(bean.getSizedString().length()).isBetween(2, 255); // @Size(min=2) String sizedString;

        assertThat(bean.getRegexString()).matches("[a-z]{4}");
    }

    @Test
    void generatedValuesForBeanWithoutReadMethod() {
        BeanValidationWithoutReadMethodBean bean = easyRandom.nextObject(BeanValidationWithoutReadMethodBean.class);

        assertThat(bean).hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        EasyRandomParameters parameters = new EasyRandomParameters().seed(123L);
        EasyRandom random = new EasyRandom(parameters);

        BeanValidationAnnotatedBean bean = random.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean.getUsername()).isEqualTo("eOMtThyhVNLWUZNRcBaQKxI");
        // uses DateRange with now as end, so test is not repeatable
        // assertThat(bean.getBirthday()).isEqualTo("2007-07-22T13:20:35.628"); // same for birthdayLocalDateTime
        // uses DateRange with now as start, so test is not repeatable
        // assertThat(bean.getEventDate()).isEqualTo("2017-07-22T13:20:35.628"); // same for eventLocalDateTime
        assertThat(bean.getMaxQuantity()).isEqualTo(-2055951745);
        assertThat(bean.getMinQuantity()).isEqualTo(91531906);
        assertThat(bean.getMaxDiscount().doubleValue()).isEqualTo(new BigDecimal(1.278685899397155).doubleValue());
        assertThat(bean.getMinDiscount().doubleValue()).isEqualTo(new BigDecimal(7.66228287663837E306).doubleValue());
        assertThat(bean.getDiscount().doubleValue()).isEqualTo(new BigDecimal(0.18272370804913468).doubleValue());
        assertThat(bean.getMinQuantity()).isEqualTo(91531906);
        assertThat(bean.getBriefMessage()).isEqualTo("tg");
        assertThat(bean.getRegexString()).isEqualTo("vuna");
        assertThat(bean.getPositive()).isEqualTo(91531902);
        assertThat(bean.getPositiveOrZero()).isEqualTo(91531901);
        assertThat(bean.getNegative()).isEqualTo(-2055951746);
        assertThat(bean.getNegativeOrZero()).isEqualTo(-2055951746);
        assertThat(bean.getEmail()).isEqualTo("celine.schoen@hotmail.com");
        assertThat(bean.getNotBlank()).isEqualTo("tg");
    }

    @Test
    void generatedBeanShouldBeValidUsingBeanValidationAPI() {
        BeanValidationAnnotatedBean bean = easyRandom.nextObject(BeanValidationAnnotatedBean.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BeanValidationAnnotatedBean>> violations = validator.validate(bean);

        assertThat(violations).isEmpty();
    }

    @Test
    void customBeanValidationRegistryTest() {
        // given
        class Salary {

            @Digits(integer = 2, fraction = 2) // OSS developer salary.. :-)
            private BigDecimal amount;
        }

        EasyRandomParameters parameters = new EasyRandomParameters()
            .randomizerRegistry(new MyCustomBeanValidationRandomizerRegistry());
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Salary salary = easyRandom.nextObject(Salary.class);

        // then
        assertThat(salary).isNotNull();
        assertThat(salary.amount).isLessThanOrEqualTo(new BigDecimal("99.99"));
    }

    @Test
    void customRegistryTest() {
        // given
        class Amount {

            @NotNull
            @Digits(integer = 12, fraction = 3)
            protected BigDecimal amount;
        }
        class DiscountEffect {

            @Digits(integer = 6, fraction = 4)
            protected BigDecimal percentage;

            protected Amount amount;

            @Digits(integer = 12, fraction = 3)
            protected BigDecimal quantity;

            @NotNull
            @DecimalMax("65535")
            @DecimalMin("1")
            protected Integer size;
        }
        class Discount {

            @NotNull
            @Size(min = 1)
            @Valid
            protected List<DiscountEffect> discountEffects;
        }

        CustomRandomizerRegistry registry = new CustomRandomizerRegistry();
        registry.registerRandomizer(
            BigDecimal.class,
            new BigDecimalRangeRandomizer(Double.valueOf(5d), Double.valueOf(10d), Integer.valueOf(3))
        );
        registry.registerRandomizer(Integer.class, new IntegerRangeRandomizer(5, 10));
        EasyRandomParameters parameters = new EasyRandomParameters().randomizerRegistry(registry);
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Discount discount = easyRandom.nextObject(Discount.class);

        // then
        assertThat(discount.discountEffects)
            .isNotEmpty()
            .allSatisfy(discountEffect -> {
                assertThat(discountEffect).isNotNull();
                assertThat(discountEffect.percentage).isBetween(new BigDecimal("5.000"), new BigDecimal("10.000"));
                assertThat(discountEffect.quantity).isBetween(new BigDecimal("5.000"), new BigDecimal("10.000"));
                assertThat(discountEffect.amount.amount).isBetween(new BigDecimal("5.000"), new BigDecimal("10.000"));
                assertThat(discountEffect.size).isBetween(5, 10);
            });
    }
}
