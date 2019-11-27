package com.example.mrdiscount

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class Scanner : AppCompatActivity(), ZXingScannerView.ResultHandler  {

    var REQUEST_CAMERA = 1
    lateinit var scannerView: ZXingScannerView
    //val camID = android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
    //var verified = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scannerView = ZXingScannerView(this)

        setContentView(scannerView)

        var currAppVer = Build.VERSION.SDK_INT

        if (currAppVer >= Build.VERSION_CODES.M){
            if(checkPermission()){

            }
            else {
                requestPermission()
            }
        }
    }

    private fun checkPermission() = ContextCompat.checkSelfPermission(applicationContext, CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_CAMERA)
    }

    override fun onResume(){
        super.onResume()

        var currApiVer = Build.VERSION.SDK_INT

        if (currApiVer == Build.VERSION_CODES.M){
            if (checkPermission()){
                startScanning()
            }
            else{
                requestPermission()
            }
        }
        else{
            startScanning()
        }
    }

    private fun startScanning() {
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    /*fun onRequestPermissionResult(requestCode: Int, permissions: String, grantResults: Array<Int>){
        when(requestCode){
            REQUEST_CAMERA ->
                if (grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted){
                        Toast.makeText(applicationContext, "da",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "net",Toast.LENGTH_SHORT).show()

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(CAMERA)){
                                AlertDialog.Builder(this)
                                    .setMessage("Ta idi nahui")
                                    .setPositiveButton("OK"){dialog, which ->
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            requestPermissions(arrayOf(CAMERA), REQUEST_CAMERA)
                                        }
                                    }
                                    .setNegativeButton("FUCK", null)
                                    .create()
                                    .show()
                                return
                            }
                        }
                    }
                }
        }
    }*/

    override fun handleResult(result: Result) { // result
        val myResult = result.text.toString()
        val intent = Intent(this, MainActivity::class.java)
            .putExtra("resultName", myResult)
        startActivity(intent)
    }
}
