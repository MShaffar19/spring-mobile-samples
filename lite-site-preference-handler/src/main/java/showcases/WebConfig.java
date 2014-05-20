/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package showcases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mobile.DeviceResolverAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Roy Clarkson
 * @see WebMvcConfigurer
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Configured in Spring Boot's {@link DeviceResolverAutoConfiguration}
	 */
	@Autowired
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor;

	@Bean
	public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
		return new SitePreferenceHandlerInterceptor();
	}

	/**
	 * Configured in Spring Boot's {@link DeviceResolverAutoConfiguration}
	 */
	@Autowired
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver;

	@Bean
	public SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver() {
		return new SitePreferenceHandlerMethodArgumentResolver();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		/*
		 * Include the handler for device resolution. Strictly speaking, a device resolver
		 * is not required for site preference management. However for this sample
		 * application, it's useful to see what device is being resolved.
		 */
		registry.addInterceptor(deviceResolverHandlerInterceptor);

		/*
		 * Include the handler for site preference management. This handler defaults to
		 * using cookie based storage.
		 */
		registry.addInterceptor(sitePreferenceHandlerInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		/*
		 * Include the argument resolver that supports accessing the current device from
		 * the web request. As with the device resolver, this argument resolver is not
		 * required for site preference management. However for this sample application,
		 * it's useful to see what device is being resolved.
		 */
		argumentResolvers.add(deviceHandlerMethodArgumentResolver);

		/*
		 * Include the argument resolver that supports accessing the current site
		 * preference from the web request
		 */
		argumentResolvers.add(sitePreferenceHandlerMethodArgumentResolver());
	}

}