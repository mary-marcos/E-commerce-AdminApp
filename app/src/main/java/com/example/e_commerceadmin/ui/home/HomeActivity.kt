package com.example.e_commerceadmin.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.constant.Helpers.NetworkChangeListener

class HomeActivity : AppCompatActivity() {

    private lateinit var networkChangeListener: NetworkChangeListener
    private var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


     // networkChangeListener = NetworkChangeListener
        networkChangeListener = NetworkChangeListener(
            context = this,
            onNetworkAvailable = {

                runOnUiThread {
                    alertDialog?.dismiss()
                    alertDialog = null
                }



            },
            onNetworkLost = {
                runOnUiThread {
                    if (alertDialog == null) {
                        alertDialog = AlertDialog.Builder(this)
                            .setTitle("No Network Connection")
                            .setMessage("A network connection is required to use this app. Please check your network settings and try again.")
                            .setPositiveButton("Ok") { _, _ -> finish() }
                            .setCancelable(false)
                            .create()
                    }
                    alertDialog?.show()
                }
            }
        )



        networkChangeListener.register()



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        networkChangeListener.unregister()
    }
}