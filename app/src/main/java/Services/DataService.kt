package Services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.innovators.fantasyfootballfixtures.Controller.App

object DataService {
    fun networkErrorListener(error:VolleyError){
        if (error is TimeoutError || error is NoConnectionError) {
            Toast.makeText(App.appContext,"No Internet Conncetion Please Check Your Internet Settings",Toast.LENGTH_LONG).show()
        } else if (error is AuthFailureError) {
            Toast.makeText(App.appContext,"",Toast.LENGTH_LONG).show()
        } else if (error is ServerError) {
            Toast.makeText(App.appContext,"The Server Is Updaing Please Try Later",Toast.LENGTH_LONG).show()

        } else if (error is NetworkError) {
            Toast.makeText(App.appContext,"Connecntion Problems Please Try Again",Toast.LENGTH_LONG).show()
        } else if (error is ParseError) {
            //Toast.makeText(App.appContext,"Please Check Your Internet Connecntion",Toast.LENGTH_LONG).show()
        }

    }

    fun checkInternetConncetion() : Boolean{
      //  var connected = false
        val connectivityManager = App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        /*if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() === NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() === NetworkInfo.State.CONNECTED))
        {
            //we are connected to a network
            connected = true
        }
        else
            connected = false

*/
        val networkInfo:NetworkInfo? =connectivityManager.activeNetworkInfo
        if (networkInfo!= null && networkInfo!!.isConnected){
            return true
        }
        else
            return false



    }
}