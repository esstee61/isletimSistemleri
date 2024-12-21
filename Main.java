import java.util.Iterator;

class Main {
    public static void main(String[] args) // testing
    {
        // inputs
        String path = System.getProperty("user.dir") + "\\deneme klasoru";
        String keyword = "tESt";

        // filter olmadan
        FileSearch fs = new FileSearch(path, keyword);
        fs.search(); // aramayi baslat

        // print
        Iterator<FileScore> it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while (it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.score);
            System.out.println(" " + fsTemp.file.getName());
        }

        System.out.println(">==============<"); // ayrac

        // filter ile
        FileSearchFilter filter = new FileSearchFilter(); // filter olusturma
        filter.isHidden = true; // konfigure etme
        filter.minSimilarity = 0;

        fs = new FileSearch(path, keyword, filter);
        fs.search();

        // print
        it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while (it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.score);
            System.out.println(" " + fsTemp.file.getName());
        }
    }
}