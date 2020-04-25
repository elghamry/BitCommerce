package sa.biotic.app

//import com.softhinkers.softwiki.providers.ArticleDataProvider

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_search.*
import sa.biotic.app.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
//
//    private var wikiManager: WikiManager? = null
//    private var adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)


//        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)


//        val searchEt:EditText = findViewById(R.id.search_et)

        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }
        })
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        search_results_recycler.layoutManager = LinearLayoutManager(this)
//        search_results_recycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        menuInflater.inflate(R.menu.search_menu, menu)

//        val searchItem = menu!!.findItem(R.id.action_search)
//        searchItem.setIcon(resources.getDrawable(R.drawable.search_colored))
//
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = searchItem!!.actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setIconifiedByDefault(false)
//        searchView.requestFocus()
////        searchView.setBackgroundColor(resources.getColor(R.color.white))
//        searchView.background=resources.getDrawable(R.drawable.search_background)
//        val layoutParams = ViewGroup.LayoutParams(dpToPx(300F), dpToPx(30F))
//
//        searchView.layoutParams=layoutParams
//searchView.margin(left = 6F,right = 20F,top = 6F,bottom = 6F)
//        searchView.gravity=Gravity.CENTER
//
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//
////                // do the search and update the elements
////                wikiManager?.search(query, 0, 20, { wikiResult ->
////                    adapter.currentResults.clear()
////                    adapter.currentResults.addAll(wikiResult.query!!.pages)
////                    runOnUiThread { adapter.notifyDataSetChanged() }
////                })
////                println("updated search")
//
//                return false
//            }
//
//            override fun onQueryTextChange(s: String): Boolean {
//                return false
//            }
//        })
//
//
//        return super.onCreateOptionsMenu(menu)
//    }
}