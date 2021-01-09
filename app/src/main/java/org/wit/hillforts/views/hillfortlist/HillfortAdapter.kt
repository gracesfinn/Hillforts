package org.wit.hillforts.hillfortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_hillfort.view.*
import kotlinx.android.synthetic.main.new_card.view.*
import kotlinx.android.synthetic.main.new_card.view.description
import kotlinx.android.synthetic.main.new_card.view.hillfortTitle

import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel

interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
   // private var favHillforts: HillfortModel?,
    private val listener: HillfortListener) :
    RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.new_card, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort,listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {
            itemView.hillfortTitle.text = hillfort.title
            itemView.description.text = hillfort.description
            Glide.with(itemView.context).load(hillfort.image1).into(itemView.imageIcon);

            var visitedString =
                if (hillfort.visited)
                    "Visited"
            else
                    "Not Visited"

            itemView.visited2.text = "${visitedString}"



            itemView.setOnClickListener {
                listener.onHillfortClick(hillfort)
            }
        }
    }
}