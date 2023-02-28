package com.example.fragmentintro

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ColorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ColorFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        return inflater.inflate(R.layout.fragment_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redButton = view.findViewById<Button>(R.id.red_button)
        val greenButton = view.findViewById<Button>(R.id.green_button)
        val blueButton = view.findViewById<Button>(R.id.blue_button)
        val helloWorldTextView = view.findViewById<TextView>(R.id.hello_world)

        redButton.setOnClickListener {
            helloWorldTextView.setTextColor(Color.RED)
        }
        greenButton.setOnClickListener {
            helloWorldTextView.setTextColor(Color.GREEN)
        }
        blueButton.setOnClickListener {
            helloWorldTextView.setTextColor(Color.BLUE)
        }
    }
}