// Modified or written by Luca Marrocco for inclusion with hoptoad.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package hoptoad;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.AppenderBase;

public class HoptoadAppender extends AppenderBase<ILoggingEvent> {
    private final HoptoadNotifier hoptoadNotifier = new HoptoadNotifier();

    private String apiKey;

    private String env;

    private boolean enabled;

    private Backtrace backtrace = new QuietRubyBacktrace();

    private String endpoint = "airbrake.io/notifier_api/v2/notices";

    private boolean secure = false;

    public HoptoadAppender(final String apiKey) {
        setApi_key(apiKey);
    }

    public HoptoadAppender(final String apiKey, final Backtrace backtrace) {
        setApi_key(apiKey);
        setBacktrace(backtrace);
    }

    @Override
    protected void append(final ILoggingEvent loggingEvent) {
        if (!enabled) return;

        if (thereIsThrowableIn(loggingEvent)) {
            notifyThrowableIn(loggingEvent);
        }
    }

    @Override
    public void start() {
        if (apiKey == null) {
            addError("API key not set for the appender named [" + name +"].");
        }
        if (env == null) {
            addError("Environment not set for the appender named [" + name +"].");
        }
        super.start();
    }

    @Override
    public void stop() {}

	public HoptoadNotice newNoticeFor(final IThrowableProxy throwable) {
		return new HoptoadNoticeBuilderUsingFilteredSystemProperties(apiKey, backtrace, throwable, env).newNotice();
	}

	private int notifyThrowableIn(final ILoggingEvent loggingEvent) {
		return hoptoadNotifier.notify(newNoticeFor(throwable(loggingEvent)), endpoint, secure);
	}

    public void setApi_key(final String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBacktrace(final Backtrace backtrace) {
        this.backtrace = backtrace;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setEnv(final String env) {
        this.env = env;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

	private boolean thereIsThrowableIn(final ILoggingEvent loggingEvent) {
		return loggingEvent.getThrowableProxy() != null;
	}

	private IThrowableProxy throwable(final ILoggingEvent loggingEvent) {
		return loggingEvent.getThrowableProxy();
	}
}
