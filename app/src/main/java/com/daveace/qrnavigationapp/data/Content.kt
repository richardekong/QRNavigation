package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

@RequiresApi(O)
data class Content(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val event: Event = newInstance(Event::class),
    val space: Space = newInstance(Space::class),
    val subspace: Subspace = newInstance(Subspace::class)
)



