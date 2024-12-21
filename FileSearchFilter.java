public class FileSearchFilter {
    public short minSimilarity = 70;  // 0-100
    public long minSize = 0; // KB
    public long maxSize = Integer.MAX_VALUE;  // KB
    public boolean isHidden = false;  // true to show hidden files too
    public boolean onlyFolders = false;  // show only folders
    public boolean onlyFiles = false;  // show only files  // !! onlyFolders has more priority than onlyFiles
    public boolean exactMatch = false; // find only exact matchs
}