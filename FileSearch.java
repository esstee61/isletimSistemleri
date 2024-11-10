import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class FileSearch {
    private String path, keyword;
    private short minSimilarity = 70;
    private FileFilter options = new FileFilter() {  // to do not show hidden files defaultly
        public boolean accept(File f) {
            return !f.isHidden();
        }
    };
    // using treeset for auto sort by minSimilarity then length of the name
    // ConcurrentSkipListSet for safe-thread
    TreeSet<FileScore> results = new TreeSet<FileScore>(Comparator.comparing((FileScore fs) -> -fs.similarity)
                                                              .thenComparing((FileScore fs) -> fs.filenameLen));

    FileSearch (String path, String keyword) {  // default search
        this.path = path;
        this.keyword = keyword.toLowerCase();
    }

    FileSearch (String path, String keyword, FileSearchFilter opt) {  // search with filter
        this.path = path;
        this.keyword = keyword.toLowerCase();
        minSimilarity = opt.minSimilarity;
        options = new FileFilter() {
            public boolean accept(File f) {
                if (!opt.isHidden) {
                    if (f.isHidden()) return false;
                }
                if (opt.onlyFolders) {
                    if (!f.isDirectory()) return false;
                }
                else if (opt.onlyFiles) {
                    if (!f.isFile()) return false;
                }
                return opt.maxSize*1024 >= f.length() && f.length() >= opt.minSize*1024;
            }
        };
    }

    public List<File> search(boolean recursive) { 
        // recursive = false -> search only given path        and return the folders in that path 
        // TODO        true  -> search all the subdirectories and return null

        File f = new File(path);

        for (File file : f.listFiles(options)) {
            FileScore fileScore = new FileScore(file, keyword);
            if (fileScore.similarity >= minSimilarity) {
                results.add(fileScore);
            }
        }

        // TODO recursive option work

        return null;  // TODO return directories in the folder if recursive is false
    }
}
