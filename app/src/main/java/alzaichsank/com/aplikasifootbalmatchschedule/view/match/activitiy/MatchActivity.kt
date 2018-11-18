package alzaichsank.com.aplikasifootbalmatchschedule.view.match.activitiy

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.LeaguesItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.activitiy.DetailMatchActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.activitiy.DetailMatchActivity.Companion.INTENT_DETAIL
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.adapter.matchAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.presenter.MatchPresenter
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.ctx
import org.json.JSONObject
import kotlin.collections.ArrayList

class MatchActivity : AppCompatActivity(), MatchView {
    private var listLeageu = ArrayList<LeaguesItem>()
    private var menuItem: Int = 1
    private var idSpinner: String = ""
    private val presenter = MatchPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        getlistLeageu()
        setSpinner(menuItem)
        initBottomNavigationContainer()
        initContainer()

        refreshButton.setOnClickListener {
            if (presenter.isNetworkAvailable(this)) {
                this@MatchActivity.finish()
                this@MatchActivity.startActivity(this@MatchActivity.intent)
            } else {
                Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
                setSpinner(menuItem)
                setDataToContainer(idSpinner, menuItem)
            }

        }
    }

    private fun initLayout() {
        setContentView(R.layout.activity_match)
    }

    private fun initBottomNavigationContainer() {
        navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private val bottomNavigationListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_match_prev -> {
                    spinner.visibility = View.VISIBLE
                    menuItem = 1
                    title = getString(R.string.prev)
                    setSpinner(menuItem)
                    setDataToContainer(idSpinner, menuItem)
                    Log.d("TAG", "ini prev")
                    true
                }
                R.id.main_menu_match_next -> {
                    spinner.visibility = View.VISIBLE
                    menuItem = 2
                    title = getString(R.string.next)
                    setSpinner(menuItem)
                    setDataToContainer(idSpinner, menuItem)
                    Log.d("TAG", "ini next")
                    true
                }
                R.id.main_menu_match_favorites -> {
                    menuItem = 3
                    title = getString(R.string.fav)
                    setSpinner(menuItem)
                    spinner.visibility = View.GONE
                    setDataToContainer("0", menuItem)
                    Log.d("TAG", "ini fav")
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    override fun showLoading() {
        progressbar.visible()
        recyclerview.invisible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    override fun hideLoading() {
        progressbar.gone()
        recyclerview.visible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    override fun showEmptyData() {
        progressbar.gone()
        recyclerview.invisible()
        if (presenter.isNetworkAvailable(this)) {
            emptyDataView.visible()
        } else {
            noconectionView.visible()

        }
    }

    private fun getlistLeageu() {
        if (presenter.isNetworkAvailable(this)) {
            presenter.getSpinnerData( object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (presenter.isSuccess(response)) {
                        try {
                            val jsonObject = JSONObject(response)
                            var numData = 0
                            val message = jsonObject.getJSONArray("countrys")
                            val idLeague: ArrayList<String> = ArrayList()
                            val leagueName: ArrayList<String> = ArrayList()
                            Log.d("TAG MESSAGE", message.length().toString())
                            if (message.length() > 0) {
                                for (i in 0 until message.length()) {
                                    val dataAll = message.getJSONObject(i)
                                        idLeague.add(dataAll.getString("idLeague"))
                                        leagueName.add(dataAll.getString("strLeague"))
                                        listLeageu.add(LeaguesItem(idLeague[i], leagueName[i]))
                                    numData += 1
                                }
                                Log.d("TAG NUMDATA", numData.toString())
                                spinner.adapter = ArrayAdapter<String>(this@MatchActivity, android.R.layout.simple_spinner_dropdown_item, leagueName)
                            } else {
                            }
                        } catch (e: NullPointerException) {
                            showEmptyData()
                        }
                    }
                }

                override fun onFailed(response: String) {
                }

                override fun onFailure(throwable: Throwable) {
                }
            })
        } else {
            showEmptyData()
            Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }

    }

    private fun setSpinner(menu: Int) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                idSpinner = listLeageu[position].idLeague
                when (menu) {
                    1 -> setDataToContainer(idSpinner, 1)
                    2 -> setDataToContainer(idSpinner, 2)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = matchAdapter { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(this, DetailMatchActivity::class.java)
            intent.putExtra(INTENT_DETAIL, dataIntent)
            intent.putExtra(getString(R.string.menuItem), menuItem.toString())
            startActivity(intent)
        }
    }

    private fun setDataToContainer(id: String, menu: Int) {
        var data: MutableList<EventsItem>
        if (presenter.isNetworkAvailable(this)) {
            if (menu == 1) {
                showLoading()
                presenter.getPrevMatch( id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (presenter.isSuccess(response)) {
                            try {
                                if (presenter.isSuccess(response)) {
                                    data = presenter.parsingData(this@MatchActivity, response)
                                    if (data.size < 1) {
                                        showEmptyData()
                                    } else {
                                        getListAdapter()?.setData(data.toMutableList())
                                        hideLoading()
                                    }
                                }
                            } catch (e: NullPointerException) {
                                showEmptyData()
                            }
                        }
                    }

                    override fun onFailed(response: String) {
                        showEmptyData()
                    }

                    override fun onFailure(throwable: Throwable) {
                        showEmptyData()
                    }
                })
            } else if (menu == 2) {
                showLoading()
                presenter.getNextMatch(id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (presenter.isSuccess(response)) {
                            try {
                                if (presenter.isSuccess(response)) {
                                    data = presenter.parsingData(this@MatchActivity, response)
                                    if (data.size < 1) {
                                        showEmptyData()
                                    } else {
                                        getListAdapter()?.setData(data.toMutableList())
                                        hideLoading()
                                    }
                                }
                            } catch (e: NullPointerException) {
                                showEmptyData()
                            }
                        }
                    }

                    override fun onFailed(response: String) {
                        showEmptyData()
                    }

                    override fun onFailure(throwable: Throwable) {
                        showEmptyData()
                    }
                })
            } else if (menu == 3) {
                var data: MutableList<EventsItem>
                data = presenter.getFavoritesAll(ctx)
                if (data.size < 1) {
                    showEmptyData()
                } else {
                    getListAdapter()?.setData(data.toMutableList())
                }

            } else {
                showEmptyData()
            }
        } else {
            showEmptyData()
            Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): matchAdapter? = recyclerview?.adapter as? matchAdapter
}
