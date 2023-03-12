/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.spring.context.aop;

import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * In order to support the semantics of {@link org.springframework.context.annotation.Configuration} in Spring. This class
 * creates a subclass of each {@link org.springframework.context.annotation.Configuration} so that singletons are honoured if
 * invoked explicitly.
 *
 * @see SpringConfigurationInterceptor
 * @since 1.0
 * @author graemerocher
 */
@SuppressWarnings("WeakerAccess")
@Around
@Type(SpringConfigurationInterceptor.class)
@Documented
@Retention(RUNTIME)
public @interface SpringConfigurationAdvice {
}
