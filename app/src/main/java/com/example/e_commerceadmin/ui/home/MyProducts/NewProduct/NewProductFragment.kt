package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.constant.Helpers.GetTime
import com.example.e_commerceadmin.databinding.FragmentAllProductBinding
import com.example.e_commerceadmin.databinding.FragmentNewProductBinding
import com.example.e_commerceadmin.model.ProductModel.Image
import com.example.e_commerceadmin.model.ProductModel.ImagesItem
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.ProductModel.OptionsItem
import com.example.e_commerceadmin.model.ProductModel.Product
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.example.e_commerceadmin.model.ProductModel.VariantsItem
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel.PostProdFactory
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel.PostProdViewModel
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel.UpdateProductFactory
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel.UpdateProductViewModel
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.AllProductAdapter
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdFactory
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdViewModel

import kotlin.random.Random


class NewProductFragment : Fragment() {
  lateinit var viewModel: PostProdViewModel
    lateinit var updateviewModel:UpdateProductViewModel
  lateinit var binding: FragmentNewProductBinding
    private var dialog: Dialog? = null
    private var dialogvariant: Dialog? = null
    var isupdate=false

    var variantsList: MutableList<VariantsItem> = mutableListOf()
   var images:MutableList<ImagesItem> =mutableListOf()
  var category="ACCESSORIES"
    var brand= "PUMA"

    val categories = listOf( "ACCESSORIES",
        "T-SHIRTS",
        "SHOES")
    val brands = listOf("VANS", "PUMA", "TIMBERLAND", "SUPRA", "NIKE",
        "PALLADIUM", "HERSCHEL", "FLEX FIT", "DR. MARTENS", "ADIDAS",
        "CONVERSE", "ASICS TIGER")



  

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var factory: PostProdFactory =
            PostProdFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[PostProdViewModel::class.java]
        var updatefactory: UpdateProductFactory =
            UpdateProductFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        updateviewModel= ViewModelProvider(this,updatefactory)[UpdateProductViewModel::class.java]

        binding= FragmentNewProductBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setupBrandSpinnerr()
        setupCategorySpinner()
        val args= NewProductFragmentArgs.fromBundle(requireArguments())
        if (args!= null) {
            isupdate = args.isUpdate
            if(isupdate){
                stupDataToUpdate(args.productItem)
           }}
        binding.addSpecficationsBtn.setOnClickListener{if (isupdate){showAddvariantsDialog(args.productItem)}else{ showAddvariantsDialog(null)}}
        binding.addImgBtn.setOnClickListener {showAddImageDialog()}


        binding.progressBar

        binding.addNewProductBtn.setOnClickListener {
           if (isupdate){test(args.productItem)}else{ test(null)}

        }

        
    }

    private fun stupDataToUpdate(product:ProductItem?) {
        binding.titleText.setText(product?.title)

        binding.descriptionEt.setText(product?.bodyHtml)
        val categoryPosition = categories.indexOf(product?.productType)
        if (categoryPosition >= 0) {
            binding.spinnerCategory.setSelection(categoryPosition)
        }
        val brandPosition = brands.indexOf(product?.vendor)
        if (brandPosition >= 0) {
            binding.spinnerBrand.setSelection(brandPosition)
        }
        product?.images?.let { imagesList ->
            images.addAll(imagesList)
        }
        product?.variants?.let { variant->variantsList.addAll(variant) }



    }

    fun isValidData(): Boolean {
        var isValid = true

     if(variantsList.isEmpty()){
       isValid = false
     Toast.makeText(requireContext(),"at least one variant with price required",Toast.LENGTH_SHORT).show()
}
        if(images.isEmpty()){
            isValid = false
            Toast.makeText(requireContext(),"at least one image for poster required",Toast.LENGTH_SHORT).show()

        }

        if (binding.titleText.text.isNullOrBlank()) {
            binding.titleText.error = "Title is required"
            isValid = false
        } else {
            binding.titleText.error = null
        }



        if (binding.descriptionEt.text.isNullOrBlank()) {
            binding.descriptionEt.error = "Description is required"
            isValid = false
        } else {
            binding.descriptionEt.error = null
        }

        return isValid
    }


    fun test(productitem:ProductItem?){
if( isValidData()){
    if(isupdate){
        updateProduct()
    }else
    { uploadCreateProduct()}

val time=GetTime.getCurrentTime()
    val productId =  Random.nextLong()
        val image_id = Random.nextLong()


    val product = Product(
        image = Image(
            updatedAt = time,
            src =images[0].src,
            productId =productitem?.id?: productId,
            adminGraphqlApiId = "gid://shopify/ProductImage/${image_id}",
            alt = null,
            width = 123,
            createdAt = time,
            variantIds = null,
            id = image_id,
            position = 1,
            height = 459
        ),
        bodyHtml = binding.descriptionEt.text.toString(),
        images = images,

        createdAt = time,
        handle = "burton-custom-freestyle-151",
        variants = variantsList,
        title = binding.titleText.text.toString(),
        tags = "",
        publishedScope = "",
        productType = category,
        templateSuffix = "",
        updatedAt = time,
        vendor = brand,
        adminGraphqlApiId = "gid://shopify/Product/${productitem?.id?:productId}",
        options = arrayOf(
            OptionsItem(
                id = Random.nextLong(4),
                productId = productitem?.id?:productId,
                name = "Size",
                position = 1,
                values = arrayOf(
                    variantsList[0].option1
                )
            ),
            OptionsItem(
                id = Random.nextLong(4),
                productId =productitem?.id?: productId,
                name = "Color",
                position = 2,
                values = arrayOf(
                    variantsList[0].option2
                )
            )
        ).toList(),
        id = productitem?.id?:productId,
        publishedAt = "",
        status = "draft"
    )


    if(isupdate){

        updateviewModel.updateProduct(product.id?:0, OneProductsResponse(product))
    }
    else{ viewModel.createProduct(ProductBody(product))}}




    }

    private fun showAddImageDialog() {
        dialog = Dialog(requireActivity()).apply {

            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.add_images_dialog)


            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT // Wrap height
            )

            var cancelBtn = findViewById<Button>(R.id.confirm_button_img)
            cancelBtn.setOnClickListener {
                dismiss()
            }
            var urlimg=findViewById<EditText>(R.id.imgs_text)
            var recyclerimg=findViewById<RecyclerView>(R.id.images_recycler_view_d)


            recyclerimg.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val imgadapter = ImagesAdapter()
            recyclerimg.adapter = imgadapter

            imgadapter.submitList(images)
            Log.d("TAG", "showAddImageDialogimg list: $images")


            var dialogImageView = findViewById<Button>(R.id.add_toimgslist)
            dialogImageView?.setOnClickListener {
                if(!urlimg.text.isNullOrBlank()){
                   images.add( ImagesItem(
                        id = Random.nextLong(6),
                        // productId = "productId",
                        src = urlimg.text.toString()
                    )
                 )



                    urlimg.text.clear()
                    imgadapter.submitList(images.toList())
                    imgadapter.notifyDataSetChanged()


                }
            }

            show()
        }
    }

    private fun showAddvariantsDialog(broductItem:ProductItem?) {
        dialogvariant = Dialog(requireActivity()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.add_variants_dialog)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            var cancelBtn = findViewById<Button>(R.id.confirm_button)
            cancelBtn.setOnClickListener {
                dismiss()
            }

            var price=findViewById<EditText>(R.id.price_text)
            var quantity=findViewById<EditText>(R.id.quantity_text)
            var color=findViewById<EditText>(R.id.color_text)
            var size=findViewById<EditText>(R.id.psize_text)
            var recyclervariant=findViewById<RecyclerView>(R.id.variant_recycler_view_d)



            recyclervariant.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val vatiantadapter = variantAdapter()
            recyclervariant.adapter = vatiantadapter

            vatiantadapter.submitList(variantsList)



            var dialogImageView = findViewById<Button>(R.id.add_tovariantlist)
            dialogImageView?.setOnClickListener {

                if(!price.text.isNullOrBlank()){
                    val variantsId = Random.nextLong(6)


                    variantsList.add(

//




                       VariantsItem(
                        id = variantsId,
                        product_id = broductItem?.id ?: 0L,
                        title = "${size.text?.toString()}/${color.text?.toString()}",
                        price = price.text.toString(),
                        sku = "",
                        position = variantsList.size + 1,
                        inventory_policy = "deny",
                        compare_at_price = null,
                        fulfillment_service = "manual",
                        inventory_management = "shopify",
                        option1 = size.text?.toString(),
                        option2 = color.text?.toString(),
                        option3 = null,
                        created_at = GetTime.getCurrentTime(),
                        updated_at = GetTime.getCurrentTime(),
                        taxable = true,
                        barcode = null,
                        grams = 0,
                        weight = 0.0,
                        weight_unit = "kg",
                        inventory_item_id = 47449209635051,
                        inventory_quantity = 9,
                        old_inventory_quantity = 9,
                        requires_shipping = true,
                        admin_graphql_api_id = "gid://shopify/ProductVariant/${variantsId}",
                        image_id = null
                    )



                    )
                    size.text.clear()
                    color.text.clear()
                    price.text.clear()
                    quantity.text.clear()
                    vatiantadapter.submitList(variantsList.toList())
                    vatiantadapter.notifyDataSetChanged()
                }else {
                    price.error = "price required"
                }

            }

            show()
        }
    }

   fun uploadCreateProduct() {
        lifecycleScope.launchWhenStarted {
            viewModel.createProductState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        // Handle success state
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Product Created Successfully",Toast.LENGTH_SHORT).show()
                      //  findNavController().navigate(R.id.action_createProductFragment_to_home)
                       // makeAlert("Product Created Successfully", "Creating new Product to our store is done")
                    }
                    is UiState.Failed -> {
                        // Handle error state
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Failed to create product",Toast.LENGTH_SHORT).show()

                        //  makeAlert("Failed to create product", "Make sure about your connection to create new Product.")
                    }
                }
            }
        }}

    fun updateProduct() {
        lifecycleScope.launchWhenStarted {
            updateviewModel.updateProductState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        // Handle success state
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Product updated Successfully",Toast.LENGTH_SHORT).show()
                        //  findNavController().navigate(R.id.action_createProductFragment_to_home)
                        // makeAlert("Product Created Successfully", "Creating new Product to our store is done")
                    }
                    is UiState.Failed -> {
                        // Handle error state
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Failed to update product",Toast.LENGTH_SHORT).show()

                        //  makeAlert("Failed to create product", "Make sure about your connection to create new Product.")
                    }
                }
            }
        }}


    private fun setupCategorySpinner() {


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spinnerCategory.adapter = adapter


        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                 category = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }}

    private fun setupBrandSpinnerr() {


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brands)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spinnerBrand.adapter = adapter


        binding.spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                brand = parent.getItemAtPosition(position).toString()
Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }}
}


