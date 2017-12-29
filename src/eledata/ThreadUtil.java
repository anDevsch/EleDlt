/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eledata;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCH
 */
public class ThreadUtil {
    protected ThreadPoolExecutor executor;
    protected boolean flag = true;
    public SockUtil server = null;
    ThreadUtil(SockUtil serv){
        this.server = serv;
        executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();  
    }
    public void startnewThread() throws IOException{
        executor.execute(new DyThread(1,this.server));
    }
    ///eg:run
    /*
    public void test(){
        new Thread(new DyThread()).start();
    }
    */
    public class DyThread implements Runnable {
        int thread;
        SockUtil serverSocket;
        SockUtil clientSocket;
        String addr = null;
        DyThread(int thread,SockUtil serverSocket){
            this.thread = thread;
            this.serverSocket = serverSocket;
            System.err.println(">>>new create thread-"+thread);
        }
        @Override
        public void run() {
            try {
                Socket client = serverSocket.isAccept();
                //create a new thread
                executor.execute(new DyThread(thread+1,server));
                if(client != null){
                    clientSocket = new SockUtil(client);
                    System.out.printf(">>>real thread count:%d,doing thread count：%d,finish thread count:%d\n",executor.getPoolSize(),executor.getActiveCount(),executor.getCompletedTaskCount());
                    if(getAddr()){
                        String[] senddi = Globe.send;
                        while(flag){
                            try {
                                if(!clientSocket.isClose()){
                                    for(int i = 0;i<senddi.length;i++){
                                        byte[] send = CreDltBuff.DltReadDataDemo(addr,senddi[i]);
                                        if(clientSocket.Write(send)){
                                            System.err.println("发送数据："+ToolUtil.HexToStr(send));
                                            LogUtil.WriteStr("send--");
                                            LogUtil.WriteByte(send);//日志
                                            byte[] rece = new byte[256];
                                            clientSocket.Read(rece);
                                            //System.out.println(ToolUtil.HexToStr(rece));
                                            byte[] nextbuff = AnaReceBuff.analysis(rece);
                                            if(nextbuff != null){
                                                sendNextData(nextbuff);
                                            }
                                        }
                                        Delay(100);
                                    }
                                }
                                else {
                                    System.err.println(">>>close thread-"+thread);
                                    break;
                                }
                            } catch (IOException ex) {
                                if(!clientSocket.isClose()){
                                    clientSocket.Close();
                                }
                                System.err.println(">>>close thread-"+thread+"-for error");
                                break;
                            }
                            Delay(10000);
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println(">>>close thread-"+thread+"-for unknow error");
            }
        }
        public boolean getAddr(){
            try {
                clientSocket.Write(CreDltBuff.reqAddr());
                System.err.println("发送数据："+ToolUtil.HexToStr(CreDltBuff.reqAddr()));
                LogUtil.WriteStr("send--");
                LogUtil.WriteByte(CreDltBuff.reqAddr());//日志
                byte[] buf = new byte[256];
                clientSocket.Read(buf);
                //LogUtil.WriteByte(thread,buf,read_len);
                addr = AnaReceBuff.anaaddrbuff(buf);
                if(addr != null){
                    //succeed receiver addr;
                    System.out.println(">>>"+thread+"-"+"addr:"+addr);
                    return true;
                }
                else {
                    Delay(1000);
                    byte[] addrb = new byte[]{0x68,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0x68,(byte)0x13,0x00,(byte)0xDF,0x16};
                    clientSocket.Write(addrb);
                    System.err.println("发送数据："+ToolUtil.HexToStr(addrb));
                    LogUtil.WriteStr("send--");
                    LogUtil.WriteByte(addrb);//日志
                    byte[] aaa = new byte[256];
                    clientSocket.Read(aaa);
                    //LogUtil.WriteByte(thread,buf,read_len);
                    addr = AnaReceBuff.anaaddrbuff(aaa);
                    if(addr != null){
                    //succeed receiver addr;
                    System.out.println(">>>"+thread+"-"+"addr:"+addr);
                    return true;
                    }
                }
            } catch (IOException ex) {
                return false;
            }
            return false;
        }
        public void sendNextData(byte[] buff){
            try {
                if(clientSocket.Write(buff)){
                    byte[] rece = new byte[256];
                    clientSocket.Read(rece);
                    System.out.println(ToolUtil.HexToStr(rece));
                    byte[] nextbuff = AnaReceBuff.analysis(rece);
                    if(nextbuff != null){
                        sendNextData(nextbuff);
                    }
                }
            } catch (IOException ex) {
                //Logger.getLogger(ThreadUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void Delay(int ms){
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                //Logger.getLogger(ThreadUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
