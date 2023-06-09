package tech.tablesaw.columns.strings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.tablesaw.columns.AbstractColumn.DEFAULT_ARRAY_SIZE;

class TextualStringDataTest {

    /**
     * Purpose: verify all static creation
     *
     * Given:
     * * initial array and list { a, b, c, d, e }
     *
     * When:
     * * 1) default creation
     * * 2) varargs creation
     * * 3) collection creation
     * * 4) creation by default size (filled with empty string)
     * * 5) stream creation
     *
     * Then:
     * * 1) size is 0
     * * 2) equal to the initial value
     * * 3) equal to the initial value
     * * 4) filled with empty string
     * * 5) equal to the initial value
     */
    @Test
    public void creationTest() {
        // given
        String[] initArray = {"a", "b", "c", "d", "e"};
        List<String> initList = List.of(initArray);

        // when
        TextualStringData creation1 = TextualStringData.create();
        TextualStringData creation2 = TextualStringData.create(initArray);
        TextualStringData creation3 = TextualStringData.create(initList);
        TextualStringData creation4 = TextualStringData.create(5);
        TextualStringData creation5 = TextualStringData.create(initList.stream());

        // then
        assertEquals(0, creation1.size());

        assertIterableEquals(initList, creation2);
        assertIterableEquals(initList, creation3);
        assertIterableEquals(initList, creation5);

        assertEquals(5, creation4.size());
        assertTrue(creation4.values.stream().allMatch(StringColumnType.missingValueIndicator()::equals));
    }
}