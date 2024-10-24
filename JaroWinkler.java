public class JaroWinkler {

    public static double jaroDistance(String s1, String s2) {
        if (s1.equals(s2)) return 1.0;

        int len1 = s1.length();
        int len2 = s2.length();

        int maxDist = Math.max(len1, len2) / 2 - 1;

        int[] hashS1 = new int[len1];
        int[] hashS2 = new int[len2];

        int match = 0;

        for (int i = 0; i < len1; i++) {
            for (int j = Math.max(0, i - maxDist); j < Math.min(len2, i + maxDist + 1); j++) {
                if (s1.charAt(i) == s2.charAt(j) && hashS2[j] == 0) {
                    hashS1[i] = 1;
                    hashS2[j] = 1;
                    match++;
                    break;
                }
            }
        }

        if (match == 0) return 0.0;

        double t = 0;
        int point = 0;

        for (int i = 0; i < len1; i++) {
            if (hashS1[i] == 1) {
                while (hashS2[point] == 0) {
                    point++;
                }
                if (s1.charAt(i) != s2.charAt(point)) {
                    t++;
                }
                point++;
            }
        }

        t /= 2;

        return ((double) match / len1 + (double) match / len2 + (match - t) / match) / 3.0;
    }

    public static double jaroWinkler(String s1, String s2, double p) {
        double jaroDist = jaroDistance(s1, s2);

        int prefix = 0;
        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                prefix++;
            } else {
                break;
            }
        }

        prefix = Math.min(4, prefix);  // Winkler's constraint
        return jaroDist + (prefix * p * (1 - jaroDist));
    }
}