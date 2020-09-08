package tomcat.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author liuran
 * @create 2020-09-07-22:45
 */
public class LRResponse {
    private OutputStream os;

    public LRResponse (OutputStream os){
        this.os = os;
    }

    public void write(String s) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 OK");
        sb.append("Content-Type: text/html;charset=utf-8");
        sb.append(s);
        try {
            os.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
