package com.example.tts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var videoView: VideoView

    private val youtubeWatchUrl = "https://www.youtube.com/watch?v=6BODDyZRF6A"
    private val youtubeEmbedUrl = "https://www.youtube.com/embed/kXYiU_JCYtU?playsinline=1&rel=0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multimedia)

        val mainLayout = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de WebView para YouTube
        webView = findViewById(R.id.webViewYouTube)
        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        ws.domStorageEnabled = true
        ws.mediaPlaybackRequiresUserGesture = false
        ws.loadWithOverviewMode = true
        ws.useWideViewPort = true

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()

        val html = """
           <!DOCTYPE html>
           <html>
           <head>
               <meta name="viewport" content="width=device-width, initial-scale=1.0">
               <style>
                   html, body {
                       margin: 0;
                       height: 100%;
                       background: #000;
                   }
                   iframe {
                       width: 100%;
                       height: 100%;
                       border: 0;
                   }
               </style>
           </head>
           <body>
               <iframe
                   src="$youtubeEmbedUrl"
                   allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                   allowfullscreen>
               </iframe>
           </body>
           </html>
       """.trimIndent()

        webView.loadDataWithBaseURL(
            "https://www.youtube.com",
            html,
            "text/html",
            "UTF-8",
            null
        )

        // Botón para abrir en la app de YouTube
        findViewById<Button>(R.id.btnOpenYouTube).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, youtubeWatchUrl.toUri())
            startActivity(intent)
        }

        // Configuración de VideoView para MP4 local
        videoView = findViewById(R.id.videoViewMp4)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Nota: Asegúrate de que el archivo 'demo.mp4' exista en 'res/raw/'
        // Si no existe, esta parte fallará en tiempo de compilación o ejecución.
        try {
            // Intentamos obtener el ID del recurso dinámicamente para evitar errores de compilación si no existe aún
            val resId = resources.getIdentifier("demo", "raw", packageName)
            if (resId != 0) {
                val videoUri: Uri = Uri.parse("android.resource://$packageName/$resId")
                videoView.setVideoURI(videoUri)
                videoView.setOnPreparedListener { mp ->
                    mp.isLooping = false
                    videoView.start()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Manejo del botón Atrás (sustituye a onBackPressed deprecado)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (this@MainActivity::webView.isInitialized && webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}