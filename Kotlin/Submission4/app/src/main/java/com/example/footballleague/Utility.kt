package com.example.footballleague

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.footballleague.fragment.ErrMessageFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    private val bottomSheetDialogFragment: BottomSheetDialogFragment = ErrMessageFragment()

    fun toDate(
        inputDtTime: String,
        dateTimeStr: String = "yyyy-MM-dd HH:mm",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        val parser = SimpleDateFormat(dateTimeStr, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(inputDtTime) as Date
    }

    fun formatTo(
        inputDtTime: Date,
        dateTimeFormat: String = "dd-MMM-yyyy HH:mm",
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formatter = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(inputDtTime)
    }

    fun replaceFragment(act: FragmentActivity, frame: Int, fragment: Fragment, data: String = "") {
        val fragmentTransaction = act.supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("data", data)
        fragment.arguments = bundle
        fragmentTransaction.replace(frame, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    fun errMessage(fragmentManager: FragmentManager, errType: String): Boolean {
        try {
            val b = Bundle()
            b.putString("errType", errType)
            bottomSheetDialogFragment.arguments = b
            if (!bottomSheetDialogFragment.isAdded) {
                bottomSheetDialogFragment.show(fragmentManager, "")
            }
        } catch (e: IllegalStateException) {
            Log.e("ERR_MSG_KEY", e.toString())
        }
        return true
    }
}