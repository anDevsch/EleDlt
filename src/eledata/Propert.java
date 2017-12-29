/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eledata;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCH
 */
public class Propert {
    public static void proLoad(){
        InputStreamReader isr = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"\\"+"config.properties"));
            isr = new InputStreamReader(is, "GBK");
            Properties properties = new Properties();
            try {
                properties.load(isr);
                Globe.properties = properties;
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            Properties pps = new Properties();
            pps.load(new FileInputStream("Test.properties"));
            Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
            while(enum1.hasMoreElements()) {
                String strKey = (String) enum1.nextElement();
                String strValue = pps.getProperty(strKey);
                System.out.println(strKey + "=" + strValue);
            }
            */
            //System.out.println("数据项名称：" + properties.getProperty(sbr.toString()));
        } catch (Exception ex) {
            Logger.getLogger(Propert.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                isr.close();
            } catch (IOException ex) {
            Logger.getLogger(Propert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void fileLoad(){
        String path = System.getProperty("user.dir")+"\\";
        //System.out.println(path);
        String filename = "sendDi.txt";
        BufferedReader bw;
        try {
            bw = new BufferedReader(new InputStreamReader(new FileInputStream(path+filename)));
            String di=null;int i = 0;
            String[] senddi = new String[20];
            for(;(di = bw.readLine()) != null && i < 20;i++){
                if(!"".equals(di)){
                    senddi[i] = di.trim();
                }
                else {
                    break;
                }
            }
            String[] send = new String[i];
            System.arraycopy(senddi,0,send,0,i);
            Globe.send = send;
            bw.close();
        } catch (Exception ex) {
            System.err.println("txt文件加载失败");
        }
    }
}
