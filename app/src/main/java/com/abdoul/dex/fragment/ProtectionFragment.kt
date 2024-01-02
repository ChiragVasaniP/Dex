package com.abdoul.dex.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.abdoul.dex.R
import com.abdoul.dex.activity.ScanDeviceActivity
import com.abdoul.dex.activity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProtectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProtectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var externalscan : TextView? = null
    var scandevice : TextView? = null
    var profile : TextView? = null
    var networkwatch : TextView? = null
    var heuristicdevice : TextView? = null
    var senses : TextView? = null
    var hamburgermenu : ImageView? = null

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
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_protection, container, false)
        externalscan = view.findViewById(R.id.externalscan)
        scandevice = view.findViewById(R.id.scandevice)
        profile = view.findViewById(R.id.internetprofile)
        networkwatch = view.findViewById(R.id.networkwatch)
        heuristicdevice = view.findViewById(R.id.heuristicdevice)
        senses = view.findViewById(R.id.senses)

        externalscan!!.setOnClickListener {
            val intent = Intent(activity, ExternalActivity::class.java)
            startActivity(intent)
        }
        scandevice!!.setOnClickListener {
            val intent = Intent(activity, ScanDeviceActivity::class.java)
            startActivity(intent)
        }
        profile!!.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        networkwatch!!.setOnClickListener {
            val intent = Intent(activity, ExternalActivity::class.java)
            startActivity(intent)
        }
        heuristicdevice!!.setOnClickListener {
            val intent = Intent(activity, HeuristicsDeviceActivity::class.java)
            startActivity(intent)
        }
        senses!!.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        hamburgermenu = view.findViewById(R.id.hamburgermenu)
        hamburgermenu!!.setOnClickListener {
            (activity as BaseActivity).openCloseNavigationDrawer()
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
         * @return A new instance of fragment ProtectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProtectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}