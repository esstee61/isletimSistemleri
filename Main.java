import java.util.Iterator;

class Main {
    public static void main(String[] args)  // test // Asıl program App.javada
    {
        // inputs
        String path = System.getProperty("user.dir") + "\\deneme klasoru";
        String keyword = "test";
        System.out.println();
        long start = System.currentTimeMillis();

        // filter olmadan
        FileSearch fs = new FileSearch(path, keyword);
        fs.search();  // aramayi baslat

        System.out.println(System.currentTimeMillis() - start + "ms");

        // print
        Iterator<FileScore> it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while (it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.score);
            System.out.println(" " + fsTemp.file.getName());
        }

        System.out.println(">==============<");  // ayrac

        // filter ile
        start = System.currentTimeMillis();
        FileSearchFilter filter = new FileSearchFilter();  // filter olusturma
        filter.isHidden = true;  // konfigure etme
        filter.minSimilarity = 50;

        fs = new FileSearch(path, keyword, filter);
        fs.search();

        System.out.println(System.currentTimeMillis() - start + "ms");

        // print
        it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while (it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.score);
            System.out.println(" " + fsTemp.file.getName());
        }

        ThreadPool.executor.shutdownNow();
    }
}