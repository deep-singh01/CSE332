import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PowModTests {
    @Test
    public void testPowModSequential() {
        int[] arr = {1, 7, 4, 3, 6};
        int pow = 6;
        int mod = 5000;
        int[] expected = {1, 2649, 4096, 729, 1656};

        PowMod.sequentialPowMod(arr, 0, arr.length, pow, mod);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testPowModParallel() {
        int[] arr = {1, 7, 4, 3, 6};
        int cutoff = 1; // Putting the cutoff as 1 makes it FULLY parallel
        int pow = 6;
        int mod = 5000;
        int[] expected = {1, 2649, 4096, 729, 1656};

        PowMod.parallelPowMod(arr, cutoff, pow, mod);
        assertArrayEquals(expected, arr);
    }
}