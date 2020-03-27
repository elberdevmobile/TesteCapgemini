package teste.capgemini

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mWebViewDemo:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mWebViewDemo = findViewById(R.id.webViewwbv) as WebView
        val myJavaScriptInterface = ButtonClickJavascriptInterface(this@MainActivity)
        mWebViewDemo.addJavascriptInterface(myJavaScriptInterface, "MyFunction")
        mWebViewDemo.getSettings().setJavaScriptEnabled(true)

        mWebViewDemo.loadUrl("file:///android_asset/mypage.html")


    }

    private var mWebView: WebView? = null

    private fun doWebViewPrint() {
        // Create a WebView object specifically for printing
        val webView = webViewwbv
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = false

            override fun onPageFinished(view: WebView, url: String) {

                mWebView = null
            }
        }

        // Generate an HTML document on the fly:
        val htmlDocument =
            "<html>\n" +
                    "<head>\n" +
                    "<title>Page Title</title>\n" +
                    "<style>\n" +
                    "body {\n" +
                    "  background-color: white;\n" +
                    "  text-align: center;\n" +
                    "  color: white;\n" +
                    "  font-family: Arial, Helvetica, sans-serif;\n" +
                    "}\n" +
                    "\n" +
                    ".button {\n" +
                    "  background-color: #cc0000; /* Green */\n" +
                    "  border: none;\n" +
                    "  color: white;\n" +
                    "  padding: 15px 32px;\n" +
                    "  text-align: center;\n" +
                    "  text-decoration: none;\n" +
                    "  display: inline-block;\n" +
                    "  font-size: 16px;\n" +
                    "  margin: 4px 2px;\n" +
                    "  cursor: pointer;\n" +
                    "}\n" +
                    "\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<textarea rows=\"4\" cols=\"50\" placeholder=\"Insira Aqui seu o texto para enviar para o nativo.\"></textarea>\n" +
                    "<button class=\"button\">Enviar</button>\n" +
                    "</body>\n" +
                    "</html>\n"
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView
    }

    class ButtonClickJavascriptInterface internal constructor(c: Context) {
        internal var mContext:Context
        init{
            mContext = c
        }
        @JavascriptInterface
        fun onButtonClick(toast:String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }

}
