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

package tech.tablesaw.conversion;

import org.junit.jupiter.api.Test;
import tech.tablesaw.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableConverterTest {

  @Test
  public void asDoubleMatrix() {
    double[] array1 = {0, 1, 2};
    double[] array2 = {0, 1, 2};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    Table table = Table.create("test", c1, c2);

    double[][] expected = {{0.0, 0.0}, {1.0, 1.0}, {2.0, 2.0}};
    double[][] results = table.as().doubleMatrix();
    assertTrue(Arrays.deepEquals(expected, results));
  }

  @Test
  public void asDoubleMatrixColArgs() {
    double[] array1 = {0, 1, 1};
    double[] array2 = {0, 1, 2};
    double[] array3 = {0, 1, 3};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    DoubleColumn c3 = DoubleColumn.create("3", array3);
    Table table = Table.create("test", c1, c2, c3);

    double[][] expected = {{0.0, 0.0}, {1.0, 1.0}, {1.0, 3.0}};
    double[][] results = table.as().doubleMatrix("1", "3");
    assertTrue(Arrays.deepEquals(expected, results));
  }

  @Test
  public void asIntMatrix() {
    double[] array1 = {0, 1, 2};
    double[] array2 = {0, 1, 2};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    Table table = Table.create("test", c1, c2);

    int[][] expected = {{0, 0}, {1, 1}, {2, 2}};
    int[][] results = table.as().intMatrix();
    assertTrue(Arrays.deepEquals(expected, results));
  }

  @Test
  public void asIntMatrixColArgs() {
    double[] array1 = {0, 1, 1};
    double[] array2 = {0, 1, 2};
    double[] array3 = {0, 1, 3};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    DoubleColumn c3 = DoubleColumn.create("3", array3);
    Table table = Table.create("test", c1, c2, c3);

    int[][] expected = {{0, 0}, {1, 1}, {1, 3}};
    int[][] results = table.as().intMatrix("1", "3");
    assertTrue(Arrays.deepEquals(expected, results));
  }

  @Test
  public void asFloatMatrix() {
    double[] array1 = {0, 1, 2};
    double[] array2 = {0, 1, 2};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    Table table = Table.create("test", c1, c2);

    float[][] expected = {{0.0f, 0.0f}, {1.0f, 1.0f}, {2.0f, 2.0f}};
    float[][] results = table.as().floatMatrix();
    assertTrue(Arrays.deepEquals(expected, results));
  }

  @Test
  public void asFloatMatrixColArgs() {
    double[] array1 = {0, 1, 1};
    double[] array2 = {0, 1, 2};
    double[] array3 = {0, 1, 3};

    DoubleColumn c1 = DoubleColumn.create("1", array1);
    DoubleColumn c2 = DoubleColumn.create("2", array2);
    DoubleColumn c3 = DoubleColumn.create("3", array3);
    Table table = Table.create("test", c1, c2, c3);

    float[][] expected = {{0.0f, 0.0f}, {1.0f, 1.0f}, {1.0f, 3.0f}};
    float[][] results = table.as().floatMatrix("1", "3");
    assertTrue(Arrays.deepEquals(expected, results));
  }

  /**
   * Purpose: Test the conversion of List<NumericColumn<?>> to double[][] in doubleMatrix() method
   * Given: Sample NumericColumns
   * When: Calling doubleMatrix() method with the sample NumericColumns
   * Then: Check the conversion of List<NumericColumn<?>> to double[][]
   **/
  @Test
  public void testDoubleMatrix() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // Purpose: Test the conversion of List<NumericColumn<?>> to double[][] in doubleMatrix() method

    // Given: Sample NumericColumns
    NumericColumn<Integer> column1 = IntColumn.create("IntColumn", 1, 2, 3);
    NumericColumn<Double> column2 = DoubleColumn.create("DoubleColumn", 4.5, 6.7, 8.9);
    NumericColumn<Float> column3 = FloatColumn.create("FloatColumn", 0.1f, 0.2f, 0.3f);
    List<NumericColumn<?>> numberColumns = new ArrayList<>();
    numberColumns.add(column1);
    numberColumns.add(column2);
    numberColumns.add(column3);

    // When: Calling doubleMatrix() method with the sample NumericColumns
    Method doubleMatrix = TableConverter.class.getDeclaredMethod("doubleMatrix", List.class);
    doubleMatrix.setAccessible(true);
    double[][] result = (double[][]) doubleMatrix.invoke(new TableConverter(null), numberColumns);

    // Then: Check the conversion of List<NumericColumn<?>> to double[][]
    assertEquals(3, result.length); // Number of rows should be 3
    assertEquals(3, result[0].length); // Number of columns should be 3

    // Check the values in the double[][] array
    assertEquals(1.0, result[0][0], 0.001);
    assertEquals(4.5, result[0][1], 0.001);
    assertEquals(0.1, result[0][2], 0.001);

    assertEquals(2.0, result[1][0], 0.001);
    assertEquals(6.7, result[1][1], 0.001);
    assertEquals(0.2, result[1][2], 0.001);

    assertEquals(3.0, result[2][0], 0.001);
    assertEquals(8.9, result[2][1], 0.001);
    assertEquals(0.3, result[2][2], 0.001);
  }
}
