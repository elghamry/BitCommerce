package sa.biotic.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import sa.biotic.app.ScrollingActivity
import sa.biotic.app.databinding.OfferItemBinding
import sa.biotic.app.model.Offer

class OfferPagerAdapter(val context: Context, val offers: MutableList<Offer>) : PagerAdapter() {

    override fun getCount(): Int = offers.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = OfferItemBinding.inflate(LayoutInflater.from(context), container, false)
//        binding.offerImage.setImageResource(offers[position].)
        binding.root.setOnClickListener {

            //            Toast.makeText(context, position.toString()+" clicked", Toast.LENGTH_SHORT).show()


            val intent = Intent(binding.root.context, ScrollingActivity::class.java)
            intent.putExtra("product_name", offers.get(position).title)
            intent.putExtra("product_image", offers.get(position).img)
            intent.putExtra("product_price", offers.get(position).price)
            intent.putExtra("product_prev_price", offers.get(position).prev_price)

            intent.putExtra("type", "offer")
            context.startActivity(intent)
        }

        container.setOnClickListener {


        }



        Glide
            .with(context)
            .load(offers.get(position).img)
            .optionalCenterCrop()
//            .placeholder(R.drawable.loading_spinner)
            .into(binding.offerImage)

        binding.offerTitle.text = offers.get(position).title



        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}