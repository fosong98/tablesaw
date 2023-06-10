package tech.tablesaw.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberPredicates;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortIndexTest {
    private ShortIndex index;
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
        index = new ShortIndex(table.shortColumn("shortTypeIndex"));
    }


    /**
     * Purpose: verify get method operated properly
     * Given:
     *          value
     *   1)     (short) 3
     *
     * When: call get
     *
     * Then:
     *         Selection
     *   1)     [4]
     */
    @Test
    void testGet() {
        Selection fromCol = table.numberColumn("shortTypeIndex").eval(NumberPredicates.isEqualTo((short)3));
        Selection fromIdx = index.get((short)3);

        assertEquals(fromCol, fromIdx);
    }

    /**
     * Purpose: verify greaterThan method operated properly
     * Given:
     *          value
     *   1)     (short) 3
     *
     * When: call greaterThan
     *
     * Then:
     *         Selection
     *   1)     [0,1,5,6,7,8]
     */
    @Test
    void testGreaterThan() {

        Selection fromCol = table.numberColumn("shortTypeIndex").eval(NumberPredicates.isGreaterThan((short)3));
        Selection fromIdx = index.greaterThan((short)3);

        assertEquals(fromCol, fromIdx);
    }

    /**
     * Purpose: verify lessThan method operated properly
     * Given:
     *          value
     *   1)     (short) 3
     *
     * When: call lessThan
     *
     * Then:
     *         Selection
     *   1)     [2,3]
     */
    @Test
    void testLessThan() {

        Selection fromCol = table.numberColumn("shortTypeIndex").eval(NumberPredicates.isLessThan((short)3));
        Selection fromIdx = index.lessThan((short)3);

        assertEquals(fromCol, fromIdx);
    }

    /**
     * Purpose: verify atLeast method operated properly
     * Given:
     *          value
     *   1)     (short) 3
     *
     * When: call atLeast
     *
     * Then:
     *         Selection
     *   1)     [0,1,4,5,6,7,8]
     *
     */
    @Test
    void atLeast() {

        Selection fromCol = table.numberColumn("shortTypeIndex").eval(NumberPredicates.isGreaterThanOrEqualTo((short)3));
        Selection fromIdx = index.atLeast((short)3);

        assertEquals(fromCol, fromIdx);
    }


    /**
     * Purpose: verify atMost method operated properly
     * Given:
     *          value
     *   1)     (short) 3
     *
     * When: call atMost
     *
     * Then:
     *         Selection
     *   1)     [2,3,4]
     *
     */
    @Test
    void atMost() {

        Selection fromCol = table.numberColumn("shortTypeIndex").eval(NumberPredicates.isLessThanOrEqualTo((short)3));
        Selection fromIdx = index.atMost((short)3);

        assertEquals(fromCol, fromIdx);
    }
}
