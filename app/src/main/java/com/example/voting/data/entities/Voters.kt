package com.example.voting.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Metodo Parcelize y Parcelable permiten pasar los parametros
 * de la clase Voters al UpdateFrament, necesario para actualizaar los datos
 */
@Parcelize
@Entity(tableName = "voters_table")
data class Voters(
    @PrimaryKey(autoGenerate = true)
    val votersId: Int,
    val firstName: String,
    val lastName: String,
    val votingCard: String,
    val img: String
):Parcelable
