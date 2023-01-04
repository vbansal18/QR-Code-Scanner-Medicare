package com.example.anveshnaqrscanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anveshnaqrscanner.databinding.ActivityMainBinding
import com.example.anveshnaqrscanner.model.MedicineDetails
import com.example.anveshnaqrscanner.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    private val jsonAdapter: JsonAdapter<MedicineDetails> by lazy { moshi.adapter(MedicineDetails::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.qrButton.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.setPrompt("Scan QR Code")
            intentIntegrator.initiateScan()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if(result != null) {
            if (result.contents != null) {
                try {
                    val medicineDetails = jsonAdapter.fromJson(result.contents) ?: throw Exception()

                    AlertDialog.Builder(this)
                        .setMessage("Do you want to add this medicine?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.postMedDetails(medicineDetails).observe(this@MainActivity) { msg ->
                                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        .setNegativeButton("No") { _, _ -> }
                        .create()
                        .show()

                } catch (e: Exception) {
                    Snackbar.make(binding.root, "Invalid QR", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}