package com.example.mrdiscount

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var result: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //TODO: database finalCode:data:price
        Toast.makeText(this, getString(R.string.toastHelp), Toast.LENGTH_LONG).show()

        val bundle = intent.extras
        val cheat = "tVQR4BP4jZU4jswV1swVjBQt3VU4jBP3jZl4lZQR5"
        val scan = findViewById<MaterialButton>(R.id.scanButton)
        val generate = findViewById<MaterialButton>(R.id.generateButton)
        val qrCode = findViewById<ImageView>(R.id.imageView)
        val manualId = findViewById<TextInputEditText>(R.id.accountIdEditText)
        val support = findViewById<ImageButton>(R.id.supportButton)
            .setOnClickListener {
                val tgUrl = "https://t.me/kawabata_san"
                val tgIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(tgUrl))
                startActivity(tgIntent)
            }

        generate.setOnClickListener {

            try {
                val finalCode = manualId.text.toString().trim().dropLast(manualId.text.toString().length - 15) //TODO: >debug<
                val accID = finalCode.plus(cheat)

                when(intent) {
                    null -> { }
                    else -> {
                        val formatW = MultiFormatWriter()
                        val bitMatrix: BitMatrix = formatW.encode(accID, BarcodeFormat.QR_CODE, 250, 250)
                        val encoder = BarcodeEncoder()
                        val bitmap: Bitmap = encoder.createBitmap(bitMatrix)

                        qrCode.setImageBitmap(bitmap)
                    }
                }
                debugInfo.text = accID

            } catch (e: Exception) {
                Log.e("Scan", e.toString())
            }

            /*val finalCode = manualID.text.toString().trim().dropLast(manualID.text.toString().length - 15)
            val accID = finalCode.plus(cheat)

            val formatW = MultiFormatWriter()
            val bitMatrix: BitMatrix = formatW.encode(accID, BarcodeFormat.QR_CODE, 250, 250)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(bitMatrix)

            qrCode.setImageBitmap(bitmap)

            debugInfo.text = accID*/
        }

        scan.setOnClickListener  {

            intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

        try {
            result = bundle!!.getString("resultName").toString()
            val finalCode = result.trim().dropLast(result.length - 16)
            val accID = finalCode.plus(cheat)

            when(intent) {
                null -> { }
                else -> {
                    val formatW = MultiFormatWriter()
                    val bitMatrix: BitMatrix = formatW.encode(accID, BarcodeFormat.QR_CODE, 250, 250)
                    val encoder = BarcodeEncoder()
                    val bitmap: Bitmap = encoder.createBitmap(bitMatrix)

                    qrCode.setImageBitmap(bitmap)
                }
            }

            debugInfo.text = accID

        } catch (e: Exception) {
            Log.e("Scan", e.toString())
        }
    }
}