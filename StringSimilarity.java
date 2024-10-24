public class StringSimilarity {

    // Function to calculate the modified Levenshtein Distance with custom costs
    public static int customLevenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        // Create a 2D array to store the edit distances
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Initialize the base cases (transforming from empty string)
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i * 2;  // Cost of deleting all characters of s1 (deletion cost = 2)
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j * 2;  // Cost of inserting all characters of s2 (insertion cost = 2)
        }

        // Fill the dp array
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No cost if characters are the same
                } else {
                    // Calculate the costs of substitution, deletion, and insertion
                    int substitutionCost = dp[i - 1][j - 1] + 1;  // Substitution cost = 1
                    int deletionCost = dp[i - 1][j] + 2;          // Deletion cost = 2
                    int insertionCost = dp[i][j - 1] + 2;         // Insertion cost = 2

                    // Take the minimum of the three operations
                    dp[i][j] = Math.min(substitutionCost, Math.min(deletionCost, insertionCost));
                }
            }
        }

        // The final answer is in dp[len1][len2]
        return dp[len1][len2];
    }

    // Function to check if the two strings are "similar enough"
    public static boolean areSimilar(String s1, String s2, int threshold) {
        int distance = customLevenshteinDistance(s1, s2);
        return distance <= threshold;  // Strings are similar if distance is less than or equal to threshold
    }
}