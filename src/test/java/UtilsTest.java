import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.leonvsg.education.timesheet.services.Utils.*;

public class UtilsTest {

    private HttpServletRequest request;
    private Map<String, String[]> map;
    private String rightString;
    private String rightNullString;
    private String[] emptyParams;
    private String[] notEmptyParams;
    private String[] nullParams;
    private String[] notNullParams;

    @Before
    public void before(){
        request = mock(HttpServletRequest.class);
        map = new HashMap<>();
        map.put("key", new String[]{"value"});
        map.put("anotherKey", new String[]{"value", "anotherValue"});
        rightString = "{ anotherKey=value, anotherKey=anotherValue, key=value, }";
        rightNullString = "{}";
        when(request.getParameterMap()).thenReturn(map);
        emptyParams = new String[]{null, ""};
        notEmptyParams = new String[]{"param", "param"};
        nullParams = new String[]{null, null,};
        notNullParams = new String[]{"", "param"};
    }

    @Test
    public void requestParamsToStringTest(){
        assertEquals(rightString, requestParamsToString(request));
        assertEquals(rightNullString, requestParamsToString(null));
    }

    @Test
    public void isEmptyParamsTest() {
        assertTrue(isEmptyParams(emptyParams));
        assertFalse(isEmptyParams(notEmptyParams));
    }

    @Test
    public void isNotEmptyParamsTest() {
        assertTrue(isNotEmptyParams(notEmptyParams));
        assertFalse(isNotEmptyParams(emptyParams));
    }

    @Test
    public void isNullParamsTest() {
        assertTrue(isNullParams(nullParams));
        assertFalse(isNullParams(notNullParams));
    }

    @Test
    public void isNotNullParamsTest() {
        assertTrue(isNotNullParams(notNullParams));
        assertFalse(isNotNullParams(nullParams));
    }
}
