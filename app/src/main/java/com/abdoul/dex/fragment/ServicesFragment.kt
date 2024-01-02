package com.abdoul.dex.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.abdoul.dex.R
import com.abdoul.dex.activity.BaseActivity
import com.abdoul.dex.activity.ScanDeviceActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ServicesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var hamburgermenu: ImageView
    lateinit var llInternetProfile: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)

        hamburgermenu = view.findViewById(R.id.hamburgermenu)
        llInternetProfile = view.findViewById(R.id.ll_internet_profile)

        hamburgermenu.setOnClickListener {
            (activity as BaseActivity).openCloseNavigationDrawer()
        }

        llInternetProfile.setOnClickListener {
            val clickintent = Intent(activity, ScanDeviceActivity::class.java)
            startActivity(clickintent)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ServicesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ServicesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}