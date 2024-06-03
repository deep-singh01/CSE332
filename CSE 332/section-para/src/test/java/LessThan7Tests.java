import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LessThan7Tests {

    @Test
    public void testSequentialLessThan7() {
        int[] arr = new int[]{21, 7, 6, 8, 17, 1, 7, 7, 1, 1, 7};
        assertEquals(4, LessThan7.sequentialLessThan7(arr, 0, arr.length));
    }

    @Test
    public void testParallelLessThan7() {
        int[] arr = new int[]{21, 7, 6, 8, 17, 1, 7, 7, 1, 1, 7};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        assertEquals(4, LessThan7.parallelLessThan7(arr, cutoff));
    }
}