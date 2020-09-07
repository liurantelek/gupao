package com.lr.mvcFrameWork.v1;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: SGCCPlatformMicroservice
 * @ClassName: test
 * @Description: client测试端
 * @Author: 刘然
 * @Date: 2020/7/27 16:01
 */
public class ClientTest {

    private static final String message = "03db9937e9898c5ecec7904bd79f956445";
    private static final String publishKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGjK3GRiU+YVQhSMtbHtZksfvqNy+J/RJR++5My6KhMNfqTkXzzWENOQZllzMn5WGbs84ihMywCYk/KO/fQT8w6lMNT6oMG8AtqdMNG6CK2MD/VnA7HzQRfTBmN6pKkRQ2xRTaLFgjqFeHUcOJzdBdXsxxzwnmfWyMsItnJt5BKQIDAQAB";

    public static void main(String[] args) {
        test();
    }


    private  static void test(){
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient("http://20.196.171.45:9096/supplyService?wsdl");
            // 设置超时时间
            Object[] result = client.invoke("getStoreItemInfo",
                    fillParam("41211011840000001","","","",ip4.getHostAddress()));// 调用webservice 
            System.out.println(result[0]);
//            Node node = NodeUtil.createNode(Util.getStrOfObj(result[0]));
//            Map<String, Object> rowsMap = NodeUtil.convertNodeToMap(node);
//            System.out.println(rowsMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充参数格式
     * @param compCode 单位编码
     * @param vollevel 电压等级
     * @param specialType 专项类型
     * @param updateTime 更新时间
     * @param systemId 系统ip
     * @return
     */
    private static String fillParam(String compCode,String vollevel,String specialType,String updateTime,String systemId){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String formatDate = sdf.format(date);
        StringBuffer xml = new StringBuffer();
        xml.append("<ROOT>");
        xml.append("<BASIC>");
        xml.append("<TOKEN>"+generateKey()+"</TOKEN>");//令牌
        xml.append("<CLIENTIP>"+systemId+"</CLIENTIP>");//客户端ip
        xml.append("<<SYSTIME>>"+formatDate+"</<SYSTIME>>");//调用时间
        xml.append("</BASIC>");
        xml.append("<INFO>");
        if(null != compCode && !"".equals(compCode)){
            xml.append( "<ZXMDW>"+compCode+"</ZXMDW>");
        }
        if(null != vollevel && !"".equals(vollevel)){
            xml.append( "<ZDYDJ>"+vollevel+"</ZDYDJ>");
        }
        if(null != specialType && !"".equals(specialType)){
            xml.append( "<ZXLX>"+specialType+"</ZXLX>");
        }
        if(null != updateTime && !"".equals(updateTime)){
            xml.append( "<UPDATE_TIME>"+updateTime+"</UPDATE_TIME>");
        }
        xml.append("</INFO>");
        xml.append("</ROOT>");
        return xml.toString();
    }

    /**
     * 获取加密后的key
     * @return
     */
    private static String generateKey(){
        try {
            RSAEncrypt.genKeyPair();
            String messageEn = RSAEncrypt.encrypt(message, publishKey);
            return messageEn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
