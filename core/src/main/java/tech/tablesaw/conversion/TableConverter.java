package tech.tablesaw.conversion;

import com.google.common.base.Preconditions;
import java.util.List;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.table.Relation;

/**
 * A tool for converting a Table or other Relation to a two-dimensional array of numeric primitives.
 */
public class TableConverter {

  private final Relation table;

  public TableConverter(Relation table) {
    this.table = table;
  }

  public double[][] doubleMatrix() {
    return doubleMatrix(table.numericColumns());
  }

  public double[][] doubleMatrix(int... columnIndicies) {
    return doubleMatrix(table.numericColumns(columnIndicies));
  }

  public double[][] doubleMatrix(String... columnNames) {
    return doubleMatrix(table.numericColumns(columnNames));
  }

  public float[][] floatMatrix() {
    return floatMatrix(table.numericColumns());
  }

  public float[][] floatMatrix(int... columnIndicies) {
    return floatMatrix(table.numericColumns(columnIndicies));
  }

  public float[][] floatMatrix(String... columnNames) {
    return floatMatrix(table.numericColumns(columnNames));
  }

  public int[][] intMatrix() {
    return intMatrix(table.numericColumns());
  }

  public int[][] intMatrix(int... columnIndicies) {
    return intMatrix(table.numericColumns(columnIndicies));
  }

  public int[][] intMatrix(String... columnNames) {
    return intMatrix(table.numericColumns(columnNames));
  }

  private static double[][] doubleMatrix(List<NumericColumn<?>> numberColumns) {
    Preconditions.checkArgument(!numberColumns.isEmpty());
    int obs = numberColumns.get(0).size();
    double[][] allVals = new double[obs][numberColumns.size()];

    for (int r = 0; r < obs; r++) {
      for (int c = 0; c < numberColumns.size(); c++) {
        allVals[r][c] = numberColumns.get(c).getDouble(r);
      }
    }
    return allVals;
  }

  private static float[][] floatMatrix(List<NumericColumn<?>> numberColumns) {
    Preconditions.checkArgument(!numberColumns.isEmpty());
    int obs = numberColumns.get(0).size();
    float[][] allVals = new float[obs][numberColumns.size()];

    for (int r = 0; r < obs; r++) {
      for (int c = 0; c < numberColumns.size(); c++) {
        allVals[r][c] = (float) numberColumns.get(c).getDouble(r);
      }
    }
    return allVals;
  }

  private static int[][] intMatrix(List<NumericColumn<?>> numberColumns) {
    Preconditions.checkArgument(!numberColumns.isEmpty());
    int obs = numberColumns.get(0).size();
    int[][] allVals = new int[obs][numberColumns.size()];

    for (int r = 0; r < obs; r++) {
      for (int c = 0; c < numberColumns.size(); c++) {
        allVals[r][c] = (int) numberColumns.get(c).getDouble(r);
      }
    }
    /**
    * Purpose: Test the conversion of List<NumericColumn<?>> to double[][] in doubleMatrix() method
    * Given: Sample NumericColumns
    * When: Calling doubleMatrix() method with the sample NumericColumns
    * Then: Check the conversion of List<NumericColumn<?>> to double[][]
    **/
     @Test
    public void testDoubleMatrix() {
        // Purpose: Test the conversion of List<NumericColumn<?>> to double[][] in doubleMatrix() method

        // Given: Sample NumericColumns
        NumericColumn<Integer> column1 = new NumericColumn<>(new Integer[]{1, 2, 3});
        NumericColumn<Double> column2 = new NumericColumn<>(new Double[]{4.5, 6.7, 8.9});
        NumericColumn<Float> column3 = new NumericColumn<>(new Float[]{0.1f, 0.2f, 0.3f});
        List<NumericColumn<?>> numberColumns = new ArrayList<>();
        numberColumns.add(column1);
        numberColumns.add(column2);
        numberColumns.add(column3);

        // When: Calling doubleMatrix() method with the sample NumericColumns
        double[][] result = doubleMatrix(numberColumns);

        // Then: Check the conversion of List<NumericColumn<?>> to double[][]
        Assert.assertEquals(3, result.length); // Number of rows should be 3
        Assert.assertEquals(3, result[0].length); // Number of columns should be 3

        // Check the values in the double[][] array
        Assert.assertEquals(1.0, result[0][0], 0.001);
        Assert.assertEquals(4.5, result[0][1], 0.001);
        Assert.assertEquals(0.1, result[0][2], 0.001);

        Assert.assertEquals(2.0, result[1][0], 0.001);
        Assert.assertEquals(6.7, result[1][1], 0.001);
        Assert.assertEquals(0.2, result[1][2], 0.001);

        Assert.assertEquals(3.0, result[2][0], 0.001);
        Assert.assertEquals(8.9, result[2][1], 0.001);
        Assert.assertEquals(0.3, result[2][2], 0.001);
    }

    private static double[][] doubleMatrix(List<NumericColumn<?>> numberColumns) {
        Preconditions.checkArgument(!numberColumns.isEmpty());
        int obs = numberColumns.get(0).size();
        double[][] allVals = new double[obs][numberColumns.size()];

        for (int r = 0; r < obs; r++) {
            for (int c = 0; c < numberColumns.size(); c++) {
                allVals[r][c] = numberColumns.get(c).getDouble(r);
            }
        }
    return allVals;
  }
}
