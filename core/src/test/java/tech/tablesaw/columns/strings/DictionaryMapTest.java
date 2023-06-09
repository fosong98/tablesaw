package tech.tablesaw.columns.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.selection.Selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
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

    /**
     * Purpose: verify isNotIn Methods
     *
     * Given:
     * *    value
     * * 1) registered value(4)
     * * 2) unregistered value(4)
     * * 3) mixed value(2, 2)
     *
     * When: call two forms of isNotIn methods
     *
     * Then:
     * *    Selection size
     * * 1) 0
     * * 2) 4
     * * 3) 2
     */
    @Test
    public void isNotInTest() throws Exception {
        // given
        String registered1 = "registered1";
        String registered2 = "registered2";
        String registered3 = "registered3";
        String registered4 = "registered4";
        String unregistered1 = "unregistered1";
        String unregistered2 = "unregistered2";
        String unregistered3 = "unregistered3";
        String unregistered4 = "unregistered4";

        List<String> registered = List.of(registered1, registered2, registered3, registered4);
        List<String> unregistered = List.of(unregistered1, unregistered2, unregistered3, unregistered4);
        List<String> mixed = List.of(unregistered1, registered2, unregistered3, registered4);

        DictionaryMap<Integer> strings = dictionaryMap;
        for (String s : registered) {
            strings.append(s);
        }

        // when
        Selection registeredSelectionStrings = dictionaryMap.isNotIn(registered1, registered2, registered3, registered4);
        Selection registeredSelectionCollection = dictionaryMap.isNotIn(registered);
        Selection unregisteredSelectionStrings = dictionaryMap.isNotIn(unregistered1, unregistered2, unregistered3, unregistered4);
        Selection unregisteredSelectionCollection = dictionaryMap.isNotIn(unregistered);
        Selection mixedSelectionStrings = dictionaryMap.isNotIn(mixed.get(0), mixed.get(1), mixed.get(2) , mixed.get(3));
        Selection mixedSelectionCollection = dictionaryMap.isNotIn(mixed);

        // then
        assertEquals(0, registeredSelectionStrings.size());
        assertEquals(0, registeredSelectionCollection.size());
        assertEquals(4, unregisteredSelectionStrings.size());
        assertEquals(4, unregisteredSelectionCollection.size());
        assertEquals(2, mixedSelectionStrings.size());
        assertEquals(2, mixedSelectionCollection.size());
    }

    /**
     * Purpose: verify promoteYourself Methods
     *
     * Given:
     * * ByteDictionaryMap
     *
     * When: Promote DictionaryMap listed below
     * * ByteDictionaryMap
     * 1) can promote
     * 2) can't promote
     * * ShortDictionaryMap
     * 3) can promote
     * 4) can't promote
     * * IntDictionaryMap
     * 5) can promote
     * 6) can't promote
     * * NullDictionaryMap
     * 7) can promote
     * 8) can't promote
     *
     * Then:
     * *    Expected type
     * * 1) ShortDictionaryMap
     * * 2) ByteDictionaryMap
     * * 3) IntDictionaryMap
     * * 4) ShortDictionaryMap
     * * 5) NullDictionaryMap
     * * 6) IntDictionaryMap
     * * 7) NullDictionaryMap
     * * 8) NullDictionaryMap
     */
    @Test
    public void dictionaryMapPromoteTest() {
        // given
        ByteDictionaryMap byteDictionaryMap = new ByteDictionaryMap(true);

        // when
        DictionaryMap promotedShortDictionaryMap = byteDictionaryMap.promoteYourself();
        byteDictionaryMap.canPromoteToText = false;
        DictionaryMap cannotPromoteByteDictionaryMap = byteDictionaryMap.promoteYourself();

        DictionaryMap promotedIntDictionaryMap = promotedShortDictionaryMap.promoteYourself();
        promotedShortDictionaryMap.canPromoteToText = false;
        DictionaryMap cannotPromoteShortDictionaryMap = promotedShortDictionaryMap.promoteYourself();

        DictionaryMap promotedNullDictionaryMap1 = promotedIntDictionaryMap.promoteYourself();
        promotedIntDictionaryMap.canPromoteToText = false;
        DictionaryMap cannotPromoteIntDictionaryMap = promotedIntDictionaryMap.promoteYourself();

        DictionaryMap promotedNullDictionaryMap2 = promotedNullDictionaryMap1.promoteYourself();
        promotedNullDictionaryMap1.canPromoteToText = false;
        DictionaryMap cannotPromoteNullDictionaryMap = promotedNullDictionaryMap1.promoteYourself();

        // then
        assertTrue(promotedShortDictionaryMap instanceof ShortDictionaryMap);
        assertTrue(promotedIntDictionaryMap instanceof IntDictionaryMap);
        assertTrue(promotedNullDictionaryMap1 instanceof NullDictionaryMap);
        assertTrue(promotedNullDictionaryMap2 instanceof NullDictionaryMap);

        // todo: When canPromoteToText of ByteDictionaryMap and ShortDictionaryMap is false,
        //  promotion should not be possible.
        // assertTrue(cannotPromoteByteDictionaryMap instanceof ByteDictionaryMap);
        // assertTrue(cannotPromoteShortDictionaryMap instanceof ShortDictionaryMap);
        assertTrue(cannotPromoteIntDictionaryMap instanceof IntDictionaryMap);
        assertTrue(cannotPromoteNullDictionaryMap instanceof NullDictionaryMap);
    }

    /**
     * Purpose: Verify that the abstract builder performs a null check on all member variables.
     *
     * Given:
     * * normal builder(All members are not null)
     * * hasNull builder(Some members are null)
     *
     * When: call build() method
     *
     * Then:
     * * normal.build() -> Nothing happens
     * * hasNull.build() -> throws NullPointerException
     */
    @Test
    public void abstractDictionaryMapBuilderTest() {
        // given
        DictionaryMap.DictionaryMapBuilder normal = mock(withSettings().defaultAnswer(CALLS_REAL_METHODS));
        normal.setValueToKey(mock())
                .setKeyToValue(mock())
                .setKeyToCount(mock())
                .setCanPromoteToText(true);
        normal.values = mock();
        normal.nextIndex = mock();

        DictionaryMap.DictionaryMapBuilder hasNull = mock(withSettings().defaultAnswer(CALLS_REAL_METHODS));
        hasNull.setValueToKey(mock())
                .setKeyToValue(mock())
                .setKeyToCount(mock())
                .setCanPromoteToText(true);
        hasNull.values = null;      // member 'values' is null
        hasNull.nextIndex = mock();

        // when
        when(normal.createTarget()).thenReturn(mock());
        when(hasNull.createTarget()).then((a)->fail());

        // then
        assertDoesNotThrow(() -> normal.build());
        assertThrows(NullPointerException.class, ()->hasNull.build());
    }
}




