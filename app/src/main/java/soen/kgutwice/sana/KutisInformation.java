package soen.kgutwice.sana;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-17.
 */

public class KutisInformation extends AppCompatActivity {

    private String userId = null;
    private String userPw = null;
    Map<String, String> cookies = null;
    private String info=null;

    public KutisInformation(String userId, String userPw){
        this.userId = userId;
        this.userPw = userPw;
    }

    private void setSession() throws InterruptedException {
        //final String URL = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wslogin/loginCheck.jsp";
        final String URL = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wslogin/loginCheck.jsp";
       Thread thread = new Thread(){
            public void run(){
                try {
                    Connection.Response KutisConnection = Jsoup.connect(URL)
                            .data("id",userId)
                            .data("pw",userPw)
                            .method(Connection.Method.POST)
                            .execute();
                    cookies = KutisConnection.cookies();
                    Document d = KutisConnection.parse();
                    System.out.println(d.html());
                    Log.i("test",cookies.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

       thread.start();
       thread.join();
    }

    private void setInfo() throws InterruptedException {
        //final String URL = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wssu3/wssu320s.jsp";
        final String URL = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wslogin/loginCheckInclude.jsp";
        final String URL2 = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wssu3/wssu320s.jsp?m_menu=wsco1s05&s_menu=wssu320s";

        Thread thread = new Thread(){
            public void run(){
                try {
                    Log.i("checkCookie",cookies.toString());
                    Document KutisInformation = Jsoup.connect(URL)
                            .header("Referer",URL)
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
                            .header("Accept", "*/*")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Host","kutis.kyonggi.ac.kr")
                            .cookies(cookies)
                            .get();
                    Log.i("ttt",KutisInformation.html());
                    System.out.println("test : " + KutisInformation.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();

        thread = new Thread(){
            public void run(){
                try {

                    Document KutisInformation = Jsoup.connect(URL2)
                            .header("Referer",URL)
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
                            .header("Accept", "*/*")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Host","kutis.kyonggi.ac.kr")
                            .cookies(cookies)
                            .get();
                    Log.i("ttt",KutisInformation.html());
                    System.out.println("test : " + KutisInformation.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();

    }

    public String getInfo() throws InterruptedException {
        //setSession();
        setInfo();
        return info;
    }
}
