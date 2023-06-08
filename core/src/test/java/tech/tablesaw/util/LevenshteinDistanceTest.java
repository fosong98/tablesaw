/**
 * Purpose: Verify LimitedCompare method is operated properly
 * Given:
 * *    threshold   left        right
 * * 1) 7           "hippo"     "elephant"
 * * 2) 6           "elephant"  "hippo"
 * * 3) 7           "hippo"     "hippo"
 * * 4) 8           ""          "elephant"
 * * 5) 8           null        "elephant"
 * * 6) -1          "hippo"     "elephant"
 *
 * When: call LimitedCompare
 *
 * Then:
 **     result
 * * 1) 7
 * * 2) -1
 * * 3) 0
 * * 4) 8
 * * 5) IllegalArgumentException
 * * 6) IllegalArgumentException
 */

package tech.tablesaw.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinDistanceTest {
    @Test
    public void testLimitedCompare(){
        assertEquals(7, new LevenshteinDistance(7).apply("hippo", "elephant"));
        assertEquals(-1, new LevenshteinDistance(6).apply("elephant", "hippo"));
        assertEquals(0, new LevenshteinDistance(7).apply("hippo", "hippo"));
        assertEquals(8, new LevenshteinDistance(8).apply("", "elephant"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LevenshteinDistance(8).apply(null, "elephant");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LevenshteinDistance(-1).apply("hippo", "elephant");
        });
    }
}
