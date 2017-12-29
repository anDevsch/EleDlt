package eledata;

/**
 * @time 2017/10/13
 * @author SCH
 */
public class CreDltBuff {
    /**
     * 获取终端地址
     * @return dltbuff for get addr
     */
    public static byte[] reqAddr(){
        return new byte[]{
            (byte)0xFE,(byte)0xFE,(byte)0xFE,(byte)0xFE,0x68,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0xAA,(byte)0x68,(byte)0x13,0x00,(byte)0xDF,0x16
        };
    }
    
    /**
     * 读数据报文
     * create dlt type FE FE FE FE 68 A0 A1 A2 A3 A4 A5 68 13 DI0 DI1 DI2 DI3 CS 16
     * @param addr 地址
     * @param di 数据标识 不用加33
     * @return dlt报文/null 地址或数据标识错误
     */
    public static byte[] DltReadDataDemo(String addr,String di){
        if(addr.length()!=12||di.length()!=8){
            LogUtil.WriteStr("地址或数据标识错误！");
            System.err.println("地址或数据标识错误！");
            return null;
        }
        byte[] bt = new byte[]{
            //(byte)0xFE,(byte)0xFE,(byte)0xFE,(byte)0xFE, //fe fe fe fe
            0x68,
            (byte)(Integer.parseInt(addr.substring(10,11))<<4|Integer.parseInt(addr.substring(11,12))), //Addr
            (byte)(Integer.parseInt(addr.substring(8,9))<<4|Integer.parseInt(addr.substring(9,10))), //Addr
            (byte)(Integer.parseInt(addr.substring(6,7))<<4|Integer.parseInt(addr.substring(7,8))), //Addr
            (byte)(Integer.parseInt(addr.substring(4,5))<<4|Integer.parseInt(addr.substring(5,6))), //Addr
            (byte)(Integer.parseInt(addr.substring(2,3))<<4|Integer.parseInt(addr.substring(3,4))), //Addr
            (byte)(Integer.parseInt(addr.substring(0,1))<<4|Integer.parseInt(addr.substring(1,2))), //Addr
            0x68,
            0x11, //concrol
            0x04, //longth
            (byte)((Integer.parseInt(di.substring(0,1))+3)<<4|(Integer.parseInt(di.substring(1,2))+3)), //DI1
            (byte)((Integer.parseInt(di.substring(2,3))+3)<<4|(Integer.parseInt(di.substring(3,4))+3)), //DI2
            (byte)((Integer.parseInt(di.substring(4,5))+3)<<4|(Integer.parseInt(di.substring(5,6))+3)), //DI3
            (byte)((Integer.parseInt(di.substring(6,7))+3)<<4|(Integer.parseInt(di.substring(7,8))+3)), //DI4
            0x00, //CS
            0x16
        };
        bt[bt.length-2] = ToolUtil.getCS(bt);
        return bt;
    }
    public static byte[] DltReadDataDemo(String addr,String di,int SEQ){
        if(addr.length()!=12||di.length()!=8){
            LogUtil.WriteStr("地址或数据标识错误！");
            System.err.println("地址或数据标识错误！");
            return null;
        }
        byte[] bt = new byte[]{
            //(byte)0xFE,(byte)0xFE,(byte)0xFE,(byte)0xFE, //fe fe fe fe
            0x68,
            (byte)(Integer.parseInt(addr.substring(10,11))<<4|Integer.parseInt(addr.substring(11,12))), //Addr
            (byte)(Integer.parseInt(addr.substring(8,9))<<4|Integer.parseInt(addr.substring(9,10))), //Addr
            (byte)(Integer.parseInt(addr.substring(6,7))<<4|Integer.parseInt(addr.substring(7,8))), //Addr
            (byte)(Integer.parseInt(addr.substring(4,5))<<4|Integer.parseInt(addr.substring(5,6))), //Addr
            (byte)(Integer.parseInt(addr.substring(2,3))<<4|Integer.parseInt(addr.substring(3,4))), //Addr
            (byte)(Integer.parseInt(addr.substring(0,1))<<4|Integer.parseInt(addr.substring(1,2))), //Addr
            0x68,
            0x12, //concrol
            0x05, //longth
            (byte)((Integer.parseInt(di.substring(0,1))+3)<<4|(Integer.parseInt(di.substring(1,2))+3)), //DI1
            (byte)((Integer.parseInt(di.substring(2,3))+3)<<4|(Integer.parseInt(di.substring(3,4))+3)), //DI2
            (byte)((Integer.parseInt(di.substring(4,5))+3)<<4|(Integer.parseInt(di.substring(5,6))+3)), //DI3
            (byte)((Integer.parseInt(di.substring(6,7))+3)<<4|(Integer.parseInt(di.substring(7,8))+3)), //DI4
            (byte)SEQ,
            0x00, //CS
            0x16
        };
        bt[bt.length-2] = ToolUtil.getCS(bt);
        return bt;
    }
}
