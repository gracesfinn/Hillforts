package org.wit.hillforts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.fragment_hillfort_list.view.*
import org.wit.hillforts.R
import org.wit.hillforts.hillfortlist.HillfortAdapter
import org.wit.hillforts.models.json.HillfortJSONStore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HillfortListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HillfortListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_hillfort_list, container, false)
        activity?.title = getString(R.string.action_addHillfort)


        return root;
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            HillfortListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}