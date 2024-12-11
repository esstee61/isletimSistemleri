import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearch {

    private String path, keyword;
    private short minSimilarity = 70;
    private boolean onlyFiles = false;
    private boolean exactMatch = false;
    private FileFilter options = new FileFilter() {  // to do not show hidden files defaultly
        public boolean accept(File f) {
            return !f.isHidden();
        }
    };
    // using treeset for auto sort by minSimilarity then length of the name then path (for unique value)
    // Change the data type to ConcurrentSkipListSet<FileScore> for safe-thread

    ConcurrentSkipListSet<FileScore> results = new ConcurrentSkipListSet<>(
        Comparator.comparing((FileScore fs) -> -fs.similarity)
                  .thenComparing((FileScore fs) -> fs.filenameLen)
                  .thenComparing((FileScore fs) -> fs.file.getPath())
    );

    FileSearch (String path, String keyword) {  // default search
        this.path = path;
        this.keyword = keyword.toLowerCase();
    }

    FileSearch (String path, String keyword, FileSearchFilter opt) {  // search with filter
        this.path = path;
        this.keyword = keyword.toLowerCase();
        minSimilarity = opt.minSimilarity;
        onlyFiles = (opt.onlyFolders) ? false:opt.onlyFiles;
        exactMatch = opt.exactMatch;
        options = new FileFilter() {
            public boolean accept(File f) {
                if (!opt.isHidden) {
                    if (f.isHidden()) return false;
                }
                if (opt.onlyFolders) {
                    if (!f.isDirectory()) return false;
                }
                return opt.maxSize*1024 >= f.length() && f.length() >= opt.minSize*1024;
            }
        };
    }

    public void search() {
        AtomicInteger tasks = new AtomicInteger(1);
        ThreadPool.executor.submit(() -> recSearch(new File(path), tasks));

        while (tasks.get() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        ThreadPool.executor.shutdown();
    }

    public void recSearch(File folder, AtomicInteger tasks) {
        for (File file : folder.listFiles(options)) {  // each file in the folder
            //System.out.println(Thread.currentThread().getName() + " is checking " + file.getName() + " at " + file.getPath());

            // check name similarity
            if (!onlyFiles || file.isFile()) { // if it is not file and onlyFiles option is true: dont check similarity:
                FileScore fileScore = new FileScore(file, keyword, exactMatch);
                if (fileScore.similarity >= minSimilarity) {
                    results.add(fileScore);
                }
            }

            // recursive call for dirs
            if (file.isDirectory()){
                tasks.incrementAndGet();
                ThreadPool.executor.submit(() -> recSearch(file, tasks));
            }
        }

        tasks.decrementAndGet();
    }
}