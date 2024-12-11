import java.util.Iterator;

class Main 
{
    public static void main(String[] args) 
    {
        long startTime = System.currentTimeMillis();
        // inputs
        //String path = System.getProperty("user.dir") + "\\deneme klasoru";
        
        String path = System.getProperty("user.dir") + "\\deneme klasoru";
        String keyword = "tESt";

        // filter olmadan
        FileSearch fs = new FileSearch(path, keyword);
        
        fs.search(); // Aramayı başlat

        Iterator<FileScore> it = fs.results.iterator();
        int size = fs.results.size();

        long endTime = System.currentTimeMillis();
        
        // print
        while(it.hasNext()) {
            FileScore fsTemp = it.next();
            System.out.println(fsTemp.file.getName());
            System.out.println("Yol: " + fsTemp.file.getPath());

            System.out.println();
        }

        System.out.println(size + " tane dosya bulundu.\tTime: " + (float)(endTime-startTime)/1000 + "s");
    }
}