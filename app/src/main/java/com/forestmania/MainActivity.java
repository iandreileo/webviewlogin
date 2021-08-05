package com.forestmania;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    //declaram browser-ul
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        browser = (WebView) findViewById(R.id.webview);
        //setarile pentru browser
        browser.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        browser.getSettings().setSupportMultipleWindows(false);
        browser.getSettings().setSupportZoom(false);
        browser.setVerticalScrollBarEnabled(false);
        browser.setHorizontalScrollBarEnabled(false);
//        browser.setBackgroundColor(getResources().getColor(R.color.loader));

        //callback-urile pentru browser
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }


        });

        // clear cache
        browser.clearCache(true);

        final ProgressDialog[] _dialog = new ProgressDialog[1];
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                _dialog[0] = ProgressDialog.show(MainActivity.this, "", "Se încarcă...");
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                _dialog[0].dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                try{
                    _dialog[0].dismiss();
                }catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        // this is necessary for "alert()" to work
        browser.setWebChromeClient(new WebChromeClient());

        // add our custom functionality to the javascript environment
//        browser.addJavascriptInterface(new MyCoolJSHandler(), "Cloud");

        browser.loadUrl("https://forestmania.ro/");
    }
    //setarile pentru butonul de back
    @Override
    public void onBackPressed() {
        if(browser!= null && browser.canGoBack())
            browser.goBack();// if there is previous page open it
        else
            super.onBackPressed();//if there is no previous page, close app
    }
}
