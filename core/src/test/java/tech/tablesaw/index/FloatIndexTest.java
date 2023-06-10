package tech.tablesaw.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberPredicates;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloatIndexTest {
    private FloatIndex index;
    private Table table;


    /**
     * table setting
     */
    @BeforeEach
    public void setUp() throws Exception {

        table =
                Table.read()
                        .csv(
                                CsvReadOptions.builder("../data/index_test.csv")
                                        .columnTypes(
                                                new ColumnType[] {
                                                        ColumnType.BOOLEAN,
                                                        ColumnType.DOUBLE,
                                                        ColumnType.FLOAT,
                                                        ColumnType.INTEGER,
                                                        ColumnType.LONG,
                                                        ColumnType.SHORT,
                                                        ColumnType.STRING
                                                }));
        index = new FloatIndex(table.floatColumn("floatTypeIndex"));
    }


    /**
     * Purpose: verify get method operated properly
     * Given:
     *          value
     *   1)     (float) 2.2
     *
     * When: call get
     *
     * Then:
     *         Selection
     *   1)     [0,3]
     */
    @Test
    void testGet() {
        Selection fromCol= table.numberColumn("floatTypeIndex").eval(NumberPredicates.isEqualTo((float)2.2));
        Selection fromIdx = index.get((float) 2.2);

        assertEquals(fromCol, fromIdx);
    }
    /**
     * Purpose: verify greaterThan method operated properly
     * Given:
     *          value
     *   1)     (float) 2.2
     *
     * When: call greaterThan
     *
     * Then:
     *         Selection
     *   1)     [1,2,6,7]
     */

    @Test
    void testGreaterThan() {

        Selection fromCol= table.numberColumn("floatTypeIndex").eval(NumberPredicates.isGreaterThan((float)2.2));
        Selection fromIdx = index.greaterThan((float) 2.2);

        assertEquals(fromCol, fromIdx);
    }

    /**
     * Purpose: verify lessThan method operated properly
     * Given:
     *          value
     *   1)     (float) 2.2
     *
     * When: call lessThan
     *
     * Then:
     *         Selection
     *   1)     [4,5,8]
     */
    @Test
    void testLessThan() {

        Selection fromCol= table.numberColumn("floatTypeIndex").eval(NumberPredicates.isLessThan((float)2.2));
        Selection fromIdx = index.lessThan((float) 2.2);

        assertEquals(fromCol, fromIdx);
    }

    /**
     * Purpose: verify atLeast method operated properly
     * Given:
     *          value
     *   1)     (float) 2.2
     *
     * When: call atLeast
     *
     * Then:
     *         Selection
     *   1)     [0,1,2,3,6,7]
     *
     */
    @Test
    void atLeast() {

        Selection fromCol= table.numberColumn("floatTypeIndex").eval(NumberPredicates.isGreaterThanOrEqualTo((float)2.2));
        Selection fromIdx = index.atLeast((float) 2.2);

        assertEquals(fromCol, fromIdx);
    }


    /**
     * Purpose: verify atMost method operated properly
     * Given:
     *          value
     *   1)     (float) 2.2
     *
     * When: call atMost
     *
     * Then:
     *         Selection
     *   1)     [0,3,4,5,8]
     *
     */
    @Test
    void atMost() {

        Selection fromCol= table.numberColumn("floatTypeIndex").eval(NumberPredicates.isLessThanOrEqualTo((float)2.2));
        Selection fromIdx = index.atMost((float) 2.2);
        assertEquals(fromCol, fromIdx);
    }
}
