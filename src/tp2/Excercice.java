package tp2;
import java.util.*;

public class Excercice {

    // Exercice 1 — Longueur de la plus longue sous-suite strictement croissante (LIS)
    public static int lisLength(int[] a) {
        if (a.length == 0) return 0;
        ArrayList<Integer> tails = new ArrayList<>();
        for (int x : a) {
            int i = Collections.binarySearch(tails, x);
            if (i < 0) i = -(i + 1);
            if (i == tails.size()) tails.add(x);
            else tails.set(i, x);
        }
        return tails.size();
    }

    // Exercice 2 — Pivots
    public static List<Integer> findPivots(int[] a) {
        int n = a.length;
        List<Integer> result = new ArrayList<>();
        if (n == 0) return result;
        int[] prefMax = new int[n];
        int[] sufMin = new int[n];
        prefMax[0] = a[0];
        for (int i = 1; i < n; i++) prefMax[i] = Math.max(prefMax[i-1], a[i]);
        sufMin[n-1] = a[n-1];
        for (int i = n-2; i >= 0; i--) sufMin[i] = Math.min(sufMin[i+1], a[i]);
        for (int i = 0; i < n; i++) {
            boolean leftOk = (i == 0) || (prefMax[i-1] <= a[i]);
            boolean rightOk = (i == n-1) || (sufMin[i+1] >= a[i]);
            if (leftOk && rightOk) result.add(a[i]);
        }
        return result;
    }

    // Exercice 3 — Matrice spirale n x n
    public static int[][] spiralMatrix(int n) {
        int[][] M = new int[n][n];
        int top = 0, bottom = n-1, left = 0, right = n-1;
        int val = 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) M[top][j] = val++;
            top++;
            for (int i = top; i <= bottom; i++) M[i][right] = val++;
            right--;
            if (top <= bottom) {
                for (int j = right; j >= left; j--) M[bottom][j] = val++;
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--) M[i][left] = val++;
                left++;
            }
        }
        return M;
    }

    // Exercice 4 — Plus grand rectangle de 1s dans une matrice binaire
    public static int maxRectangleOfOnes(int[][] mat) {
        if (mat.length == 0) return 0;
        int rows = mat.length, cols = mat[0].length;
        int[] height = new int[cols];
        int maxArea = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                height[c] = (mat[r][c] == 1) ? height[c] + 1 : 0;
            }
            maxArea = Math.max(maxArea, largestRectangleHistogram(height));
        }
        return maxArea;
    }

    private static int largestRectangleHistogram(int[] h) {
        int n = h.length;
        Deque<Integer> st = new ArrayDeque<>();
        int maxArea = 0;
        for (int i = 0; i <= n; i++) {
            int cur = (i == n) ? 0 : h[i];
            while (!st.isEmpty() && cur < h[st.peek()]) {
                int height = h[st.pop()];
                int left = st.isEmpty() ? 0 : st.peek() + 1;
                int width = i - left;
                maxArea = Math.max(maxArea, height * width);
            }
            st.push(i);
        }
        return maxArea;
    }

    // Exercice 5 — Vérifier permutation circulaire
    public static boolean isCircularPermutation(int[] a) {
        int n = a.length;
        boolean[] seen = new boolean[n + 1];
        for (int x : a) {
            if (x < 1 || x > n) return false;
            if (seen[x]) return false;
            seen[x] = true;
        }
        int start = -1;
        for (int i = 0; i < n; i++) if (a[i] == 1) { start = i; break; }
        for (int k = 0; k < n; k++) {
            int expected = k + 1;
            if (a[(start + k) % n] != expected) return false;
        }
        return true;
    }

    // Exercice 6 — Kadane (sous-suite contiguë de somme maximale)
    public static int kadaneMaxSubarray(int[] a) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxEnding = 0;
        for (int x : a) {
            maxEnding = maxEnding + x;
            maxSoFar = Math.max(maxSoFar, maxEnding);
            if (maxEnding < 0) maxEnding = 0;
        }
        return maxSoFar;
    }

    // Exercice 7 — Élément majoritaire (Boyer-Moore)
    public static int majorityElement(int[] a) {
        int count = 0, candidate = -1;
        for (int x : a) {
            if (count == 0) { candidate = x; count = 1; }
            else if (candidate == x) count++;
            else count--;
        }
        int cnt = 0;
        for (int x : a) if (x == candidate) cnt++;
        return (cnt > a.length / 2) ? candidate : -1;
    }

    // Exercice 8 — Nombres absents (1..n)
    public static List<Integer> missingNumbers(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int val = Math.abs(a[i]);
            if (val >= 1 && val <= n) {
                if (a[val - 1] > 0) a[val - 1] = -a[val - 1];
            }
        }
        List<Integer> missing = new ArrayList<>();
        for (int i = 0; i < n; i++) if (a[i] > 0) missing.add(i + 1);
        return missing;
    }

    // Exercice 9 — Différence diagonale
    public static int diagonalDifference(int[][] mat) {
        int n = mat.length;
        int sumPrimary = 0, sumSecondary = 0;
        for (int i = 0; i < n; i++) {
            sumPrimary += mat[i][i];
            sumSecondary += mat[i][n - 1 - i];
        }
        return Math.abs(sumPrimary - sumSecondary);
    }

    // Exercice 10 — Matrice magique 3x3
    public static boolean isMagic3x3(int[][] m) {
        if (m.length != 3 || m[0].length != 3) return false;
        int target = m[0][0] + m[0][1] + m[0][2];
        for (int i = 0; i < 3; i++) {
            int s = 0;
            for (int j = 0; j < 3; j++) s += m[i][j];
            if (s != target) return false;
        }
        for (int j = 0; j < 3; j++) {
            int s = 0;
            for (int i = 0; i < 3; i++) s += m[i][j];
            if (s != target) return false;
        }
        int d1 = m[0][0] + m[1][1] + m[2][2];
        int d2 = m[0][2] + m[1][1] + m[2][0];
        return d1 == target && d2 == target;
    }

    // Méthode principale pour tester tous les exercices
    public static void main(String[] args) {
        int[] ex1 = {2,1,4,2,3,5,1,7};
        System.out.println("Ex1 LIS length: " + lisLength(ex1));

        int[] ex2 = {2,4,3,5,6};
        System.out.println("Ex2 pivots: " + findPivots(ex2));

        int[][] s3 = spiralMatrix(3);
        System.out.println("Ex3 spiral(3):");
        for (int[] row : s3) System.out.println(Arrays.toString(row));

        int[][] mat = {
            {1,0,1,1,1},
            {1,1,1,1,0},
            {1,1,1,1,0},
        };
        System.out.println("Ex4 max rect of ones: " + maxRectangleOfOnes(mat));

        int[] ex5a = {4,5,1,2,3};
        int[] ex5b = {2,3,4,5,1};
        System.out.println("Ex5a circular? " + isCircularPermutation(ex5a));
        System.out.println("Ex5b circular? " + isCircularPermutation(ex5b));

        int[] ex6 = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("Ex6 Kadane: " + kadaneMaxSubarray(ex6));

        int[] ex7 = {2,2,1,2,3,2,2};
        System.out.println("Ex7 majority: " + majorityElement(ex7));

        int[] ex8 = {1,3,3,5};
        System.out.println("Ex8 missing: " + missingNumbers(Arrays.copyOf(ex8, ex8.length)));

        int[][] ex9 = {
            {11,2,4},
            {4,5,6},
            {10,8,-12}
        };
        System.out.println("Ex9 diagonal diff: " + diagonalDifference(ex9));

        int[][] magic = {
            {8,1,6},
            {3,5,7},
            {4,9,2}
        };
        System.out.println("Ex10 is magic 3x3? " + isMagic3x3(magic));
    }
}
