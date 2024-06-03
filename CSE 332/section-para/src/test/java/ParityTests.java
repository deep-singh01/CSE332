import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParityTests {
    @Test
    public void testParitySequentialTrue() {
        int[] arr = new int[]{1, 7, 4, 3, 6};
        assertTrue(Parity.sequentialParityTask(arr, 0, arr.length));
    }

    @Test
    public void testParitySequentialFalse() {
        int[] arr = new int[]{6, 5, 4, 3, 2, 1};
        assertFalse(Parity.sequentialParityTask(arr, 0, arr.length));
    }

    @Test
    public void testParityParallelTrue() {
        int[] arr = new int[]{1, 7, 4, 3, 6};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        assertTrue(Parity.parallelParityTask(arr, cutoff));
    }

    @Test
    public void testParityParallelFalse() {
        int[] arr = new int[]{6, 5, 4, 3, 2, 1};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        assertFalse(Parity.parallelParityTask(arr, cutoff));
    }
}