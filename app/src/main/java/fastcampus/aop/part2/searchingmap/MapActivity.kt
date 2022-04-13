package fastcampus.aop.part2.searchingmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fastcampus.aop.part2.searchingmap.databinding.ActivityMapBinding
import fastcampus.aop.part2.searchingmap.model.SearchResultEntity

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private var currentSelectedMarker: Marker? = null

    private lateinit var searchResult: SearchResultEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (::searchResult.isInitialized.not()) {
            intent?.let {
                searchResult = it.getParcelableExtra<SearchResultEntity>(SEARCH_RESULT_EXTRA_KEY)
                    ?: throw Exception("데이터가 존재하지 않습니다.")
                setupGoogleMap()
            }
        }

        setupGoogleMap()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        currentSelectedMarker = setupMarker(searchResult)

        currentSelectedMarker?.showInfoWindow()
    }

    private fun setupGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupMarker(searchResultEntity: SearchResultEntity): Marker? {
        val latLng = LatLng(
            searchResultEntity.locationLatLng.latitude.toDouble(),
            searchResultEntity.locationLatLng.longitude.toDouble()
        )
        val markerOption = MarkerOptions().apply {
            position(latLng)
            title(searchResultEntity.name)
            snippet(searchResultEntity.fullAddress)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM_LEVEL))

        return map.addMarker(markerOption)
    }

    companion object {
        const val SEARCH_RESULT_EXTRA_KEY = "SEARCH_RESULT_EXTRA_KEY"
        const val CAMERA_ZOOM_LEVEL = 17f
    }
}