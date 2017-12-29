package eledata;

/**
 * @time 2017/10/13
 * @author SCH
 */
public class ToolUtil {
    //tool转换类型工具
    static final char[] HEX_CHAR_TABLE = {
            '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    public static byte charToByte(char c){
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * get cs
     * @param byt 报文
     * @return cs
     */
    public static byte getCS(byte[] byt){
        int cs = 0;
        for(int i = 0;i<byt.length-2;i++){
            if(byt[i]!=(byte)0xFE){
                cs += (byt[i]&0xFF);
            }
        }
        return (byte)cs;
    }
    /**
     * byte[] To String
     * @param data  byte[]报文
     * @ isDesc 是否地位在前高位在后
     * @ isMin33 石否减0x33
     * @return String
     */
    public static String HexToStr(byte[] data){
        if(data == null || data.length == 0)
            return null;
        byte[] hex = new byte[data.length * 2];
        int index = 0;
        for(byte b : data){
            int v = b & 0xFF;
            hex[index++] = (byte) HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = (byte) HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex);
    }
    public static String HexToStr(byte[] data,boolean isDesc,boolean isMin33){
        if(data == null || data.length == 0)
            return null;
        byte[] hex = new byte[data.length * 2];
        int index = 0;
        for(byte b : data){
            int v = b & 0xFF;
            if(isMin33){
                hex[index++] = (byte) HEX_CHAR_TABLE[(v >>> 4)-3];
                hex[index++] = (byte) HEX_CHAR_TABLE[(v & 0xF)-3];
            }
            else{
                hex[index++] = (byte) HEX_CHAR_TABLE[v & 0xF];
                hex[index++] = (byte) HEX_CHAR_TABLE[v >>> 4];
            }
        }
        if(isDesc)hex = byteDesc(hex);
        return new String(hex);
    }
    //未用...
    public static byte[] hexStrToBytes(String data){
        if(data == null || "".equals(data))
            return null;
        data = data.toUpperCase();
        int length = data.length()/2;
        char[] dataChars = data.toCharArray();
        byte[] byteData = new byte[length];
        for (int i = 0;i<length;i++){
            int pos = i * 2;
            byteData[i] = (byte)(charToByte(dataChars[pos]) << 4 | charToByte(dataChars[pos + 1]));
        }
        return byteData;
    }
    
    //byte[]取反，求地址高位在前，低位在后
    public static byte[] byteDesc(byte[] data){
        int leng = data.length;
        byte[] desby = new byte[leng];
        for(int i=0;i<leng;i++){
            desby[i] = data[leng-i-1];
        }
            return desby;
    }
    //byte转string
    public static String byteToStr(byte data){
        byte[] hex = new byte[2];
        int v = data & 0xFF;
        hex[0] = (byte) HEX_CHAR_TABLE[v >>> 4];
        hex[1] = (byte) HEX_CHAR_TABLE[v & 0xF];
        return new String(hex);
    }
    /*
    public static String byteToStr(byte byt){
        return String.format("%02x",byt & 0xff);
    }
    */
}
