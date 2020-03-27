package teste.capgemini

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        minha_webview.settings.javaScriptEnabled = true
        minha_webview.addJavascriptInterface(JavaScriptInterface(), JAVASCRIPT)
        minha_webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == BASE_URL) {
                    injectJavaScriptFunction()
                }
            }
        }
        minha_webview.loadUrl(BASE_URL)

        btOkNativo.setOnClickListener {
                minha_webview.evaluateJavascript("javascript: " +
                        "updateFromAndroid(\"" + editTextNativo.text + "\")", null)
        }
    }

    override fun onDestroy() {
        minha_webview.removeJavascriptInterface(JAVASCRIPT)
        super.onDestroy()
    }

    private fun injectJavaScriptFunction() {
        minha_webview.loadUrl("javascript: " +
                "window.androidObj.textoparaandroid = function(message) { " +
                JAVASCRIPT + ".textFromWeb(message) }")
    }


    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun textFromWeb(fromWeb: String) {
            txt_webfinal.text =  fromWeb
        }
    }

    companion object {
        private val JAVASCRIPT = "JAVASCRIPT"
        private val BASE_URL = "file:///android_asset/webview.html"
    }
}