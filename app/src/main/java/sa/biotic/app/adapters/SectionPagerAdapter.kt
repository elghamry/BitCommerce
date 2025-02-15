package sa.biotic.app.adapters
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import sa.biotic.app.R
import sa.biotic.app.fragments.PlaceholderFragment
import sa.biotic.app.fragments.ProdsBundsRelaFragment
import sa.biotic.app.fragments.ReviewsFragment

private var TAB_TITLES = arrayOf(
    R.string.tab_text_3,
    R.string.tab_text_1,
    R.string.tab_text_2
)


class SectionPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return when (position) {
            0 -> {
                ProdsBundsRelaFragment.newInstance(position)
            }


            1 -> {
                PlaceholderFragment.newInstance(position + 1)
            }
            else -> {
                ReviewsFragment.newInstance(position + 2)
            }
        }



//        return             PlaceholderFragment.newInstance(position )

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}