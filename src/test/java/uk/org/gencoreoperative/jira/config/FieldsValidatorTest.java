/**
 * MIT License
 * <p>
 * Copyright (c) 2024 GencoreOperative
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package uk.org.gencoreoperative.jira.config;

import static org.forgerock.cuppa.Cuppa.describe;
import static org.forgerock.cuppa.Cuppa.it;
import static org.forgerock.cuppa.Cuppa.when;

import org.assertj.core.api.Assertions;
import org.forgerock.cuppa.Test;
import org.forgerock.cuppa.junit.CuppaRunner;
import org.junit.runner.RunWith;

import com.beust.jcommander.ParameterException;

@Test
@RunWith(CuppaRunner.class)
public class FieldsValidatorTest {
    private final FieldsValidator validator = new FieldsValidator();

    {
        describe(FieldsValidator.class.getSimpleName(), () -> {
            when("asking for a method name that does not exist", () -> {
                it("fails with a ParameterException", () -> {
                    Assertions.assertThatThrownBy(() -> validator.validate("", "badger"))
                            .isInstanceOf(ParameterException.class);
                });
            });
            when("asking for a method name that does exist", () -> {
                it("it validates correctly", () -> {
                    validator.validate("", "summary");
                });
            });
            when("asking for multiple methods exist", () -> {
                it("it validates correctly", () -> {
                    validator.validate("", "description,summary");
                });
            });
        });
    }

}