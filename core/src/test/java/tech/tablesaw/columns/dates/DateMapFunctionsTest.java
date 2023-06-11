/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.tablesaw.columns.dates;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TimeColumn;

public class DateMapFunctionsTest {

  private DateColumn column1;

  @BeforeEach
  public void setUp() {
    Table table = Table.create("Test");
    column1 = DateColumn.create("Game date");
    table.addColumns(column1);
  }

  @Test
  public void testAtTimeColumn() {
    column1.appendCell("2023-06-10");
    column1.appendCell("06/10/2023");
//    column1.appendCell("10-jan-2023");
//    column1.appendCell("10-feb-2023");
//    column1.appendCell("10-mar-2023");
//    column1.appendCell("10-apr-2023");
//    column1.appendCell("10-may-2023");
//    column1.appendCell("10-jun-2023");
//    column1.appendCell("10-jul-2023");
//    column1.appendCell("10-aug-2023");
//    column1.appendCell("10-sep-2023");
//    column1.appendCell("10-oct-2023");
//    column1.appendCell("10-nov-2023");
//    column1.appendCell("10-dec-2023");
//    column1.appendCell("10-JAN-2023");
//    column1.appendCell("10-FEB-2023");
//    column1.appendCell("10-MAR-2023");
//    column1.appendCell("10-APR-2023");
//    column1.appendCell("10-MAY-2023");
//    column1.appendCell("10-JUN-2023");
//    column1.appendCell("10-JUL-2023");
//    column1.appendCell("10-AUG-2023");
//    column1.appendCell("10-SEP-2023");
//    column1.appendCell("10-OCT-2023");
//    column1.appendCell("10-NOV-2023");
//    column1.appendCell("10-DEC-2023");

    TimeColumn timeColumn = TimeColumn.create("times");
    timeColumn.append(LocalTime.NOON);
    timeColumn.append(LocalTime.NOON);
    timeColumn.append(LocalTime.NOON);
    timeColumn.append(LocalTime.NOON);
    DateTimeColumn dateTimes = column1.atTime(timeColumn);
    assertNotNull(dateTimes);
    assertTrue(dateTimes.get(0).toLocalTime().equals(LocalTime.NOON));
  }

  @Test
  public void testAtTime() {
    column1.appendCell("2023-06-10");
    column1.appendCell("06/10/2023");
//    column1.appendCell("10-jan-2023");
//    column1.appendCell("10-feb-2023");
//    column1.appendCell("10-mar-2023");
//    column1.appendCell("10-apr-2023");
//    column1.appendCell("10-may-2023");
//    column1.appendCell("10-jun-2023");
//    column1.appendCell("10-jul-2023");
//    column1.appendCell("10-aug-2023");
//    column1.appendCell("10-sep-2023");
//    column1.appendCell("10-oct-2023");
//    column1.appendCell("10-nov-2023");
//    column1.appendCell("10-dec-2023");
//    column1.appendCell("10-JAN-2023");
//    column1.appendCell("10-FEB-2023");
//    column1.appendCell("10-MAR-2023");
//    column1.appendCell("10-APR-2023");
//    column1.appendCell("10-MAY-2023");
//    column1.appendCell("10-JUN-2023");
//    column1.appendCell("10-JUL-2023");
//    column1.appendCell("10-AUG-2023");
//    column1.appendCell("10-SEP-2023");
//    column1.appendCell("10-OCT-2023");
//    column1.appendCell("10-NOV-2023");
//    column1.appendCell("10-DEC-2023");;

    DateTimeColumn dateTimes = column1.atTime(LocalTime.NOON);
    assertNotNull(dateTimes);
    assertTrue(dateTimes.get(0).toLocalTime().equals(LocalTime.NOON));
  }

}
