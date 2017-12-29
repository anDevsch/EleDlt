package eledata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @time 2017/10/13
 * @author SCH
 */
public class LogUtil {
    static String path;
    static String filename;
    static Date date = new Date();
    static SimpleDateFormat sdfna=new SimpleDateFormat("yyyy_MM_dd");
    static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:  ");
    static BufferedWriter bw;
    public static void configfile() {
        date = new Date();
        path = System.getProperty("user.dir")+"\\";
        //System.out.println(path);
        filename = "Log\\Log_"+ sdfna.format(date)+".log";
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+filename,true)));
        } catch (FileNotFoundException ex) {
            File file = new File(path+filename);
            if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs()) {
                        Logger.getLogger(LogUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            try{
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+filename,true)));
            }catch(FileNotFoundException e){
                Logger.getLogger(LogUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void WriteLine(String logline){
        try {
            configfile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+filename,true)));
            bw.write(sdf.format(date)+logline);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
        }
    }
    public static void WriteStr(String logline){
        try {
            configfile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+filename,true)));
            bw.write(sdf.format(date)+logline);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
        }
    }
    public static void WriteByte(byte[] logbyte){
        try {
            configfile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+filename,true)));
            bw.write(ToolUtil.HexToStr(logbyte));
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
        }
    }
}
