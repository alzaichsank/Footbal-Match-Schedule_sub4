package alzaichsank.com.aplikasifootbalmatchschedule.system.config

/**
 * Created by Alza Ichsan Kurniawa on 18/12/2017.
 */
class ApiConfig {
    companion object {
        // base end point
        const val END_POINT = "https://www.thesportsdb.com/api/v1/json/1/"
        // all leagues ( spinner )
        const val allLeagues = "search_all_leagues.php?s=Soccer"
        // prev match
        const val eventsPastleague = "eventspastleague.php"
        // next match
        const val eventsNextleague = "eventsnextleague.php"
        // detail team
        const val lookupTeam = "lookupteam.php"
    }
}
