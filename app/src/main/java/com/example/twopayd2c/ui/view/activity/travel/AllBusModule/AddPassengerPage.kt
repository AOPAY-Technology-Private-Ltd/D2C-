package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.FutureUseDataListModel
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.databinding.ActivityAddPasangerPageBinding
import com.example.twopayd2c.ui.adapter.FuturePassengerDataSeatDataAdapter
import com.example.twopayd2c.ui.adapter.PassengarSelectedSeatDataAdapter
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson

class AddPassengerPage : AppCompatActivity() , PassengarSelectedSeatDataAdapter.OnClickListener, FuturePassengerDataSeatDataAdapter.OnClickListener {
    lateinit var binding : ActivityAddPasangerPageBinding
    lateinit var adapter : PassengarSelectedSeatDataAdapter
    private var futureusedAdapter : FuturePassengerDataSeatDataAdapter?= null
    var count : Int = 0
    var passengerSeatPosition: Int = 0
    var futureUsedDataList : MutableList<FutureUseDataListModel> = mutableListOf()
    var saveInLoaclDataList : MutableList<FutureUseDataListModel> = mutableListOf()
    var seatNumber : String =""
    var gender : String = ""
    var seatFixedFemalegender : Boolean = false
    var selectedSeatIsFemale = true
    lateinit var preference:SharedPreference
    lateinit var dialog : Dialog


    companion object{
        var seatDataList :MutableList <PassengerSelectedSeatDataModel> = mutableListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddPasangerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val secondaryBlue = ContextCompat.getColor(this, R.color.white)
        binding.main.setBackgroundColor(secondaryBlue)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // APPLY TOP PADDING: This prevents the overlap with the status bar
            view.setPadding(
                systemBars.left,
                systemBars.top,   // This fixes the overlap
                systemBars.right,
                bottomPadding
            )
            insets
        }

        preference= SharedPreference.getInstance(this)!!
        setDataOnView()
        var list = preference.getFutureDataList(ConstantClass.FUTUREDATALIST).toMutableList()
        if(list.isNotEmpty()){
            futureUsedDataList= list
        }
        setDataForFutureuse()
        setOnClickListner()

    }

    fun setDataForFutureuse(){
          if(futureUsedDataList.isNotEmpty()){
              binding.addedPassengerLayout.visibility = android.view.View.VISIBLE
              binding.addPassengerLayout.visibility= View.GONE
              binding.busimage.visibility= View.GONE
              futureusedAdapter = FuturePassengerDataSeatDataAdapter(this,futureUsedDataList,this)
              binding.savedPassengerListShown.adapter = futureusedAdapter
          }
          else{
              binding.addedPassengerLayout.visibility = android.view.View.GONE
              binding.addPassengerLayout.visibility= View.VISIBLE
              binding.busimage.visibility= View.VISIBLE
          }
    }


    fun  setOnClickListner(){

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.passengerName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
               if(binding.passengerName.text.isNotEmpty()&& binding.passengerAge.text.isNotEmpty()){
                   binding.nextbutton.isEnabled = true
                   binding.nextBtn.backgroundTintList = ContextCompat.getColorStateList(this@AddPassengerPage,R.color.primary_blue)
               }else{
                   binding.nextbutton.isEnabled = false
                   binding.nextBtn.backgroundTintList = ContextCompat.getColorStateList(this@AddPassengerPage,R.color.disablebuttoncolor)
               }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        binding.passengerAge.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(binding.passengerName.text.isNotEmpty()&& binding.passengerAge.text.isNotEmpty()){
                    binding.nextbutton.isEnabled = true
                    binding.nextBtn.backgroundTintList = ContextCompat.getColorStateList(this@AddPassengerPage,R.color.primary_blue)
                }else{
                    binding.nextbutton.isEnabled = false
                    binding.nextBtn.backgroundTintList = ContextCompat.getColorStateList(this@AddPassengerPage,R.color.disablebuttoncolor)
                }


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })


        binding.nextBtn.setOnClickListener {
            val name = binding.passengerName.text.toString().trim()
            val age = binding.passengerAge.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter passenger name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (age.isEmpty()) {
                Toast.makeText(this, "Enter passenger age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gender.isNullOrBlank()) {
                Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔹 Prevent duplicate passenger name
            val alreadyExists = futureUsedDataList.any {
                it.userName.equals(name, ignoreCase = true)
            }

            if (alreadyExists) {
                Toast.makeText(this, "Passenger already added", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPassenger = FutureUseDataListModel(
                userName = name,
                age = age,
                gender = gender,
                seatNumber = "" // seat will be assigned later
            )

            saveInLoaclDataList.add(newPassenger)
            preference.saveForFutureData(ConstantClass.FUTUREDATALIST,saveInLoaclDataList)
            futureUsedDataList.add(newPassenger)
            setDataForFutureuse()

            if(futureusedAdapter!=null){
                futureusedAdapter?.updateSelectedSeatGender(seatFixedFemalegender)
            }
            
            Log.d("FutureDataList",Gson().toJson(futureUsedDataList))

            // Optional: clear fields
            binding.passengerName.text?.clear()
            binding.passengerAge.text?.clear()
        }

        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)

            gender = selectedRadioButton.text.toString()

            Log.d("SelectedValue", gender)

        }

        binding.addnewbutton.setOnClickListener {
            binding.addedPassengerLayout.visibility = android.view.View.GONE
            binding.addPassengerLayout.visibility= View.VISIBLE
            binding.busimage.visibility= View.VISIBLE
        }

        binding.continuelayout.setOnClickListener {
            startActivity(Intent(this,ReviewBookingPage::class.java))
        }


    }


    fun setDataOnView(){
        binding.numberOfPassemger.text="0/${seatDataList.size}"
        binding.numberOfPassemger.setTextColor(resources.getColor(R.color.edtsubblack))
        binding.ratingcount.setTextColor(resources.getColor(R.color.edtsubblack))
        adapter = PassengarSelectedSeatDataAdapter(this, seatDataList,this)
        binding.passengershowlist.adapter = adapter
        adapter.updateCheckPosition(0)
        binding.Addtitle.text= "Passenger ${0+1}"
        passengerSeatPosition=0
        var item = seatDataList[0]
        seatNumber = item.seatNumber
        seatFixedFemalegender = item.femaleSeat

        if (item.femaleSeat == true) {
            gender = ConstantClass.FemaleGender
            binding.radiogroup.check(R.id.female)
            binding.male.isEnabled = false
            binding.female.isEnabled = true
            binding.female.isChecked=true

        }
        else {
            gender = ConstantClass.MaleGender
            binding.male.isEnabled = true
            binding.female.isEnabled = true
            binding.male.isChecked=true
        }

    }



    override fun setonclicklistner(item: PassengerSelectedSeatDataModel,position: Int) {
         passengerSeatPosition = position
         binding.Addtitle.text= "Passenger ${position+1}"
         seatNumber = item.seatNumber

        // 🔥 IMPORTANT
        futureusedAdapter?.updateSelectedSeatGender(item.femaleSeat)

        seatFixedFemalegender = item.femaleSeat
        if (item.femaleSeat == true) {
            gender = ConstantClass.FemaleGender
            binding.radiogroup.check(R.id.female)
            binding.male.isEnabled = false
            binding.female.isEnabled = true
            binding.female.isChecked=true

        }
        else {
            gender = ConstantClass.MaleGender
            binding.radiogroup.check(R.id.male)
            binding.male.isEnabled = true
            binding.female.isEnabled = true
            binding.male.isChecked=true
        }
        setDataForFutureuse()

    }



    override fun setForEditonclicklistner(item: FutureUseDataListModel, position: Int) {
        var dataForEdit= item
        var editPosition = position
        openDialogForEditPassengerDataList(dataForEdit,editPosition)
    }



    override fun setForDeleteonclicklistner(item: FutureUseDataListModel, position: Int) {
        if (position !in futureUsedDataList.indices) return

        openDialogForDeletePassengerInDataList(item,position)

    }


    override fun setRadioonclicklistner(item: FutureUseDataListModel, position: Int) {

        if (position !in futureUsedDataList.indices) return
        // 🔹 Remove this seat from any other passenger (if already assigned)
        futureUsedDataList.forEachIndexed { index, model ->
            if (model.seatNumber == seatNumber) {
                futureUsedDataList[index] = model.copy(seatNumber = "")
            }
        }

        // 🔹 If this passenger already had a seat → clear that seat's passenger name
        val oldSeat = futureUsedDataList[position].seatNumber

        if (oldSeat.isNotEmpty()) {

            val oldSeatIndex = seatDataList.indexOfFirst {
                it.seatNumber == oldSeat
            }

            if (oldSeatIndex != -1) {
                seatDataList[oldSeatIndex] = seatDataList[oldSeatIndex].copy(passengerName = "", age = "", gender = "")
            }
        }

        // 🔹 Assign new seat to passenger
        futureUsedDataList[position] = futureUsedDataList[position].copy(seatNumber = seatNumber)

        // 🔹 Update seatDataList with passenger name
        val seatIndex = seatDataList.indexOfFirst {
            it.seatNumber == seatNumber
        }

        if (seatIndex != -1) {
            seatDataList[seatIndex] = seatDataList[seatIndex].copy(passengerName = item.userName, age = item.age, gender = item.gender)
        }

        count=0
        futureUsedDataList.forEachIndexed { index, item ->
            if(item.seatNumber.isNotEmpty()){
                count++
            }
        }
        binding.numberOfPassemger.text="${count}/${seatDataList.size}"
        binding.numberOfPassemger.setTextColor(resources.getColor(R.color.seat_orange))
        binding.ratingcount.setTextColor(resources.getColor(R.color.seat_orange))
        futureusedAdapter?.notifyDataSetChanged()
        adapter.notifyDataSetChanged()
        onResume()
    }


    fun openDialogForEditPassengerDataList(item: FutureUseDataListModel,position: Int){
        dialog = Dialog(this)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passengereditdialoglayout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        var cancel = dialog.findViewById<ImageView>(R.id.cancelimage)
        var updateBtn = dialog.findViewById<LinearLayout>(R.id.updateBtn)
        var radiogroup = dialog.findViewById<RadioGroup>(R.id.radiogroup)
        var passengerName = dialog.findViewById<EditText>(R.id.passengerName)
        var passengerAge = dialog.findViewById<EditText>(R.id.passengerAge)

        if(item.gender.equals(ConstantClass.FemaleGender)){
            radiogroup.check(R.id.female)

        }else{
            radiogroup.check(R.id.male)
        }

        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)

            gender = selectedRadioButton.text.toString()

            Log.d("SelectedValue", gender)

        }

        passengerName.setText(item.userName)
        passengerAge.setText(item.age)

        saveInLoaclDataList = preference.getFutureDataList(ConstantClass.FUTUREDATALIST).toMutableList()

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        updateBtn.setOnClickListener {
            val updatedModel = item.copy(
                userName = passengerName.text.toString(),
                age = passengerAge.text.toString(),
                gender = gender
            )

            // Update list
            futureUsedDataList[position] = updatedModel

            // Update adapter
            futureusedAdapter?.updateItem(position, updatedModel)

            seatDataList.forEachIndexed{
                index,unit->
                if(passengerSeatPosition==index){
                    seatDataList[index] = unit.copy(
                        passengerName = passengerName.text.toString()
                    )
                    adapter.updateItem(passengerSeatPosition,seatDataList[passengerSeatPosition])
                    return@forEachIndexed
                }
            }

            saveInLoaclDataList.forEachIndexed{index,unit->
                if(position==index){
                    val updatedModel = unit.copy(
                        userName = passengerName.text.toString(),
                        age = passengerAge.text.toString()
                    )
                    saveInLoaclDataList[position] = updatedModel
                    preference.saveForFutureData(ConstantClass.FUTUREDATALIST,saveInLoaclDataList)
                    return@forEachIndexed
                }

            }

            dialog.dismiss()
        }

        dialog.show()

    }


    fun openDialogForDeletePassengerInDataList(item: FutureUseDataListModel, position: Int){
        dialog = Dialog(this)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passengrer_removedata_layout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        var cancel = dialog.findViewById<ImageView>(R.id.cancelimage)
        var removeBtn = dialog.findViewById<LinearLayout>(R.id.removeBtn)
        var title = dialog.findViewById<TextView>(R.id.title)

        title.text= "Remove ${item.userName} from saved list"

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        removeBtn.setOnClickListener {
            // 1️⃣ Remove from futureUsedDataList
            futureUsedDataList.removeAt(position)
            futureusedAdapter?.notifyItemRemoved(position)

            // 2️⃣ Clear seat data if linked
            seatDataList.forEachIndexed { index, unit ->
                if (passengerSeatPosition == index) {
                    seatDataList[index] = unit.copy(
                        passengerName = "" ,  // clear name
                        age = "",
                        gender = ""
                    )
                    adapter.updateItem(index, seatDataList[index])
                    count--

                    return@forEachIndexed
                }
            }

            binding.numberOfPassemger.text="${count}/${seatDataList.size}"

            if(count>0){
                binding.numberOfPassemger.setTextColor(resources.getColor(R.color.seat_orange))
                binding.ratingcount.setTextColor(resources.getColor(R.color.seat_orange))
            }else{
                binding.numberOfPassemger.setTextColor(resources.getColor(R.color.edtsubblack))
                binding.ratingcount.setTextColor(resources.getColor(R.color.edtsubblack))
            }

            futureUsedDataList.forEachIndexed { index, item ->
                if(item.seatNumber.isNotEmpty()){
                    count--
                }
            }


            // 3️⃣ Remove from local saved list
            saveInLoaclDataList = preference.getFutureDataList(ConstantClass.FUTUREDATALIST).toMutableList()
            if(position!in saveInLoaclDataList.indices) return@setOnClickListener
            saveInLoaclDataList.removeAt(position)
            preference.saveForFutureData(ConstantClass.FUTUREDATALIST, saveInLoaclDataList)
            onResume()
            dialog.dismiss()

        }

        dialog.show()

    }


    override fun onResume() {
        super.onResume()
        if(count==seatDataList.size){
            binding.continuelayout.visibility=View.VISIBLE
        }
        else{
            binding.continuelayout.visibility=View.GONE
        }
    }

}