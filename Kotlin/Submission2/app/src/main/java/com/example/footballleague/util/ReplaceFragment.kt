package com.example.footballleague.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class ReplaceFragment {

    fun replaceFragment(act: FragmentActivity, frame: Int, fragment: Fragment, data: String = "") {
        val fragmentTransaction = act.supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("data", data)
        fragment.arguments = bundle
        fragmentTransaction.replace(frame, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }
}