package tech.tablesaw.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberPredicates;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteIndexTest {

    private ByteIndex index;
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
        index = new ByteIndex(table.booleanColumn("byteTypeIndex"));
    }


    /**
     * Purpose: verify get method operated properly
     * Given:
     *          value
     *   1)     (byte) 0
     *   2)     (byte) 1
     *
     * When: call get
     *
     * Then:
     *         Selection
     *   1)     [0,3,5]
     *   2)     [1,2,4,6,7,8]
     */
    @Test
    void testGet() {
        Selection fromCol0 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isEqualTo(0));
        Selection fromIdx0 = index.get((byte)0);
        Selection fromCol1 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isEqualTo(1));
        Selection fromIdx1 = index.get((byte)1);

        assertEquals(fromCol0, fromIdx0);
        assertEquals(fromCol1, fromIdx1);
    }

    /**
     * Purpose: verify greaterThan method operated properly
     * Given:
     *          value
     *   1)     (byte) 0
     *
     * When: call greaterThan
     *
     * Then:
     *         Selection
     *   1)     [1,2,4,6,7,8]
     */
    @Test
    void testGreaterThan() {
        Selection fromCol = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isGreaterThan(0));
        Selection fromIdx = index.greaterThan((byte)0);
        assertEquals(fromCol, fromIdx);

    }

    /**
     * Purpose: verify lessThan method operated properly
     * Given:
     *          value
     *   1)     (byte) 1
     *
     * When: call lessThan
     *
     * Then:
     *         Selection
     *   1)     [0,3,5]
     */
    @Test
    void testLessThan() {
        Selection fromCol = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isLessThan(1));
        Selection fromIdx = index.lessThan((byte)1);
        assertEquals(fromCol, fromIdx);

    }

    /**
     * Purpose: verify atLeast method operated properly
     * Given:
     *          value
     *   1)     (byte) 0
     *   2)     (byte) 1
     *
     * When: call atLeast
     *
     * Then:
     *         Selection
     *   1)     [0,1,2,3,4,5,6,7,8]
     *   2)     [1,2,4,6,7,8]
     *
     */
    @Test
    void atLeast() {

        Selection fromCol0 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isGreaterThanOrEqualTo(0));
        Selection fromIdx0 = index.atLeast((byte)0);
        Selection fromCol1 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isGreaterThanOrEqualTo(1));
        Selection fromIdx1 = index.atLeast((byte)1);

        assertEquals(fromCol0, fromIdx0);
        assertEquals(fromCol1, fromIdx1);

    }


    /**
     * Purpose: verify atMost method operated properly
     * Given:
     *          value
     *   1)     (byte) 0
     *   2)     (byte) 1
     *
     * When: call atMost
     *
     * Then:
     *         Selection
     *   1)     [0,3,6]
     *   2)     [0,1,2,3,4,5,6,7,8]
     *
     */
    @Test
    void atMost() {
        Selection fromCol0 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isLessThanOrEqualTo(0));
        Selection fromIdx0 = index.atMost((byte)0);
        Selection fromCol1 = table.numberColumn("byteTypeIndex").eval(NumberPredicates.isLessThanOrEqualTo(1));
        Selection fromIdx1 = index.atMost((byte)1);

        assertEquals(fromCol0, fromIdx0);
        assertEquals(fromCol1, fromIdx1);

    }

}
