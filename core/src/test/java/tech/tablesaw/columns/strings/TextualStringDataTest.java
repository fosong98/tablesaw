package tech.tablesaw.columns.strings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

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
     * * 3) call appendMissing method
     * * 4) call addAll method
     * * 5) call set method (change the value of index 1 to "z")
     * * 6) call setMissing method (change the value of index 1 to "")
     * * 7) call set method (Change all values other than "c" to "c")
     * * 8) call appendObj (with normal argument)
     * * 9) call appendObj (with no string argument)
     *
     * Then:
     * * 1, 2, 4, 8) same as the initial value
     * * 3) has missing value
     * * 5) value of index 1 is "z"
     * * 6) value of index 1 is ""
     * * 7) all values are "c"
     * * 9) throws IllegalArgumentException
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
        Runnable appendMissing = () -> data.appendMissing();
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
        appendMissing.run();
        assertEquals(StringColumnType.missingValueIndicator(), data.get(0));
        clear.run();

        // 4)
        appendList.run();
        assertIterableEquals(initList, data);

        // 5)
        setValue.run();
        assertEquals("z", data.get(1));

        // 6)
        setMissing.run();
        assertEquals(StringColumnType.missingValueIndicator(), data.get(1));

        // 7)
        setSelection.run();
        assertTrue(data.values.stream().allMatch("c"::equals));
        clear.run();

        // 8)
        appendObj.run();
        assertIterableEquals(expectedOne, data);

        // 9)
        assertThrows(IllegalArgumentException.class, appendObjNotString::run);
    }

    /**
     * Purpose: Verify copy logic
     *
     * Given:
     * * initial list { a, b, c, d, e }
     *
     * When:
     * * 1) call emptyCopy method
     * * 2) call emptyCopy method with rowSize argument
     * * 3) call copy method
     *
     * Then:
     * * 1) data size is 0
     * * 2) data is equal to the argument and are all "".
     * * 3) same value as the original, but different object.
     */
    @Test
    public void copyTest() {
        // given
        List<String> initData = List.of("a", "b", "c", "d", "e");
        TextualStringData data = TextualStringData.create(initData);

        // when
        TextualStringData emptyCopy1 = data.emptyCopy();
        TextualStringData emptyCopy2 = data.emptyCopy(3);
        TextualStringData copy = data.copy();

        // then
        assertEquals(0, emptyCopy1.size());

        assertEquals(3, emptyCopy2.size());
        assertTrue(emptyCopy2.values.stream().allMatch(StringColumnType.missingValueIndicator()::equals));

        assertNotSame(copy, data);
        assertIterableEquals(initData, copy);
    }

    /**
     * Purpose: verify isIn and isNotIn method
     *
     * Given:
     * * initial data {"a", "b", "c", "d", "e"}
     * * query data {"a", "c", "e", "f"}
     *
     * When:
     * * 1) Call isIn method as array
     * * 2) Call isNotIn method as array
     * * 3) Call isIn method as list
     * * 4) Call isNotIn method as list
     *
     * Then:
     * * isIn: Return the index of values in the list
     * * isNotIn: Return the index of unlisted values
     */
    @Test
    public void queryTest() {
        // given
        List<String> initData = List.of("a", "b", "c", "d", "e");
        TextualStringData data = TextualStringData.create(initData);
        String[] queryStrings = {"a", "c", "e", "f"};
        List<Integer> expectedIsIn = List.of(0, 2, 4);
        List<Integer> expectedIsNotIn = List.of(1, 3);

        // when
        Selection isInArray = data.isIn(queryStrings);
        Selection isNotInArray = data.isNotIn(queryStrings);
        Selection isInList = data.isIn(List.of(queryStrings));
        Selection isNotInList = data.isNotIn(List.of(queryStrings));

        // then
        assertIterableEquals(expectedIsIn, isInArray);
        assertIterableEquals(expectedIsIn, isInList);
        assertIterableEquals(expectedIsNotIn, isNotInArray);
        assertIterableEquals(expectedIsNotIn, isNotInList);
    }

    /**
     * Purpose: Verify summary method
     *
     * Given:
     * * initial data with 2 missing values and 5 values
     * * {"", "a", "b", "", "c", "d", "e"}
     *
     * When:
     * * Call summary method
     *
     * Then:
     * * Return Table with the number of missing values(2) and the number of values(7).
     */
    @Test
    public void summaryTest() {
        // given
        List<String> initData = List.of("", "a", "b", "", "c", "d", "e");
        TextualStringData data = TextualStringData.create(initData);

        // when
        Table summary = data.summary();

        // then
        Row count = summary.row(0);
        Row missing = summary.row(1);

        assertEquals("7", count.getString("Value"));
        assertEquals("2", missing.getString("Value"));
    }

    /**
     * Purpose: Verify lag method to apply boundary value analysis
     *
     * Given:
     * * initial data {"a", "b", "c", "d", "e"}
     *
     * When:
     * * boundary 1. n >= 0
     * * 1) n = 0
     * * 2) n = -1
     * * boundary 2. i + n >= dataSize (initial i is 0)
     * * 3) n = dataSize
     * * 4) n = dataSize - 1
     * * boundary 3. i < dataSize (initial i is -n)
     * * 5) n = -dataSize
     * * 6) n = -(dataSize - 1)
     *
     * Then:
     * * 1) {"a", "b", "c", "d", "e"}
     * * 2) {"b", "c", "d", "e", ""}
     * * 3) {"", "", "", "", ""}
     * * 4) {"", "", "", "", "a"}
     * * 5) {"", "", "", "", ""}
     * * 6) {"e", "", "", "", ""}
     */
    @Test
    public void lagTest() {
        // given
        List<String> initData = List.of("a", "b", "c", "d", "e");
        TextualStringData data = TextualStringData.create(initData);

        List<String> case1 = initData;
        List<String> case2 = List.of("b", "c", "d", "e", "");
        List<String> case3 = List.of("", "", "", "", "");
        List<String> case4 = List.of("", "", "", "", "a");
        List<String> case5 = case3;
        List<String> case6 = List.of("e", "", "", "", "");

        // when
        TextualStringData lag1 = data.lag(0);
        TextualStringData lag2 = data.lag(-1);
        TextualStringData lag3 = data.lag(data.size());
        TextualStringData lag4 = data.lag(data.size() - 1);
        TextualStringData lag5 = data.lag(-1 * data.size());
        TextualStringData lag6 = data.lag(-1 * (data.size() - 1));

        // then
        assertIterableEquals(case1, lag1);
        assertIterableEquals(case2, lag2);
        assertIterableEquals(case3, lag3);
        assertIterableEquals(case4, lag4);
        assertIterableEquals(case5, lag5);
        assertIterableEquals(case6, lag6);
    }
}