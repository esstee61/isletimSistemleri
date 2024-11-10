import java.util.Iterator;

class Main 
{
    public static void main(String[] args) 
    {   
        // inputs
        String path = System.getProperty("user.dir") + "\\deneme klasoru";
        String keyword = "tESt";

        // filter olmadan
        FileSearch fs = new FileSearch(path, keyword);
        fs.search(true);  // aramayi baslat
        
        // print
        Iterator<FileScore> it = fs.results.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().file.getName());
        }

        System.out.println(">==============<");  // ayrac

        // filter ile 
        FileSearchFilter filter = new FileSearchFilter();  // filter olustur
        filter.isHidden = true;  // konfiguasyon
        filter.minSimilarity = 0;

        fs = new FileSearch(path, keyword, filter);
        fs.search(true);
        
        // print
        it = fs.results.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().file.getName());
        }
    }
}