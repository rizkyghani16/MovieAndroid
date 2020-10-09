
package id.firman.movieandroid.models.entity

import android.os.Parcelable
import androidx.room.Entity
import id.firman.movieandroid.models.Keyword
import id.firman.movieandroid.models.Review
import id.firman.movieandroid.models.Video
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = [("id")])
data class Tv(
  var page: Int,
  var keywords: List<Keyword>? = ArrayList(),
  var videos: List<Video>? = ArrayList(),
  var reviews: List<Review>? = ArrayList(),
  val poster_path: String?,
  val popularity: Float,
  val id: Int,
  val backdrop_path: String?,
  val vote_average: Float,
  val overview: String,
  val first_air_date: String?,
  val origin_country: List<String>,
  val genre_ids: List<Int>,
  val original_language: String,
  val vote_count: Int,
  val name: String,
  val original_name: String,
  var search: Boolean?,
  var filter: Boolean?
) : Parcelable {
  constructor(id: Int, search: Boolean? = false, filter: Boolean? = false, name: String = "ANY_TV") :this(
    1,
    emptyList(),
    emptyList(),
    emptyList(),
    "",
    0f,
    id,
    "",
    0f,
    "",
    "",
    ArrayList(),
    ArrayList(),
    "",
    1,
    name,
    "",
    search,
    filter
  )
}