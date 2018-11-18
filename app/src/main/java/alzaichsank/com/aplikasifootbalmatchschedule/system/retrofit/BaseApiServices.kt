package alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit

import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.config.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by alzaichsank on 1/22/18.
 */
interface BaseApiServices {

    @GET(ApiConfig.allLeagues)
    fun getallLeagues(): Call<ResponseBody>

    @GET(ApiConfig.eventsPastleague)
    fun geteventsPasteague(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.eventsNextleague)
    fun geteeventsNextleague(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.lookupTeam)
    fun getLookupteam(@Query("id") id : String): Call<ResponseBody>
}

