package iaws.tblabsauzzya.cobar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-config.xml"})
public class TheatreSearchEndpointIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void getSalleListHandlerTest() throws Exception {
        Source requestPayload = new StringSource(
                "<MovieRequest xmlns='http://projectCobart/namespace/scheme'>" +
                        "<filmImdbId>tt0357111</filmImdbId>" +
                        "</MovieRequest>");
        Source responsePayload = new StringSource(
                "<SallesListResponse xmlns='http://projectCobart/namespace/scheme'>" +
                        "<Salle>//ajouter attribut//</Salle>" +
                        "</SallesListResponse>");

        mockClient.sendRequest(withPayload(requestPayload)).
                andExpect(payload(responsePayload));
    }
}
