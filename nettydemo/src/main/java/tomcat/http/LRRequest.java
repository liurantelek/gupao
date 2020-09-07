package tomcat.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liuran
 * @create 2020-09-07-22:44
 */
public class LRRequest {

    private String URL;

    private String method;

    public LRRequest(InputStream is){
        StringBuffer sb = new StringBuffer ();
            byte[] bytes = new byte[1024];
            int len = 0;
        try {
            while ((len=is.read (bytes))>0){
                String content = new String (bytes,0,len);
                sb.append (content);
            }

            System.out.println (sb.toString ());
            String line = sb.toString ().split ("\\n")[0];
            String[] arr = line.split ("\\s");
            this.URL=arr[1].split ("\\?")[0];
            this.method = arr[0];
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public String getURL() {
        return URL;
    }

    public String getMethod(){
        return method;
    }
}
