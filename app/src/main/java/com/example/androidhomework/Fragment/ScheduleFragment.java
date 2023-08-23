package com.example.androidhomework.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.androidhomework.Dao.SimpleNEUClassDao;
import com.example.androidhomework.Entity.SimpleNEUClass;
import com.example.androidhomework.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private View mainView;
    private Context context;
    private SimpleNEUClassDao simpleNEUClassDao;

    private WebView webView = null;
    private Button webViewLoginSuccessButton = null;
    private Boolean isClassPage = false;
    private Boolean isClassLoadSuccess = false;
    private String classDataString = null; //用于存储课程数据字符串，可以将其存储到文件，无需转换
    private List<SimpleNEUClass> classArray = null; //课程数组
    private String classPageUrl;
    private String st;
    //忽略Java的XSS注入提示
    @SuppressLint("SetJavaScriptEnabled")

    public static boolean Is_Use_school_Net() {
        URL url = null;
        Boolean is_connection = false;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://219.216.96.4/eams");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(1000);
            connection.connect();
            is_connection = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is_connection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        simpleNEUClassDao = SimpleNEUClassDao.getInstance();
        try {
            if(simpleNEUClassDao.getClassArray() != null){



            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        mainView = inflater.inflate(R.layout.fragment_schedule, container, false);
        context = mainView.getContext();

        // 代码起始点 B --------------

        //绑定元素
        webView = mainView.findViewById(R.id.webview);
        webViewLoginSuccessButton = mainView.findViewById(R.id.webviewLoginSuccessButton);
        //清除Cookie，避免重复登录
        CookieManager.getInstance().removeAllCookies(value -> {});
        //按钮点击事件
        webViewLoginSuccessButton.setOnClickListener(v -> {
            isClassPage = true;

            webView.loadUrl(classPageUrl);
        });
        final String postDataBytes = ("ignoreHead=1&showPrintAndExport=1&setting.kind=std&s" +
                "tartWeek=&semester.id=57&ids=74104");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Is_Use_school_Net() == true){
                    st = "http://219.216.96.4/eams/homeExt.action";
                    classPageUrl = "http://219.216.96.4/eams/courseTableForStd!courseTable.action"+"?"+postDataBytes;

                }else {
                    st = "https://webvpn.neu.edu.cn/http/77726476706e69737468656265737421a2a618d275613e1e275ec7f8/eams/homeExt.action";
                    classPageUrl = "https://webvpn.neu.edu.cn/http/77726476706e69737468656" +
                            "265737421a2a618d275613e1e275ec7f8/eams/courseTableForStd!courseTable.a" +
                            "ction?vpn-12-o1-219.216.96.4" + "?" + postDataBytes;

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(st);
                    }
                });
            }
        }).start();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.setWebViewClient(new WebViewClient() {
            //拦截跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            //处理HTTPS
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }


            //处理POST页面加载完成，其他页面不搭理
            @Override
            public void onPageFinished(WebView view, String url) {
                int i = 0;
                if (isClassPage) {
                    isClassPage = false;//复原变量，以免处理失败用户重试的情况
                    //不要试图修改下面这一长串东西
                    final String javaScript = "(function scheduleHtmlParser(){let t=document.getE" +
                            "lementsByTagName(\"html\")[0].innerHTML,e=[];var i=/(?<=var[ \\t]" +
                            "*actTeachers[ \\t]*=.*name:[ \\t]*\\\").*?(?=\\\")/,n=/(?<=activi" +
                            "ty[\\t ]*=[ \\t]*new[\\t ]*TaskActivity\\(.*\\\"[0-9A-Za-z]{4,10}" +
                            "\\([0-9A-Za-z]{4,10}\\)\\\"[\\t ]*,[\\t ]*\\\").*?(?=\\\")/,a=/(?" +
                            "<=activity[\\t ]*=[\\t ]*new[\\t ]*TaskActivity\\(([^\"]*?\\\"){7" +
                            "})[^\"]*?(?=\\\")/,c=/(?<=\\\")[0-1]{53}(?=\\\")/,r=/index[\\t ]" +
                            "*=[\\t ]*[0-6][\\t ]*\\*[\\t ]*unitCount[\\t ]*\\+[\\t ]*[0-9][0" +
                            "-1]?[\\t ]*;/g,s=/(?<=index[\\t ]*=[\\t ]*)[0-6](?=[\\t ]*\\*)/,h=" +
                            "/(?<=index[\\t ]*=[\\t ]*.*?unitCount[\\t ]*\\+[\\t ]*)[0-9][0-1" +
                            "]?/,m=String(t).match(/var[ \\t]*teachers[\\t ]*=[\\t ]*[\\s\\S]*" +
                            "?(?=table0\\.activities\\[index\\]\\[table0\\.activities\\[index\\" +
                            "]\\.length\\][ \\t]*=[\\t ]*activity;[\\r\\n\\t ]*[vt])/g);for(l" +
                            "et t=0;t<m.length;t++){const o=m[t];let u={name:String,position:" +
                            "String,teacher:String,weeks:[],day:Number,sections:[]};u.name=o.m" +
                            "atch(n)[0],u.position=o.match(a)[0],u.teacher=o.match(i)[0],u.da" +
                            "y=Number(o.match(s)[0])+1;let g=o.match(c)[0];for(let t=0;t<g.le" +
                            "ngth;t++)\"1\"==g[t]&&u.weeks.push(t);var l=o.match(r);for(let t" +
                            "=0;t<l.length;t++){const e=l[t];u.sections.push(Number(e.match(h)" +
                            "[0])+1)}e.push(u)}return JSON.stringify(e)})()";
                    //开始获取
                    webView.evaluateJavascript(
                            javaScript,
                            value -> {
                                classDataString = value.substring(1, value.length() - 1)
                                        .replace("\\", "");
                                try {
                                    classArray =
                                            JSONObject.parseArray(
                                                    classDataString,
                                                    SimpleNEUClass.class
                                            );
                                }catch (Exception e){
                                    Toast.makeText(
                                            null,
                                            "失败！！！！！！！11",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                if (classArray != null){
                                    isClassLoadSuccess = true;
                                }else{
                                    Toast.makeText(
                                            null,
                                            "失败！！！！！！！22",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                try {
                                    simpleNEUClassDao.setClassArray(classArray);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                for (SimpleNEUClass s :
                                        classArray) {
                                    Log.e("Class Information",
                                            "  " + System.lineSeparator() + s);
                                }
                                Toast.makeText(
                                        context,
                                        "获取成功",
                                        Toast.LENGTH_LONG
                                ).show();








                                //请在上面写你的逻辑，我是底线
                            }
                    );
                }
            }
        });

        // 代码终止点 B --------------


        return mainView;
    }
}