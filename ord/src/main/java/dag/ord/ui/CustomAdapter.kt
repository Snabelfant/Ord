package dag.ord.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dag.ord.R
import dag.ord.util.Logger


class CustomAdapter(val data: List<DataModel>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.result)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.result, parent, false)
//        view.setOnClickListener(MainActivity.myOnClickListener)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {
        val textViewName = holder.textViewName
        textViewName.text = ">>>>> ${data[listPosition].name} ${data[listPosition].name.toInt()*2}\ngggggggg"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    data class DataModel(var name: String)
}