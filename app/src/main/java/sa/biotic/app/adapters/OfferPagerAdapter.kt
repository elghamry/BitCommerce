package sa.biotic.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import sa.biotic.app.R
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

        Glide
            .with(context)
            .load(offers.get(position).img)
            .centerCrop()
//            .placeholder(R.drawable.loading_spinner)
            .into(binding.offerImage);

        binding.offerTitle.setText(offers.get(position).title)


        container?.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container?.removeView(`object` as LinearLayout)
    }
}