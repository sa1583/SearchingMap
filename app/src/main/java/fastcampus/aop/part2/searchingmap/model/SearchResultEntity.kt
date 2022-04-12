package fastcampus.aop.part2.searchingmap.model

data class SearchResultEntity(
    val fullAddress: String,
    val name: String,
    val locationLatLng: LocationLatLngEntity
)