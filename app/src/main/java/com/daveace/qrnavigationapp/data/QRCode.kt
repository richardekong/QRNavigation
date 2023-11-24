package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(O)
data class QRCode(
    val id:Int = 0,
    val description:String = "",
    val space: Space? = null,
    val subspace: Subspace? = null,
    val pageURL:String = "",
    val imageURL:String = "",
    val createdAt:LocalDateTime = LocalDateTime.now()
)

