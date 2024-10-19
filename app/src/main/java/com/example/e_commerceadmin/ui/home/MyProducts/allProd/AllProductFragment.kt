package com.example.e_commerceadmin.ui.home.MyProducts.allProd

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.e_commerceadmin.databinding.FragmentAllProductBinding
import com.example.e_commerceadmin.databinding.FragmentHomeBinding
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdFactory
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdViewModel
import com.example.e_commerceadmin.ui.home.myHome.viewModel_home.HomeViewModel
import kotlinx.coroutines.launch
import java.util.Locale



class AllProductFragment : Fragment() {
    lateinit var viewModel: AllProdViewModel
    lateinit var binding: FragmentAllProductBinding
    lateinit var allProductAdapter: AllProductAdapter
    lateinit var productList: List<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var factory: AllProdFactory =
            AllProdFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[AllProdViewModel::class.java]

        binding= FragmentAllProductBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allProductAdapter = AllProductAdapter(
            onClick = { productItem ->

                val action =AllProductFragmentDirections.actionAllProductFragmentToProductInfoFragment(productItem)
                    //.also
               // { it.prodDetails=productItem }
                findNavController().navigate(action)

            },
            onDeleteClick = { productItem ->

            }
        )
        binding.floatAddNewProd.setOnClickListener {
            findNavController().navigate(R.id.action_allProductFragment_to_newProductFragment)
        }
        binding.productRecyclerView
            .apply { adapter=allProductAdapter}


        binding.searchProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                val filteredList = productList.filter {
                    it.title?.toLowerCase(Locale.getDefault())!!.contains(searchText) ||
                            it.productType?.toLowerCase(Locale.getDefault())!!.contains(searchText) ||
                            it.vendor?.toLowerCase(Locale.getDefault())!!.contains(searchText)
                }

                if (filteredList.isEmpty()) {
                    binding.noList.visibility = View.VISIBLE
                } else {
                    binding.noList.visibility = View.GONE
                }

                allProductAdapter.submitList(filteredList)
            }
        })

        observeAllProductList()

    }


    private fun observeAllProductList(){

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allProduct.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        productList=uiState.data.getProducts
                        allProductAdapter.submitList(productList)


                    }
                    is UiState.Failed -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"faild fetch all product", Toast.LENGTH_SHORT).show()
                        Log.e ("AllProdFragment","Error: ${uiState.msg.message}")
                    }
                }
            }
        }
    }

}