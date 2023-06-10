package tech.tablesaw.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.DateAndTimePredicates;
import tech.tablesaw.columns.numbers.NumberPredicates;
import tech.tablesaw.columns.strings.StringPredicates;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringIndexTest {
    private StringIndex index;
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
        index = new StringIndex(table.stringColumn("stringTypeIndex"));
    }


    /**
     * Purpose: verify get method operated properly
     * Given:
     *          value
     *   1)     (string) cc
     *
     * When: call get
     *
     * Then:
     *         Selection
     *   1)     [2]
     */
    @Test
    void testGet() {

        Selection fromIdx = index.get((String)"cc");
        assertEquals(fromIdx.get(0),2);

    }

}
