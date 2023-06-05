package tech.tablesaw.columns.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DictionaryMapTest {

    DictionaryMap<Integer> dictionaryMap;
    Map<Integer, Integer> keyToCount;
    Map<String, Integer> valueToKey;
    Map<Integer, String> keyToValue;
    List<Integer> values;
    static final int DEFAULT_MISSING_VALUE = Integer.MAX_VALUE;
    int id;

    @BeforeEach
    public void setUp() throws Exception {
        dictionaryMap = mock(withSettings().defaultAnswer(CALLS_REAL_METHODS));
        dictionaryMap.keyToCount = new HashMap<>();
        keyToCount = dictionaryMap.keyToCount;
        dictionaryMap.valueToKey = new HashMap<>();
        valueToKey = dictionaryMap.valueToKey;
        dictionaryMap.keyToValue = new HashMap<>();
        keyToValue = dictionaryMap.keyToValue;
        dictionaryMap.values = new ArrayList<>();
        id = 0;
        dictionaryMap.nextIndex = new AtomicReference<>(id);
        values = dictionaryMap.values;
        when(dictionaryMap.getValueId()).then((m)->{
            id++;
            return id;
        });
        when(dictionaryMap.getDefaultMissingValue()).thenReturn(DEFAULT_MISSING_VALUE);
    }

    /**
     * Purpose: verify append method is operated properly
     * * Select test cases that satisfy decision coverage
     *
     * Given:
     * *    value
     * * 1) null
     * * 2) "registered" (contains in keyMap)
     * * 3) "unregistered" (not contains in keyMap)
     *
     * When: append value to the dictionaryMap
     *
     * Then:
     * * keyToCount
     *  { INTEGER.MAX_INT: 1, 1: 1, 2: 1 }
     * * valueToKey
     *  { "": INTEGER.MAX_INT, "registered": 1, "unregistered": 2 }
     * * values.size() == 4
     */
    @Test
    public void appendTest() throws Exception {
        // given
        String case1 = null;
        String case2 = "registered";
        valueToKey.put("registered", 1);
        keyToValue.put(1, "registered");
        values.add(1);
        keyToCount.put(1, 1);
        id++;

        String case3 = "unregistered";

        // when
        dictionaryMap.append(case1);
        dictionaryMap.append(case2);
        dictionaryMap.append(case3);

        // then
        assertTrue(keyToCount.containsKey(Integer.MAX_VALUE));
        assertEquals(1, keyToCount.get(Integer.MAX_VALUE));
        assertTrue(keyToCount.containsKey(1));
        assertEquals(2, keyToCount.get(1));
        assertTrue(keyToCount.containsKey(2));
        assertEquals(1, keyToCount.get(2));

        assertTrue(valueToKey.containsKey(""));
        assertEquals(Integer.MAX_VALUE, valueToKey.get(""));
        assertTrue(valueToKey.containsKey("registered"));
        assertEquals(1, valueToKey.get("registered"));
        assertTrue(valueToKey.containsKey("unregistered"));
        assertEquals(2, valueToKey.get("unregistered"));

        assertEquals(4, values.size());
    }
}




