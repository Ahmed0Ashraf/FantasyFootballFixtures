package Model

import android.os.Parcel
import android.os.Parcelable

class Player(var id:Int,var name:String,var status:String,var teamCode:Int,var elementType:Int,var lastPoints:Int,var isCaptian:Boolean,
             var isViceCaptian:Boolean,var price:Double,
             var percent:Double,var totalPoints:Int,var goalsScored:Int,var assists:Int, var cleanSheets:Int,
             var goalsConceded:Int,var penaltiesSaved:Int,var yellowCards:Int,var redCards:Int,var saves:Int,var bonus:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeInt(teamCode)
        parcel.writeInt(elementType)
        parcel.writeInt(lastPoints)
        parcel.writeByte(if (isCaptian) 1 else 0)
        parcel.writeByte(if (isViceCaptian) 1 else 0)
        parcel.writeDouble(price)
        parcel.writeDouble(percent)
        parcel.writeInt(totalPoints)
        parcel.writeInt(goalsScored)
        parcel.writeInt(assists)
        parcel.writeInt(cleanSheets)
        parcel.writeInt(goalsConceded)
        parcel.writeInt(penaltiesSaved)
        parcel.writeInt(yellowCards)
        parcel.writeInt(redCards)
        parcel.writeInt(saves)
        parcel.writeInt(bonus)

    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }


}