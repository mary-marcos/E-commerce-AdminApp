package com.example.e_commerceadmin.ui.home.MyProducts.productinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentNewProductBinding
import com.example.e_commerceadmin.databinding.FragmentProductInfoBinding
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.variantAdapter


class ProductInfoFragment : Fragment() {
    lateinit var  vatiantadaapter: variantAdapterInfo
    lateinit var  imgsadapte:imagesInfoAdapter
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
        setupDaua(product)

    }

    private fun setupDaua(prod:ProductItem) {
        Glide.with(requireContext())
            .load(prod.image?.src)
            .placeholder(R.drawable.add_prod_main_img)
            .error(R.drawable.search_off)
            .into( binding.imageView2)

       binding.tvTitle.setText(prod.title)
        binding.tvDescription.setText(prod.bodyHtml)
        binding.tvPrice.setText( prod?.variants?.firstOrNull()?.price ?: "0")

        vatiantadaapter = variantAdapterInfo()
        binding.variantRecyclerViewD.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.variantRecyclerViewD.adapter = vatiantadaapter

        vatiantadaapter.submitList(prod.variants?.toList())


        imgsadapte = imagesInfoAdapter()
        binding.imagesRecyclerViewD.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.imagesRecyclerViewD.adapter = imgsadapte

        imgsadapte.submitList(prod.images?.toList())


    }


}