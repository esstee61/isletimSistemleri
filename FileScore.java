import java.io.File;

public class FileScore {  // file objelerini score ile özdeşleştirmek için
    File file;
    int filenameLen;
    int score;

    FileScore(File file, String keyword, boolean exactMatch) {
        this.file = file;
        filenameLen = file.getName().length();
        score = exactMatch ? isSubstr(file.getName().toLowerCase(), keyword)  // tam eşleşme ayarı
                           : isSubsequence(file.getName().toLowerCase(), keyword);
    }

    private int isSubsequence(String fileName, String keyword) {  // varsayılan puanlandırma algoritması
        int keywordLen = keyword.length();
        int nameLen = fileName.length();
        if (keywordLen == 0 || nameLen == 0)
            return -1;
        int i = 0;
        int j = 0;
        while (keyword.charAt(i) != fileName.charAt(j)) {  // until first char match
            ++j;
            if (j >= nameLen)
                break;
        }

        int start = j;  // first index of the match
        int end = j;  // last index of the match

        while (i < keywordLen && j < nameLen) {  // until last char match (if it contains keyword)
            if (keyword.charAt(i) == fileName.charAt(j)) {
                ++i;  // if it contains the keyword, i become equal with keywordLen end of this loop 
                end = j;
            }
            ++j;
        }

        // keyword = 'test' Keywordlen:4  
        // i: number of matched chars
        // fileName = '.......test...' nameLen
        // first,last indexes ^     ^   start and end | so subsequence lenght = end - start + 1 

        int matchLen = end - start + 1;

        if (keywordLen > i * 2 || nameLen * 2 < keywordLen)
            return 0;  // aratılan kelimenin en az ilk yarısı eşleşmediyse 0 döndür

        int res = 0;
        if (matchLen > keywordLen && nameLen - start > keywordLen)  // "tetest...." ve "T.E.S.T..TEST" durumlar için
            res = isSubsequence(fileName.substring(start + 1, nameLen), keyword);
        
        // exact match
        if (i == keywordLen && keywordLen == matchLen)
            return Math.max(res, (int) (80 + (double) matchLen / nameLen * 20));  // '....test....'
        
        // half match: matchLen > keywordLen  // '...t.e.s.t...' 'test'
        return Math.max(res,
                        (int) ((double) (i+1) / keywordLen * 25 +    // '...t.e.s...' '..t.e..'
                                (double) i / matchLen * 40 +
                                (double) matchLen / nameLen * 15));
    }

    public int isSubstr(String fileName, String keyword) {  // tam eşleşme araması
        if (fileName.contains(keyword)) {
            return 70 + Math.max(30 + keyword.length() - fileName.length(), 0);
        } else
            return 0;
    }
}
