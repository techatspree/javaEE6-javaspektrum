package com.acme.youTask.common.testsupport;

import java.util.Arrays;

import org.jboss.logging.Logger;


/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 */
public abstract class DummyLogger extends Logger {

  private static final long serialVersionUID = 1L;

  public DummyLogger() {
    super("Dummy");
  }

  // Eclipse thinks DummyLogger is a CDI injectable class otherwise
  public static Logger createInstance() {
    return new DummyLogger() {
      private static final long serialVersionUID = 1L;
    };
  }

  @Override
  public boolean isEnabled(final Level level) {
    return true;
  }

  @Override
  protected void doLog(final Level level, final String loggerClassName, final Object message, final Object[] parameters,
      final Throwable thrown) {
    System.out.println(level + ":" + loggerClassName + ":" + message + ":" + (parameters != null ? Arrays.asList(parameters) : ""));
  }

  @Override
  protected void doLogf(final Level level, final String loggerClassName, final String format, final Object[] parameters,
      final Throwable thrown) {
    System.out.println(level + ":" + loggerClassName + ":" + (parameters != null ? String.format(format, parameters) : ""));
  }
}
