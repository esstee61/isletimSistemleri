package similarityFunctions;
public class Solution {
    public static int isSubsequence(String s, String t) {
        int sn = s.length();
        int tn = t.length();
        if (sn == 0 || tn == 0) return -1;
        int i = 0;
        int j = 0;
        while (s.charAt(i) != t.charAt(j)) {
            ++j;
            if (j >= tn) break;
        }

        int start = j;  // save first index of the match

        while (i<sn && j<tn){
            if (s.charAt(i) == t.charAt(j)) {
                ++i;
            }
            ++j;
        }   
        j--;  // last index of the match

        if (sn > i*2 || tn*2 < sn) return 0;  // en az ilk yarisi eslesmediyse 0 dondur

        int res = 0;
        if (j - start + 1 > sn || tn-j > sn)  // "tetetetest" ve "TaESfdTjklTESTgh" vb durumlar icin
            res = isSubsequence(s, t.substring(start+1, tn));

        if (sn == (j-start+1) && i == sn) return max(res, (int) (80 + (double) sn / tn * 20));  // full and partial match

        return max(res, (int) (((double) i/sn + (double) ((sn<tn) ? sn: tn)/(j-start+1) + (double) ((j-start+1)/tn))*25));  // half match
    }

    public static int max(int a, int b) {
        return (a > b) ? a: b;
    }
}