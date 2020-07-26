/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.spring.namespace.orchestration.parser;

import org.apache.shardingsphere.orchestration.repository.api.config.OrchestrationRepositoryConfiguration;
import org.apache.shardingsphere.spring.namespace.orchestration.constants.InstanceBeanDefinitionTag;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Properties;

/**
 * Orchestration instance parser for spring namespace.
 */
public final class InstanceBeanDefinitionParser extends AbstractBeanDefinitionParser {
    
    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(OrchestrationRepositoryConfiguration.class);
        factory.addConstructorArgValue(element.getAttribute(InstanceBeanDefinitionTag.TYPE_TAG));
        factory.addConstructorArgValue(element.getAttribute(InstanceBeanDefinitionTag.SERVER_LISTS_TAG));
        factory.addConstructorArgValue(element.getAttribute(InstanceBeanDefinitionTag.NAMESPACE_TAG));
        factory.addConstructorArgValue(parseProperties(element, parserContext));
        return factory.getBeanDefinition();
    }
    
    private Properties parseProperties(final Element element, final ParserContext parserContext) {
        Element propsElement = DomUtils.getChildElementByTagName(element, InstanceBeanDefinitionTag.PROP_TAG);
        return null == propsElement ? new Properties() : parserContext.getDelegate().parsePropsElement(propsElement);
    }
}
