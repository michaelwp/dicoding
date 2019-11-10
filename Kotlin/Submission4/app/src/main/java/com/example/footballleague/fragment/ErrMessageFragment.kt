package com.example.footballleague.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.footballleague.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.err_message_fragment.*
import org.jetbrains.anko.image

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ErrMessageFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.err_message_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val errType = arguments!!.get("errType")
        when (errType) {
            "NullPointerException" -> {
                img_err_message.image =
                    ContextCompat.getDrawable(
                        activity!!.applicationContext,
                        R.drawable.ic_notfound
                    )
            }
            else -> {
                img_err_message.image =
                    ContextCompat.getDrawable(
                        activity!!.applicationContext,
                        R.drawable.ic_disconnect
                    )
            }
        }
        //Toast.makeText(activity, errType.toString(), Toast.LENGTH_LONG).show()
    }
}
