package com.example.e_commerceadmin.ui.home.myHome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentHomeBinding
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.myHome.viewModel_home.HomeFactory
import com.example.e_commerceadmin.ui.home.myHome.viewModel_home.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel



    lateinit var binding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var factory: HomeFactory =
            HomeFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]

        binding= FragmentHomeBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_allProductFragment)
        }
        binding.cardCoupons.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_allRulesFragment)
        }
        observeCountOfProduct()

    }


    private fun observeCountOfProduct(){

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productCount.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.productCard.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productCard.visibility = View.VISIBLE
                        binding.countValue.text = uiState.data.count.toString()
                    }
                    is UiState.Failed -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productCard.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),"faild",Toast.LENGTH_SHORT).show()
                        Log.e ("HomeFragment","Error: ${uiState.msg.message}")
                    }
                }
            }
        }
    }


}