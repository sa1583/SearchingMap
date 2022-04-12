package fastcampus.aop.part2.searchingmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.aop.part2.searchingmap.databinding.ActivityMainBinding
import fastcampus.aop.part2.searchingmap.model.LocationLatLngEntity
import fastcampus.aop.part2.searchingmap.model.SearchResultEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        setData()
        initViews()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter {
            Toast.makeText(this@MainActivity, "${it.name}, ${it.fullAddress}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData() {
        val dataList = (0..10).map {
            SearchResultEntity(
                name = "빌딩 $it",
                fullAddress = "주소 $it",
                locationLatLng = LocationLatLngEntity(
                    it.toFloat(),
                    it.toFloat()
                )
            )
        }
        adapter.submitList(dataList)
    }
}