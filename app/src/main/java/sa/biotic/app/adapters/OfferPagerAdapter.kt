package sa.biotic.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import sa.biotic.app.R
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
//            intent.putExtra("product_name", offers.get(position).OfferDescription_En)
//            intent.putExtra("product_image", offers.get(position).OfferImage)
//            intent.putExtra("product_price", offers.get(position).OfferPrice)
//            intent.putExtra("product_prev_price", offers.get(position).ProductPrice)
//            intent.putExtra("product_description", offers.get(position).ProductDescreption_En)

//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)
            intent.putExtra("OfferItem", offers.get(position))
//            intent.putExtra("type", "bundle")


//            intent.putExtra(EXTRA_MESSAGE, message)
//            startActivityForResult(intent, 1)

            intent.putExtra("type", "offer")
            context.startActivity(intent)
        }

        container.setOnClickListener {


        }



        Glide
            .with(context)
            .load(offers.get(position).OfferImage)
            .optionalCenterCrop()
//            .placeholder(r)
            .placeholder(R.drawable.offer_placeholder)
            .into(binding.offerImage)


        binding.offerTitle.text = offers.get(position).OfferDescription_En



        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}