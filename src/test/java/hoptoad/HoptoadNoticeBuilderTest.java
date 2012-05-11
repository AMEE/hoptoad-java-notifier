package hoptoad;

import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HoptoadNoticeBuilderTest {

	@Test
	public void testBuildNoticeErrorClass() {
		HoptoadNoticeBuilder builder = new HoptoadNoticeBuilder("apiKey", new ThrowableProxy(new RuntimeException("errorMessage")));
		HoptoadNotice notice = builder.newNotice();
		assertThat(notice.errorClass(), is(equalTo("java.lang.RuntimeException")));
	}

	@Test
	public void testErrorClass() {
		HoptoadNoticeBuilder builder = new HoptoadNoticeBuilder("apiKey", new ThrowableProxy(new RuntimeException("errorMessage")));
		assertTrue(builder.errorClassIs("java.lang.RuntimeException"));
	}
}
