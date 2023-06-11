package tech.tablesaw.selection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class BitSetBackedSelectionTest {
    /**
     * Purpose : To test the toBitmap() method when otherSelection is the kind of type which is included in BitSetBackedSelection.
     * Given : otherSelection is from BitSetBackedSelection plus some other elements.
     * When : The toBitmap() method is called with otherSelection as the input
     * Then : - The clone is copied from the bitmap which is from otherSelection, and it is returned
     - The bitmap which is returned should be same as otherSelection's
     - The newly returned bitmap should have a separate instance from otherSelection's
     */
    @Test
    public void testToBitmap_WithBitSetBackedSelection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Purpose : To test the toBitmap() method when otherSelection is the kind of type which is included in BitSetBackedSelection.

        //Given : otherSelection is from BitSetBackedSelection plus some other elements.
        BitSetBackedSelection otherSelection = new BitSetBackedSelection();
        otherSelection.add(1);
        otherSelection.add(3);

        // When : The toBitmap() method is called with otherSelection as the input
        Method toBitmap = BitSetBackedSelection.class.getDeclaredMethod("toBitmap", Selection.class);
        toBitmap.setAccessible(true);
        BitSet result = (BitSet) toBitmap.invoke(otherSelection, otherSelection);

        //Assert
        assertEquals(otherSelection.bitSet(), result);
    }
}