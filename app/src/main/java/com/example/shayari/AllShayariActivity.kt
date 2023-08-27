package com.example.shayari

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayari.Adapter.AllShayariAdapter
import com.example.shayari.Model.ShayariModel
import com.example.shayari.databinding.ActivityAllShayariBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class AllShayariActivity : AppCompatActivity() {

    lateinit var binding: ActivityAllShayariBinding
    lateinit var db:FirebaseFirestore
    private var i :Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAllShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name=intent.getStringExtra("name")
        val id = intent.getStringExtra("id")


        db= FirebaseFirestore.getInstance()

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }


        binding.catName.text=name.toString()

        db.collection("Shayari").document(id!!).collection("all").addSnapshotListener{
            value,error ->

            val shayariList= arrayListOf<ShayariModel>()
            val data= value?.toObjects(ShayariModel::class.java)
            shayariList.addAll(data!!)

            binding.rcvAllShayari.layoutManager=LinearLayoutManager(this)
            binding.rcvAllShayari.adapter=AllShayariAdapter(this,shayariList)

        }


    }
}