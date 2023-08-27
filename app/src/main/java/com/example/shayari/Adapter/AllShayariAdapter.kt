package com.example.shayari.Adapter

import android.animation.Animator
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.shayari.AllShayariActivity
import com.example.shayari.BuildConfig
import com.example.shayari.Model.ShayariModel
import com.example.shayari.R
import com.example.shayari.databinding.ItemShayariBinding
import java.util.logging.Handler


class AllShayariAdapter(
    val allShayariActivity: AllShayariActivity,
    val shayariList: ArrayList<ShayariModel>,
    private var i:Int =0

) :RecyclerView.Adapter<AllShayariAdapter.ShayariViewHolder>() {
    class ShayariViewHolder(val binding:ItemShayariBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {
        return ShayariViewHolder(ItemShayariBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
     return shayariList.size
    }

    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {

        if (position%6==0){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_1)

        }else if (position%6==1){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_2)
        }else if (position%6==2){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_3)
        }else if (position%6==3){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_4)
        }else if (position%6==4){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_5)
        }else if (position%6==5){
            holder.binding.mainBack.setBackgroundResource(R.drawable.gradient_6)
        }







       holder.binding.itemShayari.text=shayariList[position].data.toString()




          holder.binding.btnCopy.setOnClickListener{

            val clipboard: ClipboardManager? =
               allShayariActivity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", shayariList[position].data.toString())
            clipboard?.setPrimaryClip(clip)

            Toast.makeText(allShayariActivity,"Copied",Toast.LENGTH_SHORT).show()

        }
        holder.binding.btnShare.setOnClickListener{
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\n ${shayariList[position].data}\n\n"
                shareMessage =
                    """
                            ${shareMessage} https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                           
                            """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                allShayariActivity.startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }

        }

        holder.binding.btnWhatsapp.setOnClickListener{
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shayariList[position].data.toString())
            try {
                allShayariActivity.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {

            }

        }
    }

    abstract class DoubleClickListener : View.OnClickListener{
        private var lastClickTime:Long=0

        companion object{
            private const val DOUBLE_CLICK_TIME_DELTA =300
        }

        override fun onClick(v: View?) {
            val clickTime=System.currentTimeMillis()
            if (clickTime-lastClickTime< DOUBLE_CLICK_TIME_DELTA){
                onDoubleCLick(v)
            }
            lastClickTime=clickTime
        }

        abstract fun onDoubleCLick(v: View?)

    }

}
