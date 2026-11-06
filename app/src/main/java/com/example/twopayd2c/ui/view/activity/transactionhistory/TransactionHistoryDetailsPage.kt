package com.example.twopayd2c.ui.view.activity.transactionhistory

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.activity.HelpCenter
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryDataItem
import com.example.twopayd2c.databinding.ActivityTransactionHistoryDetailsPageBinding
import com.example.twopayd2c.ui.view.activity.MyTickets
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.transactionformatDate
import com.example.twopayd2c.utils.SharedPreference

class TransactionHistoryDetailsPage : AppCompatActivity() {

    lateinit var binding : ActivityTransactionHistoryDetailsPageBinding

    lateinit var preference: SharedPreference

    companion object{
        lateinit var transactionHistoryDataItem:TransactionHistoryDataItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransactionHistoryDetailsPageBinding.inflate(layoutInflater)
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

        preference = SharedPreference(this)

        setMenuView()

        setDataOnView()

        setOnClickListner()

    }

    fun setDataOnView(){
        binding.remarks.text = transactionHistoryDataItem.remark
        binding.refId.text = transactionHistoryDataItem.amtTransferTransID
        val formatted = transactionformatDate(transactionHistoryDataItem.recordDateTime!!)
        binding.date.text = formatted
        binding.transferFromMsg.text = transactionHistoryDataItem.transferFromMsg
        binding.transfertoMsg.text = transactionHistoryDataItem.transferToMsg
        binding.openingbalance.text = transactionHistoryDataItem.openingBalance.toString()
        binding.closingbalance.text = transactionHistoryDataItem.closingBalance.toString()
        binding.transactionid.text = transactionHistoryDataItem.amtTransferTransID
        binding.referenceid.text = transactionHistoryDataItem.refTransID
        binding.remark.text = transactionHistoryDataItem.remark
        binding.userName.text = "${transactionHistoryDataItem.firstName} (${transactionHistoryDataItem.transferFrom})"
        binding.receiverNameFirstCharacter.text=transactionHistoryDataItem.firstName!!.take(1).uppercase()
        binding.debitamt.text = "₹ ${transactionHistoryDataItem.transferAmt.toString()} Debited"
        binding.creditamt.text = "₹ ${transactionHistoryDataItem.transferAmt.toString()} Credited"
        binding.contactNumber.text = transactionHistoryDataItem.mobileNo
    }

    fun setMenuView(){
        binding.menuIcon.setOnClickListener {
            val popup = PopupMenu(this,  binding.menuIcon)
            popup.menuInflater.inflate(R.menu.menu_options, popup.menu)

            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.help -> {
                        startActivity(Intent(this, HelpCenter::class.java))
                        true
                    }
                    R.id.raise_ticket -> {
                        // Handle Raise Ticket
                        startActivity(Intent(this, MyTickets::class.java))

                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    fun  setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }
    }

}