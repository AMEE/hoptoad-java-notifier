package hoptoad;

import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.Before;
import org.junit.Test;

import static hoptoad.ApiKeys.TEST_API_KEY;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class NoticeApi2XmlBuilderTest {

	private String xml(HoptoadNotice notice) {
		NoticeApi2 noticeApi2 = new NoticeApi2(notice);
		return noticeApi2.toString();
	}

	private HoptoadNoticeBuilder productionNoticeBuilder;

	@Test
	public void testEscapesAngleBrackets() throws Exception {
		assertThat(xml(productionNoticeBuilder.newNotice()), containsString("&lt;blink&gt;production&lt;/blink&gt;"));
	}

	@Test
	public void testSendsRequest() throws Exception {
		HoptoadNoticeBuilder builder = new HoptoadNoticeBuilder(TEST_API_KEY, new ThrowableProxy(newThrowable())) {
			{
				setRequest("http://example.com", "carburetor");
			}
		};

		assertThat(xml(builder.newNotice()), containsString("<request><url>http://example.com</url>"));
	}

	@Test
	public void testSendsSessionKeyColorAndLights() throws Exception {
		HoptoadNoticeBuilder builder = new HoptoadNoticeBuilder(TEST_API_KEY, new ThrowableProxy(newThrowable())) {
			{
				setRequest("http://example.com", "carburetor");
				addSessionKey("lights", "<blink>");
				addSessionKey("color", "orange");
			}
		};

		assertThat(xml(builder.newNotice()), containsString("<session><var key=\"color\">orange</var>"));
		assertThat(xml(builder.newNotice()), containsString("<var key=\"lights\">&lt;blink&gt;</var></session>"));
	}

	private RuntimeException newThrowable() {
		return new RuntimeException("errorMessage");
	}

	@Before
	public void setUp() {
		productionNoticeBuilder = new HoptoadNoticeBuilder(TEST_API_KEY, new ThrowableProxy(newThrowable()), "<blink>production</blink>");
	}
}
