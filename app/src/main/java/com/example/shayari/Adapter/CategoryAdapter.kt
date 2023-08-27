package com.example.shayari.Adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shayari.AllShayariActivity
import com.example.shayari.MainActivity
import com.example.shayari.Model.CategoryModel
import com.example.shayari.databinding.ItemCategoryBinding

class CategoryAdapter( val mainActivity: MainActivity,val list: ArrayList<CategoryModel>) :RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {

    val colorsList= arrayListOf<String>("#EA1179","#9F0D7F","#40F8FF","#ED7B7B","#35A29F","#FF449F")
    class CatViewHolder(val binding: ItemCategoryBinding) :RecyclerView.ViewHolder(binding.root){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
       return CatViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=list.size



    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        if (position%6==0){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[0]))

        }else if (position%6==1){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[1]))
        }else if (position%6==2){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[2]))
        }else if (position%6==3){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[3]))
        }else if (position%6==4){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[4]))
        }else if (position%6==5){
            holder.binding.itemTxt.setBackgroundColor(Color.parseColor(colorsList[5]))
        }

        holder.binding.itemTxt.text=list[position].name.toString()
        holder.binding.root.setOnClickListener{

            val intent = Intent(mainActivity,AllShayariActivity::class.java)

            intent.putExtra("id",list[position].id)
            intent.putExtra("name",list[position].name)
            mainActivity.startActivity(intent)
        }

    }
}