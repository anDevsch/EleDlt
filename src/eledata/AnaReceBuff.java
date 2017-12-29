package eledata;
/**
 * @time 2017/10/13
 * @author SCH
 */
    public class AnaReceBuff {
    //AnaReceBuff.analysis(new byte[]{0x68,(byte)0x92,0x23,0x76,0x08,0x17,0x04,0x68,(byte)0x91,0x08,0x33,0x34,0x33,0x33,0x33,0x33,0x33,0x33,0x50,0x16});
    static String Addr;
    static String Di;
    static float Data = 0;
    /**
     * 报文分析
     * @param byt 返回报文
     */
    public static byte[] analysis(byte[] byt){
        int start = getStart(byt);
        int dataLength = byt[start + 9];
        byte[] nbyte = new byte[dataLength+12];
        System.arraycopy(byt,start,nbyte,0,dataLength + 12);
        if(isRight(nbyte)){
            Addr = getAddr(nbyte);
            Di = getDi(nbyte);
            byte[] sendbyte = null;
            switch((nbyte[8]&0xFF)){
                case 0x91:
                    Data = getData(nbyte,dataLength);
                    break;
                case 0xB1:
                    Data = getData(nbyte,dataLength);
                    sendbyte = CreDltBuff.DltReadDataDemo(Addr,Di,1);
                    //Globe.com.sendDataToSerialPort(sendbyte);
                    break;
                case 0x92:
                    Data = getData(nbyte,dataLength-1);
                    break;
                case 0xB2:
                    Data = getData(nbyte,dataLength-1);
                    int SEQ = (nbyte[9+dataLength]&0xFF)+1;
                    if(SEQ == 256)SEQ++;
                    sendbyte = CreDltBuff.DltReadDataDemo(Addr,Di,SEQ);
                    //Globe.com.sendDataToSerialPort(sendbyte);
                    break;
            }
            String outp = "Addr:"+Addr+"-Di:"+Di+"-"+Globe.properties.getProperty(Di)+"-Data:"+Data+"----"+ToolUtil.HexToStr(nbyte);
            //System.out.print("Addr:"+Addr);
            //System.out.print("-Di:"+Di+"-"+Globe.properties.getProperty(Di));
            //System.out.print("-Data:"+Data);
            //System.out.println("----"+ToolUtil.HexToStr(nbyte));
            System.out.println(outp);
            LogUtil.WriteLine(outp);
            if(sendbyte != null)return sendbyte;
        }
        return null;
    }
    
    public static String anaaddrbuff(byte[] byt){
        int start = getStart(byt);
        int dataLength = byt[start + 9];
        byte[] nbyte = new byte[dataLength+12];
        System.arraycopy(byt,start,nbyte,0,dataLength + 12);
        if(isRight(nbyte)){
            //return ToolUtil.byteToStr(nbyte[6])+ToolUtil.byteToStr(nbyte[5])+ToolUtil.byteToStr(nbyte[4])+ToolUtil.byteToStr(nbyte[3])+ToolUtil.byteToStr(nbyte[2])+ToolUtil.byteToStr(nbyte[1]);
            return getAddr(nbyte);
        }
        return null;
    }
    
    public static boolean isRight(byte[] nbyte){
        if(nbyte[0]!=(byte)0x68||nbyte[nbyte.length-1]!=(byte)0x16||nbyte[nbyte.length-2]!=ToolUtil.getCS(nbyte)){
            LogUtil.WriteStr("异常报文-");
            LogUtil.WriteByte(nbyte);
            LogUtil.WriteLine("");
            System.err.println("异常报文："+ToolUtil.HexToStr(nbyte));
            return false;
        }
        else return true;
    }
    public static int getStart(byte[] byt){
        int start = 0;
        for(int i=0;i<byt.length;i++){
            if(byt[i] != (byte)0xFE){start = i;break;}
        }
        return start;
    }
    public static String getAddr(byte[] byt){
        String saddr = null;
        byte[] b6 = new byte[6];
        System.arraycopy(byt, 1, b6, 0, 6);
        saddr = ToolUtil.HexToStr(b6, true, false);
        return saddr;
    }
    public static String getDi(byte[] byt){
        String sdi = null;
        byte[] b4 = new byte[4];
        System.arraycopy(byt, 10, b4, 0, 4);
        sdi = ToolUtil.HexToStr(b4, false, true);
        return sdi;
    }
    public static float getData(byte[] nbyte,int dataLength){
        float d = 0;
        for(int i=0;i<dataLength-4;i++){
            d += ((((nbyte[14+i]&0xF0)>>>4)-3)*10+(nbyte[14+i]&0x0F)-3)*Math.pow(10,i*2-2);
        }
        return d;
    }
}
