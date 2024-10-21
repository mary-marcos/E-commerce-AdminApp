package com.example.e_commerceadmin.ui.home.coupons.zDiscounts.AllDiscounts

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentAllDiscountsBinding
import com.example.e_commerceadmin.databinding.FragmentAllRulesBinding
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.OneItemCode
import com.example.e_commerceadmin.model.ProductModel.ImagesItem
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.ImagesAdapter
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.AllRulesAdapter
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesFactory
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesViewModel
import com.example.e_commerceadmin.ui.home.coupons.zDiscounts.ViewMode.DiscountFactory
import com.example.e_commerceadmin.ui.home.coupons.zDiscounts.ViewMode.DiscountViewMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random


class AllDiscountsFragment : Fragment() {
    lateinit var viewModel: DiscountViewMode
    lateinit var binding: FragmentAllDiscountsBinding
    lateinit var allDisAdapter: AllDisAdapter
      var ruleIdd:Long=0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var factory: DiscountFactory =
            DiscountFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[DiscountViewMode::class.java]

        binding= FragmentAllDiscountsBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allDisAdapter = AllDisAdapter(
            onClick = { ruleItem ->

//                val action = AllRulesFragmentDirections.actionAllRulesFragmentToUpdateRuleFragment(ruleItem)
//                findNavController().navigate(action)

            },
            onItemClicked = { ruleItemID ->
//                val action = AllRulesFragmentDirections.actionAllRulesFragmentToAllDiscountsFragment(ruleItemID)
//                findNavController().navigate(action)
            },

            onDeleteClick = { disItem ->
                observeDeleteDisc()
                viewModel.deleteDiscount(ruleIdd,disItem.id?:0L)
            }
        )


        binding.rulesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allDisAdapter
        }




        observeGetAllDiscounts()
        var args=AllDiscountsFragmentArgs.fromBundle(requireArguments())
        ruleIdd=args.ruleId
       viewModel.getAllDiscounts(ruleIdd)

        binding.addFloatingBtn.setOnClickListener {

            showCreateDiscountDialog()
        }

    }





    private fun observeDeleteDisc() {



        lifecycleScope.launch {
            viewModel.deleteDiscount.collectLatest{
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
                        viewModel.getAllDiscounts(ruleIdd)

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
    private fun observeGetAllDiscounts() {



            lifecycleScope.launch {
                viewModel.allDiscounts.collectLatest{
                    when (it) {
                        is UiState.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressbar.visibility = View.GONE
                            allDisAdapter.submitList(it.data)
                        }
                        else -> {
                            Toast.makeText(requireContext(),"fail get all discounts",Toast.LENGTH_SHORT).show()
                            binding.progressbar.visibility = View.GONE
                        }
                    }
                }
            }
        }

    private fun observingCreateCoupone(){
        lifecycleScope.launch {
            viewModel.createcopon.collectLatest{
                when (it) {
                    is UiState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressbar.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            requireView(),
                            "Coupon added successfully",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        viewModel.getAllDiscounts(ruleIdd)

                      //  Toast.makeText(requireContext(),"success add code coupone ",Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        val snackbar = Snackbar.make(
                            requireView(),
                            "add Coupon failed",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                      //  Toast.makeText(requireContext(),"fail get all discounts",Toast.LENGTH_SHORT).show()
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
        }
    }



    private fun showCreateDiscountDialog() {
       var dialog = Dialog(requireActivity()).apply {

            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.add_discount_dialog)


            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT // Wrap height
            )

            var cancelBtn = findViewById<Button>(R.id.cancel_button)
            cancelBtn.setOnClickListener {
                dismiss()
            }
            var addedCode=findViewById<EditText>(R.id.code_text)



            var dialogImageView = findViewById<Button>(R.id.add_code)
            dialogImageView?.setOnClickListener {
                if(!addedCode.text.isNullOrBlank()){
                    observingCreateCoupone()

                   viewModel.createCoupones(ruleIdd, DiscountCodeRequest(OneItemCode(addedCode.text.toString())))

                     dismiss()
                    addedCode.text.clear()
                    allDisAdapter.notifyDataSetChanged()




                }
            }

            show()
        }
    }


}