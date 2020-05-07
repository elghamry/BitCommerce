import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import sa.biotic.app.R
import sa.biotic.app.fragments.PastOrdersFragment
import sa.biotic.app.fragments.UpcomingOrdersFragment


private var TAB_TITLES = arrayOf(
    R.string.upcoming,

    R.string.past

)


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class OrdersSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {


    var fm2 = fm
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("heyamhe", "hellloooooo")
        return when (position) {
            0 -> {
                UpcomingOrdersFragment.newInstance(position)
            }


            else -> {
                PastOrdersFragment.newInstance(position)
            }
        }


//        return             PlaceholderFragment.newInstance(position )

    }


    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        fm2.beginTransaction().remove((`object` as Fragment))
            .commitNowAllowingStateLoss()
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}