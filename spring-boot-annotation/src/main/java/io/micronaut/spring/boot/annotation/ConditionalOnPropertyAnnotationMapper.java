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
package io.micronaut.spring.boot.annotation;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.AnnotationValueBuilder;
import io.micronaut.core.util.ArrayUtils;
import io.micronaut.inject.visitor.VisitorContext;
import io.micronaut.spring.annotation.AbstractSpringAnnotationMapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maps ConditionalOnProperty to Micronaut Requires.
 *
 * @author graemerocher
 * @since 1.0
 */
public class ConditionalOnPropertyAnnotationMapper extends AbstractSpringAnnotationMapper {
    @Override
    public String getName() {
        return "org.springframework.boot.autoconfigure.condition.ConditionalOnProperty";
    }

    @Override
    protected List<AnnotationValue<?>> mapInternal(AnnotationValue<Annotation> annotation, VisitorContext visitorContext) {
        String[] propertyNames = annotation.stringValues(AnnotationMetadata.VALUE_MEMBER);
        if (ArrayUtils.isEmpty(propertyNames)) {
            propertyNames = annotation.stringValues("name");
        }
        if (propertyNames != null) {
            final String prefix = annotation.get("prefix", String.class).orElse(null);
            final boolean matchIfMissing = annotation.get("matchIfMissing", boolean.class).orElse(false);
            final String havingValue = annotation.get("havingValue", String.class).orElse(null);
            List<AnnotationValue<?>> annotationValues = new ArrayList<>(propertyNames.length);
            for (String propertyName : propertyNames) {
                if (prefix != null) {
                    propertyName = prefix + "." + propertyName;
                }

                final AnnotationValueBuilder<Requires> builder = AnnotationValue.builder(Requires.class);
                builder.member(matchIfMissing ? "missingProperty" : "property", propertyName);
                if (havingValue != null) {
                    builder.value(havingValue);
                }
                annotationValues.add(builder.build());

            }

            return annotationValues;
        }
        return Collections.emptyList();
    }
}
