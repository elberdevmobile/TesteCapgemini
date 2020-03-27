package teste.capgemini

import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_web_view.settings.javaScriptEnabled = true
        my_web_view.addJavascriptInterface(JavaScriptInterface(), JAVASCRIPT_OBJ)
        my_web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == BASE_URL) {
                    injectJavaScriptFunction()
                }
            }
        }
        my_web_view.loadUrl(BASE_URL)

        btOkNativo.setOnClickListener {
            my_web_view.evaluateJavascript("javascript: " +
                    "updateFromAndroid(\"" + editTextNativo.text + "\")", null)
        }
    }

    override fun onDestroy() {
        my_web_view.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }

    private fun injectJavaScriptFunction() {
        my_web_view.loadUrl("javascript: " +
                "window.androidObj.textToAndroid = function(message) { " +
                JAVASCRIPT_OBJ + ".textFromWeb(message) }")
    }


    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun textFromWeb(fromWeb: String) {
            txt_webfinal.text =  fromWeb
        }
    }

    companion object {
        private val JAVASCRIPT_OBJ = "javascript_obj"
        private val BASE_URL = "file:///android_asset/webview.html"
    }
}