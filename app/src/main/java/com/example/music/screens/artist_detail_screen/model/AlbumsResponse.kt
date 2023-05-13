import com.google.gson.annotations.SerializedName

class AlbumsResponse(
    @SerializedName("data") var data: List<Data>,
    @SerializedName("total") val total: Int,
    @SerializedName("next") val next: String
){
    fun removeDuplicates() {
        data = data.distinctBy { it.album.id }
    }
}

data class Data(
    @SerializedName("album") val album: Album
)

data class Album(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("cover_big") val coverBig: String,
    @SerializedName("cover_medium") val coverMedium: String,
    @SerializedName("cover_small") val coverSmall: String
)
