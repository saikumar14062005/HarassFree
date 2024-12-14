package com.example.harassfree
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OfficialAdapter(private val officials: List<Official>) :
    RecyclerView.Adapter<OfficialAdapter.OfficialViewHolder>() {

    class OfficialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.imagePhoto)
        val name: TextView = itemView.findViewById(R.id.textName)
        val email: TextView = itemView.findViewById(R.id.textEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_official, parent, false)
        return OfficialViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfficialViewHolder, position: Int) {
        val official = officials[position]
        holder.photo.setImageResource(official.photo) // Set the photo
        holder.name.text = official.name             // Set the name
        holder.email.text = official.email           // Set the email
    }

    override fun getItemCount(): Int {
        return officials.size
    }
}
