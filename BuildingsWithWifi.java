import java.io.*;
import java.util.*;

public class Solution {

    public static class Result {
        /**
         * Returns how many buildings are served by at least as many routers
         * as the building's headcount. 1‑based routerLocation.
         */
        public static int getServedBuildings(
                int[] buildingCount,
                int[] routerLoc1,
                int[] routerRange) {
            int n = buildingCount.length;
            int R = routerLoc1.length;
            int[] diff = new int[n + 1];  // we’ll do diff[start] += 1, diff[end+1] -= 1

            // Build diff array for router coverage counts
            for (int i = 0; i < R; i++) {
                int loc1 = routerLoc1[i];
                int range = routerRange[i];
                int l = Math.max(1, loc1 - range);
                int r = Math.min(n, loc1 + range);

                diff[l - 1]++;           // start coverage at l‑th building
                if (r < n) diff[r]--;   // stop coverage after r‑th building
            }

            // Convert to real coverage per building via prefix-sum
            int served = 0;
            int curr = 0;
            for (int i = 0; i < n; i++) {
                curr += diff[i];
                if (curr >= buildingCount[i]) served++;
            }

            return served;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        int n = in.nextInt();
        int[] buildingCount = new int[n];
        for (int i = 0; i < n; i++) buildingCount[i] = in.nextInt();

        int R = in.nextInt();
        int[] routerLoc = new int[R];
        int[] routerRange = new int[R];
        for (int i = 0; i < R; i++) routerLoc[i] = in.nextInt();
        for (int i = 0; i < R; i++) routerRange[i] = in.nextInt();

        int ans = Result.getServedBuildings(buildingCount, routerLoc, routerRange);
        System.out.println(ans);
    }

    // Minimal buffered fast input reader
    static class FastReader {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");
        String next() throws IOException {
            while (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
