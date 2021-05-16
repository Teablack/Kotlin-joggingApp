package com.example.lab8_beta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class DetailFragment : Fragment() {

    private var item: Route.Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = Route.route_map[it.getString(ARG_ITEM_ID)]
                activity!!.findViewById<Toolbar>(R.id.detail_toolbar).title = item!!.content

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail).text = it.details
        }

        return rootView
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}