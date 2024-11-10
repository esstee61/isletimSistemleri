import java.io.File;

public class FileScore {
    File file;
    String keyword;
    int keywordLen;
    int filenameLen;
    int similarity;

    FileScore(File file, String keyword) {
        this.file = file;
        this.keyword = keyword;
        keywordLen = keyword.length();
        filenameLen = file.getName().length();
        similarity = isSubsequence(file.getName().toLowerCase());
    }

    private int isSubsequence(String fileName) {
        int nameLen = fileName.length();
        if (keywordLen == 0 || nameLen == 0) return -1;
        int i = 0;
        int j = 0;
        while (keyword.charAt(i) != fileName.charAt(j)) {
            ++j;
            if (j >= nameLen) break;
        }

        int start = j;  // save first index of the match

        while (i<keywordLen && j<nameLen){
            if (keyword.charAt(i) == fileName.charAt(j)) {
                ++i;
            }
            ++j;
        }   
        j--;  // last index of the match

        if (keywordLen > i*2 || nameLen*2 < keywordLen) return 0;  // en az ilk yarisi eslesmediyse 0 dondur

        int res = 0;
        if (j - start + 1 > keywordLen * 2 || nameLen-j > keywordLen)  // "tetetetest" ve "TaESfdTjklTESTgh" vb durumlar icin
            res = isSubsequence(fileName.substring(start+1, nameLen));

        if (keywordLen == (j-start+1) && i == keywordLen) 
            return Math.max(res, (int) (80 + (double) keywordLen / nameLen * 20));  // full and partial match

        // half match
        return Math.max(res, (int) (((double) i/keywordLen + 
                                     (double) (Math.min(keywordLen, nameLen)/(j-start+1)) + 
                                     (j-start+1)/nameLen) * 25));
    }
}
