package com.lr.framework.webmvc.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrView
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/4 10:52
 */
public class LrView {

    public final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    File viewFile;

    public LrView(File file) {
    this.viewFile = file;
    }

    public void render( Map<String,?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception{

        StringBuffer sb = new StringBuffer();

        RandomAccessFile ra = new RandomAccessFile(this.viewFile,"r");
       String line = null;
        Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
        while (null!=(line = ra.readLine())){
            line = new String(line.getBytes("ISO-8859-1"),"utf-8");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()){
                String paramName = matcher.group();
                paramName = paramName.replaceAll("￥\\{|\\}", "");

                Object paramValue = model.get(paramName);

                if(paramValue!=null){
                    line = matcher.replaceFirst(paramValue.toString());
                    matcher = pattern.matcher(line);
                }
            }
            sb.append(line);
        }


        resp.setCharacterEncoding("utf-8");
        resp.setContentType(DEFAULT_CONTENT_TYPE);
        resp.getWriter().write(sb.toString());
    }
}
