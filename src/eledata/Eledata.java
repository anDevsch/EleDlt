/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eledata;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author SCH
 */
public class Eledata {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //加载要获取数据的信息
        Propert.fileLoad();
        //加载Di信息
        Propert.proLoad();
        //启动服务器
        SockUtil server = new SockUtil();
        server.StartServer(4601);
        //配置线程
        ThreadUtil thread = new ThreadUtil(server);
        thread.startnewThread();
        /*
        while(true){
            Socket accept = server.isAccept();
            if(accept!=null)
            {
                System.out.println("连接成功");
                //定义变量
                String addr = null;
                byte[] rece = new byte[256];
                //发送报文
                SockUtil client = new SockUtil(accept);
                client.Write(CreDltBuff.reqAddr());
                int len = client.Read(rece);
                if(len>0){
                    addr = AnaReceBuff.anaaddrbuff(rece);
                }
                if(addr!=null){
                    if(client.Write(CreDltBuff.DltReadDataDemo(addr,"00000000"))){
                        rece = new byte[256];
                        client.Read(rece);
                        AnaReceBuff.analysis(rece);
                    }
                }
            }
        }
        */
    }
}
