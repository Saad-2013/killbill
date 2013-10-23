/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.currency.glue;

import org.skife.config.ConfigSource;
import org.skife.config.ConfigurationObjectFactory;

import com.ning.billing.currency.DefaultCurrencyService;
import com.ning.billing.currency.api.CurrencyConversionApi;
import com.ning.billing.currency.api.CurrencyService;
import com.ning.billing.currency.api.DefaultCurrencyConversionApi;
import com.ning.billing.currency.plugin.api.CurrencyPluginApi;
import com.ning.billing.osgi.api.OSGIServiceRegistration;
import com.ning.billing.util.config.CurrencyConfig;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class CurrencyModule extends AbstractModule {


    protected ConfigSource configSource;

    public CurrencyModule(ConfigSource configSource) {
        this.configSource = configSource;
    }

    @Override
    protected void configure() {

        final ConfigurationObjectFactory factory = new ConfigurationObjectFactory(configSource);
        final CurrencyConfig currencyConfig = factory.build(CurrencyConfig.class);
        bind(CurrencyConfig.class).toInstance(currencyConfig);

        bind(new TypeLiteral<OSGIServiceRegistration<CurrencyPluginApi>>() {}).toProvider(DefaultCurrencyProviderPluginRegistryProvider.class).asEagerSingleton();

        bind(CurrencyConversionApi.class).to(DefaultCurrencyConversionApi.class).asEagerSingleton();
        bind(CurrencyService.class).to(DefaultCurrencyService.class).asEagerSingleton();
    }
}
