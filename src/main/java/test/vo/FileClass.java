package test.vo;

/**
 * Created by lai on 2019/4/23.
 */
public class FileClass {
    public String FileName;
    public String Filesize;
    public String DataName;

    public FileClass(String filename,String filesize,String dataname)
    {
        this.FileName = filename;
        this.Filesize = filesize;
        this.DataName = dataname;
    }
}
