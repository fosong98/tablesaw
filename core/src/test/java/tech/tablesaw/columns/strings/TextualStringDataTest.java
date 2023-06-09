package tech.tablesaw.columns.strings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import tech.tablesaw.api.StringColumn;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * Purpose: Verify addition
     *
     * Given:
     * * initial List and column
     *
     * When:
     * * 1) call append method, argument is "a"
     * * 2) call append method, argument is column
     * * 3) call addAll method
     * * 4) call set method (change the value of index 1 to "z")
     * * 5) call setMissing method (change the value of index 1 to "")
     * * 6) call set method (Change all values other than "c" to "c")
     * * 7) call appendObj (with normal argument)
     * * 8) call appendObj (with no string argument)
     *
     * Then:
     * * 1 ~ 3, 7) same as the initial value
     * * 4) value of index 1 is "z"
     * * 5) value of index 1 is ""
     * * 6) all values are "c"
     * * 8) throws IllegalArgumentException
     */
    @Test
    public void additionTest() {
        // given
        TextualStringData data = TextualStringData.create();
        StringColumn column = StringColumn.create("column");
        List<String> initList = List.of("a", "b", "c", "d", "e");
        List<String> expectedOne = List.of("a");
        column.addAll(initList);

        // when
        Runnable appendOne = () -> data.append("a");
        Runnable appendColumn = () -> data.append(column);
        Runnable appendList = () -> data.addAll(initList);
        Runnable setValue = () -> data.set(1, "z");
        Runnable setMissing = () -> data.setMissing(1);
        Runnable setSelection = () -> data.set(column.isNotEqualTo("c"), "c");
        Runnable appendObj = () -> data.appendObj("a");
        Runnable appendObjNotString = () -> data.appendObj(new Object());
        Runnable clear = data::clear;

        // then
        // 1)
        appendOne.run();
        assertIterableEquals(expectedOne, data);
        clear.run();

        // 2)
        appendColumn.run();
        assertIterableEquals(initList, data);
        clear.run();

        // 3)
        appendList.run();
        assertIterableEquals(initList, data);

        // 4)
        setValue.run();
        assertEquals("z", data.get(1));

        // 5)
        setMissing.run();
        assertEquals(StringColumnType.missingValueIndicator(), data.get(1));

        // 6)
        setSelection.run();
        assertTrue(data.values.stream().allMatch("c"::equals));
        clear.run();

        // 7)
        appendObj.run();
        assertIterableEquals(expectedOne, data);

        // 8)
        assertThrows(IllegalArgumentException.class, appendObjNotString::run);
    }
}