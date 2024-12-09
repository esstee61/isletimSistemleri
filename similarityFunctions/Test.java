package similarityFunctions;

public class Test 
{
    public static void main(String[] args) 
    {   
        String find = "test";

        // Test cases
        String[] file_names = {"qwerty.test", "asdf", "tEsT12sadf124", "Test2", 
                               "1asdf2421tESt", "asdfTesT124", "TEqwerjklxST", 
                               "TEzxcTESTcvbnST", "ttgheeklsskkTT", "abcdefghijklmnopqrstuvwxyz", 
                               "iceTEaMachineSAuTomat", "tes1", "tes", "tes1t", 
                               "t4st", "t432", "te", "TESqwer", "teeeeeeest", 
                               "enteresant", "kestane", "Latest", "ttteeesssttt", 
                               "TaESfdTjklTESTghj", "tesaduf", "tetetetetettest", 
                               "t", "e", "x", "tets", "etst", "teest", "teat", 
                               "zxcvbnmyuiophjklqwsdTETghjkl_6TEST", "TESkereTESkereTESkere",
                               "teat", "1est", "", "sadltjaslkj;asldkjfsaltkjslakdrjasdf asdfqwerpoi2134124334asld;ikjlajsdft;ekasrt"};

        // Measure time for JaroWinkler
        long startJaroWinkler = System.nanoTime();
        for (String str : file_names) {
            // int similarity = (int) (JaroWinkler.jaroWinkler(find, str.toLowerCase(), 0.2) * 100);
            // System.out.print(str);
            // System.out.print(" => ");
            // System.out.println(similarity);
        }
        long endJaroWinkler = System.nanoTime();
        long durationJaroWinkler = endJaroWinkler - startJaroWinkler;
        System.out.println("Time taken by JaroWinkler: " + durationJaroWinkler + " ns");

        // Measure time for isSubsequence
        long startIsSubsequence = System.nanoTime();
        for (String str : file_names) {
            int result = Solution.isSubsequence(find, str.toLowerCase());
            System.out.print(str);
            System.out.print(" => ");
            System.out.println(result);
        }
        long endIsSubsequence = System.nanoTime();
        long durationIsSubsequence = endIsSubsequence - startIsSubsequence;
        System.out.println("Time taken by Subsequence: " + durationIsSubsequence + " ns");

        long startSub = System.nanoTime();
        for (String str : file_names) {
            // int Sub = Re.isSubstr(find, str.toLowerCase());
            // System.out.print(str);
            // System.out.print(" => ");
            // System.out.println(Sub);
        }
        long endSub = System.nanoTime();
        long durationSub = endSub - startSub;
        System.out.println("Time taken by isSubstr: " + durationSub + " ns");
    }
}