import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountStrsTests {
    @Test
    public void testCountStrsSequential1() {
        String[] arr = {"h", "ee", "llll", "llll", "oo", "llll"};
        assertEquals(1, CountStrs.sequentialCountStrs(arr, 0, arr.length, "h"));
    }

    @Test
    public void testCountStrsSequential2() {
        String[] arr = {"h", "ee", "llll", "llll", "oo", "llll"};
        assertEquals(1, CountStrs.sequentialCountStrs(arr, 0, arr.length, "h"));
    }

    @Test
    public void testCountStrsParallel1() {
        String[] arr = {"h", "ee", "llll", "llll", "oo", "llll"};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        assertEquals(3, CountStrs.parallelCountStrs(arr, cutoff, "llll"));
        assertEquals(1, CountStrs.parallelCountStrs(arr, cutoff, "h"));
    }

    @Test
    public void testCountStrsParallel2() {
        String[] arr = {"h", "ee", "llll", "llll", "oo", "llll"};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        assertEquals(1, CountStrs.parallelCountStrs(arr, cutoff, "h"));
    }
}