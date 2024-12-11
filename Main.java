import java.util.Iterator;
import java.io.File;
import java.io.IOException;

class Main 
{
    public static void main(String[] args) 
    {   
        // inputs
        String path = System.getProperty("user.dir") + "\\deneme klasoru";
        String keyword = "test";

        // filter olmadan
        FileSearch fs = new FileSearch(path, keyword);
        fs.search();  // aramayi baslat
        
        // print
        Iterator<FileScore> it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while(it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.similarity);
            System.out.println(" " + fsTemp.file.getName());
            System.out.println("Dosya Yolu: " + fsTemp.file.getAbsolutePath());

            String filePath = fsTemp.file.getAbsolutePath();
            try {
                // Dosya nesnesi oluştur
                File file = new File(filePath);
    
                if (!file.exists()) {
                    System.out.println("Dosya bulunamadı: " + filePath);
                    return;
                }
    
                // Explorer'da dosya seçili halde aç
                String command = "explorer.exe /select," + file.getAbsolutePath();
                Runtime.getRuntime().exec(command);
    
            } catch (IOException e) {
                System.err.println("Hata: " + e.getMessage());
                e.printStackTrace();
            }


            System.out.println();
        }

        System.out.println(">==============<");  // ayrac

        // filter ile 
        FileSearchFilter filter = new FileSearchFilter();  // filter olustur
        filter.isHidden = true;  // konfigurasyon
        filter.minSimilarity = 0;

        fs = new FileSearch(path, keyword, filter);
        fs.search();
        
        // print
        it = fs.results.iterator();
        System.out.println(fs.results.size() + " tane dosya siralandi");
        while(it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.print(fsTemp.similarity);
            System.out.println(" " + fsTemp.file.getName());
        }
    }
}