/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eledata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author SCH
 */
public class SockUtil {
    public Socket socket = null;
    ServerSocket server;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    SockUtil(){}
    SockUtil(Socket s) throws IOException{
        socket = s;
        bis = new BufferedInputStream(socket.getInputStream());  
	bos = new BufferedOutputStream(socket.getOutputStream());
    }
    SockUtil(SockUtil so) throws IOException{
        socket = so.socket;
        bis = new BufferedInputStream(socket.getInputStream());  
	bos = new BufferedOutputStream(socket.getOutputStream());
    }
    SockUtil(String Addr,int port) throws UnknownHostException, IOException{
        socket = new Socket(InetAddress.getByName(Addr),port);
        bis = new BufferedInputStream(socket.getInputStream());  
	bos = new BufferedOutputStream(socket.getOutputStream());
    }
    public void StartServer(int port) throws UnknownHostException, IOException{
        server = new ServerSocket(port);
    }
    public Socket isAccept() throws IOException{
        if(server!=null){
            socket = server.accept();
            bis = new BufferedInputStream(socket.getInputStream());  
            bos = new BufferedOutputStream(socket.getOutputStream());
            return socket;
        }else{
            return null;
        }
    }
    public boolean Write(byte[] writebuff) throws IOException{
        if(!isClose()){
            bos.write(writebuff,0,writebuff.length); 
            bos.flush();
            return true;
        }
        return false;
    }
    public int Read(byte[] readbuff) throws IOException{
        if(!isClose()){
            return bis.read(readbuff);
        }
        return -1;
    }
    public void Close() throws IOException{
        if(!socket.isClosed())
	    socket.close();
    }
    public boolean isClose() throws IOException{
        return socket.isClosed();
    }
}
