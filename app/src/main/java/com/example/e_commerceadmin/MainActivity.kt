package com.example.e_commerceadmin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerceadmin.MainViewModelAuth.FactoryAuth
import com.example.e_commerceadmin.MainViewModelAuth.ViewModelAuth
import com.example.e_commerceadmin.databinding.ActivityMainBinding
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.HomeActivity
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdFactory
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var factory: FactoryAuth =
            FactoryAuth(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this, factory)[ViewModelAuth::class.java]


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            observeUserList()
            //"admin123@gmail.com"
           if(isValidEmail(binding.EmailText.text.toString())) {

            viewModel.getadmin(binding.EmailText.text.toString())
           }else{ val snackbar = Snackbar.make(
               binding.root,
               "Invalid email ",
               Snackbar.LENGTH_SHORT
           )
               snackbar.show()}


        }

    }


    private fun observeUserList() {
        lifecycleScope.launch {
            viewModel.allUsers.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {

                       binding.progressBar3.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {

                      binding.progressBar3.visibility = View.GONE
                        val adminList = uiState.data.customers
                        if(adminList.isNotEmpty()){
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val snackbar = Snackbar.make(
                                binding.root,
                                "email is incorrect try again",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }
                       // Toast.makeText(this@MainActivity, "fetchedData=${adminList[0].id}", Toast.LENGTH_SHORT).show()


                    }
                    is UiState.Failed -> {

                       binding.progressBar3.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Failed to fetch all users", Toast.LENGTH_SHORT).show()
                        Log.e("MainActivity", "Error: ${uiState.msg.message}")
                    }
                }
            }
        }
    }

//
private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

}