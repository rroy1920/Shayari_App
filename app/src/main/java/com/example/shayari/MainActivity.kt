package com.example.shayari

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayari.Adapter.CategoryAdapter
import com.example.shayari.Model.CategoryModel
import com.example.shayari.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=FirebaseFirestore.getInstance()

        db.collection("Shayari").addSnapshotListener{value,error->

            val list= arrayListOf<CategoryModel>()
            val data =value?.toObjects(CategoryModel::class.java)
            list.addAll(data!!)

            binding.rcvCategory.layoutManager=LinearLayoutManager(this)
            binding.rcvCategory.adapter=CategoryAdapter(this,list)

        }



        binding.btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            } else {
                binding.drawerLayout.openDrawer(Gravity.LEFT)
            }
        }


        binding.navigationView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.share->{
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            """
                            ${shareMessage} https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                           
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                    true
                }
                R.id.more->{
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                    }
                    true

                }
                R.id.rate->{

                    val uri = Uri.parse("market://details?id=" + this.packageName)
                    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                    try {
                       startActivity(myAppLinkToMarket)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, " Sorry, Not able to open!", Toast.LENGTH_SHORT).show()
                    }
                    true
                }else-> false
            }
        }
    }

        override fun onBackPressed(){
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)){
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }else{
                super.onBackPressed()
            }
        }
    }
