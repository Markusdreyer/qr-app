package no.vipps.md.qr_app
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.net.URI

class MainActivity : AppCompatActivity() {

    private var qrBtn: Button? = null
    private var appswitchBtn: Button? = null
    var transactionInfo: Array<String>? = null


    private val TAG = "Permission"
    private val CAMERA_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvresult = findViewById(R.id.tvresult) as TextView

        qrBtn = findViewById(R.id.qrBtn) as Button
        appswitchBtn = findViewById(R.id.appswitchBtn) as Button

        qrBtn!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanActivity::class.java)
            startActivity(intent)
        }


        appswitchBtn!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "vipps://?action=inAppPayment&appID=500094684&amount=5000&merchantSerialNumber=210823&orderID=2345"


            intent.setData(Uri.parse(url))
            MainActivity.tvresult!!.setText(intent.toString())
            startActivity(intent)
        }

        setupPermissions()

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }



    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }


    companion object {

        var tvresult: TextView? = null
    }



}
