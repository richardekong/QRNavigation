package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance
import java.time.LocalDateTime

@RequiresApi(O)
data class QRCode(
    val id:Int = 0,
    val description:String = "",
    val space: Space = newInstance(Space::class),
    val subspace: Subspace = newInstance(Subspace::class),
    val pageURL:String = "",
    val imageURL:String = "",
    val createdAt:LocalDateTime = LocalDateTime.now()
)

