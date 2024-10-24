import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Re {
    public static int search(String s, String t) {
        int tn = t.length();
        int sn = s.length();
        Pattern pat = Pattern.compile(s + ".*", Pattern.CASE_INSENSITIVE);
        Matcher match = pat.matcher(t);
        if (match.find()) return 80 + Math.max(20-tn+sn, 0);  // full match and prematch match
        
        pat = Pattern.compile(".*" + s, Pattern.CASE_INSENSITIVE);
        match = pat.matcher(t);
        if (match.find()) return 75 + Math.max(20-tn+sn, 0); // postmatch

        pat = Pattern.compile(".*" + s + ".*", Pattern.CASE_INSENSITIVE);
        match = pat.matcher(t);
        if (match.find()) return 70 + Math.max(20-tn+sn, 0); // prepostmatch
        
        return 0;
    }
}
