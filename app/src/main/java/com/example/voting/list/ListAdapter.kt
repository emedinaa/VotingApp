package com.example.voting.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voting.R
import com.example.voting.data.entities.Voters

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<Voters>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int = userList.size
    // return userList.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        //holder.itemView.tvId.text = currentItem.votersId.toString()
        holder.itemView.findViewById<TextView>(R.id.tvFirstName).text = currentItem.firstName
        holder.itemView.findViewById<TextView>(R.id.tvLastName).text = currentItem.lastName
        holder.itemView.findViewById<TextView>(R.id.tvAge).text = currentItem.votingCard

        //Se usa Glide para que se proyecte la imagen en el RecyclerView
        Glide.with(holder.itemView.context).load(currentItem.img).into(
            holder.itemView.findViewById(R.id.imageView3))

        //Metodo para actualizar información del usuario (cuando demos click en el recycler view se mantenga la información)
        holder.itemView.findViewById<View>(R.id.rowLayout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpDateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(user: List<Voters>) {
        this.userList = user
        notifyDataSetChanged()
    }
}