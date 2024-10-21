package com.example.e_commerceadmin.ui.home.coupons.createRules

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
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.FragmentAllRulesBinding
import com.example.e_commerceadmin.databinding.FragmentCreateRuleBinding
import com.example.e_commerceadmin.model.CoponsModel.OneRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesFactory
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesViewModel
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleFactory
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleviewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date




class CreateRuleFragment : Fragment() {

    lateinit var viewModel: CreateRuleviewModel
    lateinit var binding: FragmentCreateRuleBinding

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
        var factory: CreateRuleFactory =
            CreateRuleFactory(Repository.getInstance(RemoteProductDataSource.getInstance()))

        viewModel = ViewModelProvider(this,factory)[CreateRuleviewModel::class.java]

        binding= FragmentCreateRuleBinding.inflate(inflater,container,false)

        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addRuleBtn.setOnClickListener {
            if (isDataValidate()) {
                observeingRule()
                viewModel.createRulesf(PriceRuleRequest(createNewRule()))

            }
        }


        DatePricker()
         TimePicker()
        setselectedItemsList()
        SelectedType()
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
            viewModel.createRule.collectLatest {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE

                    }
                    is UiState.Success -> {
                        Toast.makeText(requireContext(),"rule created successfully",Toast.LENGTH_SHORT).show()
                       binding.progressBar2.visibility = View.GONE
                     //   Toast.makeText(requireContext(),"Created Rule successfully",Toast.LENGTH_SHORT).show()

                        val snackbar = Snackbar.make(
                            requireView(),
                            "Created Rule successfully",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()

                    }
                    is UiState.Failed -> {
                       binding.progressBar2.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            requireView(),
                            "faile to Created Rule ",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
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

    private fun isDate(): Boolean{
    startDate = binding.startdateEditText.text.toString() + " " + binding.startTimeEditText.text
    endDate = binding.endsAtEditText.text.toString() + " " + binding.endTimeEditText.text

    if(!binding.endsAtEditText.text.toString().isNullOrEmpty())
    return buildDate(startDate!!)!!.before(buildDate(endDate!!))

    return true
    }

//    private fun isDateValid(): Boolean {
//        val startDate = "${binding.startdateEditText.text} ${binding.startTimeEditText.text}"
//        val endDate = "${binding.endsAtEditText.text} ${binding.endTimeEditText.text}"
//
//        val hasEndDate = !binding.endsAtEditText.text.isNullOrEmpty()
//
//        if (hasEndDate) {
//            return  buildDate(startDate)?.before(buildDate(endDate)) == true
//        }
//        else {
//            return  true
//        }
//    }

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
        if(!isDate()){
            Toast.makeText(requireContext(),"check the End date before start date",Toast.LENGTH_SHORT).show()

            return false
        }

        if(!isPriceValueValid()){
            return false
        }
        return true
    }

    private fun createNewRule(): OneRule {
        val value = (binding.RuleValueTx.text.toString()).toDouble() * -1.0

        return OneRule(
            title = binding.RuleTittle.text.toString(),
            value = value.toString(),
            startsAt = startDate,
            endsAt = endDate,
            valueType = valueType,
            targetType = "line_item",
            allocationMethod = "across",
            targetSelection = "all",
            customerSelection = "all",
        )
    }

}