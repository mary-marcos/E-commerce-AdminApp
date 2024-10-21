package com.example.e_commerceadmin.ui.home.coupons.UpdateRule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.constant.Constants
import com.example.e_commerceadmin.constant.Helpers.GetTime
import com.example.e_commerceadmin.databinding.FragmentCreateRuleBinding
import com.example.e_commerceadmin.databinding.FragmentUpdateRuleBinding
import com.example.e_commerceadmin.model.CoponsModel.OneRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.NewProductFragmentArgs
import com.example.e_commerceadmin.ui.home.coupons.UpdateRule.viewModel.ViewModelFactory
import com.example.e_commerceadmin.ui.home.coupons.UpdateRule.viewModel.updateRuleViewModel
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleFactory
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleviewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class UpdateRuleFragment : Fragment() {


    lateinit var viewModel: updateRuleViewModel
    lateinit var binding: FragmentUpdateRuleBinding

    lateinit var rulePriceData: PriceRule

    private var startDate : String? = null
    private var endDate : String? = null
    private var valueType = ""






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var factory: ViewModelFactory =
            ViewModelFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[updateRuleViewModel::class.java]

        binding= FragmentUpdateRuleBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args= UpdateRuleFragmentArgs.fromBundle(requireArguments())

        rulePriceData=args.priceRuleData
        setDatatoUi(args.priceRuleData)


        DatePricker()
        TimePicker()
        setselectedItemsList()
        SelectedType()



            binding.addRuleBtn.setOnClickListener {
                if (isDataValidate()) {
                    observeingRule()
                    updateRule()


                }
            }


    }

    private fun setDatatoUi(rulePriceData:PriceRule) {
        binding.RuleTittle.setText(rulePriceData.title)
        binding.RuleValueTx.setText(((rulePriceData?.value?.substring(1))?.toDouble() ?: 1.0).toString())
        binding.valueTypeSpinner.setText(rulePriceData.value_type)
        binding.startdateEditText.setText(rulePriceData?.starts_at?.substring(0,10))
        binding.endsAtEditText.setText(rulePriceData?.ends_at?.substring(0,10))
        binding.startTimeEditText.setText(rulePriceData?.starts_at?.substring(11,16))
        binding.endTimeEditText.setText(rulePriceData?.ends_at?.substring(11,16))
        valueType = rulePriceData?.value_type!!

    }

    private fun DatePricker() {
        binding.startdateEditText.setOnClickListener {
            showDatePicker(it)
        }
        binding.endsAtEditText.setOnClickListener {
            showDatePicker(it)
        }
    }

    private fun TimePicker() {
        binding.startTimeEditText.setOnClickListener {
            showTimeDialog(it)
        }
        binding.endTimeEditText.setOnClickListener {
            showTimeDialog(it)
        }
    }

    private fun showTimeDialog(view: View) {
        val c2: Calendar = Calendar.getInstance()
        val mHour = c2.get(Calendar.HOUR_OF_DAY)
        val mMinute = c2.get(Calendar.MINUTE)


        val timePickerDialog = TimePickerDialog(
            requireActivity(), { _, hourOfDay, minute ->

                val time = "$hourOfDay:$minute"
                if (view.id == binding.startTimeEditText.id) {
                    binding.startTimeEditText.setText(time)
                } else {
                    binding.endTimeEditText.setText(time)
                }

            }, mHour!!, mMinute!!, true
        )
        timePickerDialog.show()
    }

    private fun showDatePicker(view: View) {
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, year, monthOfYear, dayOfMonth ->

                val strDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                if (view.id == binding.startdateEditText.id) {
                    binding.startdateEditText.setText(strDate)
                } else {
                    binding.endsAtEditText.setText(strDate)
                }
            },
            mYear!!,
            mMonth!!,
            mDay!!
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000;
        datePickerDialog.show()
    }


    private fun setselectedItemsList() {
        val valueTypeList =
            listOf("percentage", "fixed_amount")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.item_selected, R.id.text1,valueTypeList)

        binding.valueTypeSpinner.threshold = 1
        binding.valueTypeSpinner.setAdapter(adapter)
        binding.valueTypeSpinner.setTextColor(Color.BLACK)
    }

    private fun SelectedType() {
        binding.valueTypeSpinner.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                valueType = parent.getItemAtPosition(position).toString()
            }


    }


    private fun observeingRule() {
        lifecycleScope.launch {
            viewModel.updaterule.collectLatest {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE

                    }
                    is UiState.Success -> {
                        val snackbar = Snackbar.make(
                            requireView(),
                            "rule updated successfully",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                       // Toast.makeText(requireContext(),"rule updated successfully", Toast.LENGTH_SHORT).show()
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(),"Created Rule successfully", Toast.LENGTH_SHORT).show()



                    }
                    is UiState.Failed -> {
                        binding.progressBar2.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            requireView(),
                            "update rule failed",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                      //  Toast.makeText(requireContext(),"failed to upload", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }





    private fun isPriceValueValid() : Boolean{
        if(valueType == "percentage") {
            if ((binding.RuleValueTx.text.toString()).toDouble() > 100
                || (binding.RuleValueTx.text.toString()).toDouble() < 1
            ) {
                binding.RuleValueTx.error = "enter price value  between 1 and 100"
                return false
            }
        }else{
            if ((binding.RuleValueTx.text.toString()).toDouble() == 0.0){
                binding.valueInput.error = "price value should  be more than  0"
                return false
            }
        }
        return true
    }



    private fun isDateValid(): Boolean {
        startDate = binding.startdateEditText.text.toString() + " " + binding.startTimeEditText.text
        endDate = binding.endsAtEditText.text.toString() + " " + binding.endTimeEditText.text
        val hasEndDate = !binding.endsAtEditText.text.isNullOrEmpty()

         if (hasEndDate) {
            return buildDate(startDate!!)!!.before(buildDate(endDate!!))

        } else {
             return  true
        }
    }

    fun buildDate(strDate : String) : Date?{
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        try {
            return format.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    private fun isDataValidate(): Boolean {
        if (binding.RuleTittle.text.toString().isNullOrEmpty()) {
            binding.RuleTittle.error = " title is required"
            return false
        }
        if (binding.RuleValueTx.text.toString().isNullOrEmpty()) {
            binding.RuleValueTx.error = "value is required"
            return false
        }


        if (binding.startdateEditText.text.toString().isNullOrEmpty()) {
            binding.startdateEditText.error = " start date is required"
            return false
        }
        if (binding.startTimeEditText.text.toString().isNullOrEmpty()) {
            binding.startTimeEditText.error = " start time is required"
            return false
        }
        if (!binding.endsAtEditText.text.toString().isNullOrEmpty()) {
            if (binding.endTimeEditText.text.toString().isNullOrEmpty()) {
                binding.endTimeEditText.error = " end time is required"
                return false
            }
        }
        if (!binding.endTimeEditText.text.toString().isNullOrEmpty()) {
            if (binding.endsAtEditText.text.toString().isNullOrEmpty()) {
                binding.endsAtEditText.error = " end date is required"
                return false
            }
        }
        if(!isDateValid()){
            Toast.makeText(requireContext(),"check the End date IS before start date", Toast.LENGTH_SHORT).show()

            return false
        }

        if(!isPriceValueValid()){
            return false
        }
        return true
    }

    private fun updateRule() {
        val value = (binding.RuleValueTx.text.toString()).toDouble() * -1.0
        rulePriceData.title = binding.RuleTittle.text.toString()
        rulePriceData.value = value.toString()
        rulePriceData. starts_at = startDate
        rulePriceData. ends_at = endDate
        rulePriceData. value_type = valueType

        viewModel.updateruleF(rulePriceData.id!!, PriceRuleResponsePost(rulePriceData))

    }


}