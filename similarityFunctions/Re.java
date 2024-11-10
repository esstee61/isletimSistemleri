package similarityFunctions;

public class Re {
    public static int isSubstr(String s, String t) {
        if (t.contains(s)) {
            return 70 + Math.max(30 - t.length() + s.length(), 0); 
        }
        else return 0;
    }
}
