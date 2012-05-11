// Modified or written by Luca Marrocco for inclusion with hoptoad.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package hoptoad;

import ch.qos.logback.classic.spi.IThrowableProxy;
import org.slf4j.MDC;

import java.util.Map;

public class HoptoadNoticeBuilderUsingFilteredSystemProperties extends HoptoadNoticeBuilder {

	public HoptoadNoticeBuilderUsingFilteredSystemProperties(final String apiKey, final Backtrace backtraceBuilder, final IThrowableProxy throwable, final String env) {
		super(apiKey, backtraceBuilder, throwable, env);

		environment(System.getProperties());

		addMDCToSession();

		standardEnvironmentFilters();

		ec2EnvironmentFilters();
	}

	private void addMDCToSession() {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = MDC.getCopyOfContextMap();

		if (map != null) {
			addSessionKey(":key", Integer.toString(map.hashCode()));
			addSessionKey(":data", map);
		}
	}
}
