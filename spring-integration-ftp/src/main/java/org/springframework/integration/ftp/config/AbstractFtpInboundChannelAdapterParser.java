/**
 * 
 */
package org.springframework.integration.ftp.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;

/**
 * @author ozhurakousky
 *
 */
public abstract class AbstractFtpInboundChannelAdapterParser extends AbstractPollingInboundChannelAdapterParser {

	private Set<String> receiveAttrs = new HashSet<String>(Arrays.asList(
			"auto-delete-remote-files-on-sync,filename-pattern,local-working-directory".split(",")));

	@Override
	protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(this.getClassName());
		IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "filter");
		for (String a : receiveAttrs) {
			IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, a);
		}
		FtpNamespaceParserSupport.configureCoreFtpClient(builder, element, parserContext);
		String beanName = BeanDefinitionReaderUtils.registerWithGeneratedName(
				builder.getBeanDefinition(), parserContext.getRegistry());
		return new RuntimeBeanReference(beanName);
	}
	
	protected abstract String getClassName();
}