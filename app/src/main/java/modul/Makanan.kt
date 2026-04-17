package modul
import androidx.annotation.DrawableRes

data class Makanan(
    val nama: String,
    val deskripsi: String,
    val harga: Int,
    val rating: Double,
    @param:DrawableRes val gambar: Int
)