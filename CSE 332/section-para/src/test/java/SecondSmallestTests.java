import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecondSmallestTests {
    @Test
    public void testSecondSmallestSequential1() {
        int[] arr = new int[]{1, 7, 4, 3, 6};
        assertEquals(3, SecondSmallest.sequentialSecondSmallest(arr, 0, arr.length).secondSmallest);
    }

    @Test
    public void testSecondSmallestSequential2() {
        int[] arr = new int[]{6, 1, 4, 3, 5, 2};
        assertEquals(2, SecondSmallest.sequentialSecondSmallest(arr, 0, arr.length).secondSmallest);
    }

    @Test
    public void testSecondSmallestParallel1() {
        int[] arr = new int[]{1, 7, 4, 3, 6};
        assertEquals(3, SecondSmallest.parallelSecondSmallest(arr, 1));
    }

    @Test
    public void testSecondSmallestParallel2() {
        int[] arr = new int[]{6, 1, 4, 3, 5, 2};
        assertEquals(2, SecondSmallest.parallelSecondSmallest(arr, 1));
    }
}
