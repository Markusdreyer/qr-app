package no.vipps.md.qr_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        var qrValues = rawResult.text.split(",")

        val intent = Intent(Intent.ACTION_VIEW)
        val url = "vipps://?action=inAppPayment&appID=500094684&amount=${qrValues[0]}&merchantSerialNumber=210823&orderID=${qrValues[1]}"


        intent.setData(Uri.parse(url))
        MainActivity.tvresult!!.setText(intent.toString())
        startActivity(intent)
        onBackPressed()

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }


}