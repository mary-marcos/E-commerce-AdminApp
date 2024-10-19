package com.example.e_commerceadmin.ui.home.MyProducts.productinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentNewProductBinding
import com.example.e_commerceadmin.databinding.FragmentProductInfoBinding


class ProductInfoFragment : Fragment() {
   // val args = ProductInfoFragmentArgs.fromBundle(requireArguments())

    lateinit var binding: FragmentProductInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProductInfoBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args=ProductInfoFragmentArgs.fromBundle(requireArguments())
        val product = args.prodDetails

        binding.editProductButton.setOnClickListener {
            val action = ProductInfoFragmentDirections.actionProductInfoFragmentToNewProductFragment()
                .also {it.isUpdate=true
                it.productItem=product}
//            action.setIsUpdate(true)
//            action.setProd
            findNavController().navigate(action)
        }
    }


}