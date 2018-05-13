import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.leonvsg.education.timesheet.services.Utils.requestParamsToString;

public class UtilsTest {

    private HttpServletRequest request;
    private Map<String, String[]> map;
    private String rightString = "{ anotherKey=value, anotherKey=anotherValue, key=value, }";
    private String rightNullString = "{}";

    @Before
    public void before(){
        request = mock(HttpServletRequest.class);
        map = new HashMap<>();
        map.put("key", new String[]{"value"});
        map.put("anotherKey", new String[]{"value", "anotherValue"});
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    public void requestParamsToStringTest(){
        assertEquals(rightString, requestParamsToString(request));
        assertEquals(rightNullString, requestParamsToString(null));
    }
}
