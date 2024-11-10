public class FileSearchFilter {
    public short minSimilarity = 70;  // 0-100
    public long minSize = 0; // KB
    public long maxSize = Integer.MAX_VALUE; 
    public boolean isHidden = false;  // true to show hidden files too
    public boolean onlyFolders = false;  // show only folders
    public boolean onlyFiles = false;  // show only files  // onlyFolders > onlyFiles

    // public boolean exactMatch = false; TODO tam eslesme secenegi?
}