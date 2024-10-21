package com.example.e_commerceadmin.ui.home.coupons.RulesPrice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentAllProductBinding
import com.example.e_commerceadmin.databinding.FragmentAllRulesBinding
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.AllProductAdapter
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.AllProductFragmentDirections

import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdFactory
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdViewModel
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesFactory
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AllRulesFragment : Fragment() {
    lateinit var viewModel: AllRulesViewModel
    lateinit var binding: FragmentAllRulesBinding
    lateinit var allrulesAdapter: AllRulesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onResume() {
        super.onResume()
        viewModel.getAllRules()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var factory: AllRulesFactory =
            AllRulesFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[AllRulesViewModel::class.java]

        binding= FragmentAllRulesBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allrulesAdapter = AllRulesAdapter(
            onClick = { ruleItem ->

                val action = AllRulesFragmentDirections.actionAllRulesFragmentToUpdateRuleFragment(ruleItem)
                findNavController().navigate(action)

            },
            onItemClicked = { ruleItemID ->
                val action = AllRulesFragmentDirections.actionAllRulesFragmentToAllDiscountsFragment(ruleItemID)
               findNavController().navigate(action)
            },

            onDeleteClick = { ruleItem ->
                observeDeleteRule()
                viewModel.deleteRule(ruleItem.id?:0L)
            }
        )


        binding.rulesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allrulesAdapter
        }


        binding.addFloatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_allRulesFragment_to_createRuleFragment)
        }
        observeGetAllPriceRules()
    }
    private fun observeGetAllPriceRules() {


        lifecycleScope.launch {
            viewModel.allRules.collectLatest{
                when (it) {
                    is UiState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressbar.visibility = View.GONE
                        allrulesAdapter.submitList(it.data)
                    }
                    else -> {
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun observeDeleteRule() {



        lifecycleScope.launch {
            viewModel.deleteRule.collectLatest{
                when (it) {
                    is UiState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressbar.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            requireView(),
                            it.data,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        viewModel.getAllRules()

                    }
                    else -> {
                        binding.progressbar.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            requireView(),
                            "faile to delete",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                    }
                }
            }
        }
    }

}