package alzaichsank.com.aplikasifootbalmatchschedule.view.match.adapter

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.adapter.BaseAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.utils.DateTime.getLongDate
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_match.view.*

class matchAdapter(private val onClickListener: (position: Int) -> Unit) : BaseAdapter<RecyclerView.ViewHolder, EventsItem>() {

    companion object {
        const val TRANSACTION_ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var data: MutableList<EventsItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_match, parent, false)
        return ProductViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(data[position],position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TRANSACTION_ITEM_TYPE
    }

    override fun addAllData(data: MutableList<EventsItem>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: EventsItem) {
        this.data.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): EventsItem {
        return data[position]
    }

    override fun getAllData(): MutableList<EventsItem> {
        return data
    }

    override fun setData(data: MutableList<EventsItem>) {

        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

class ProductViewHolder(viewItem: View, onClickListener: (position: Int) -> Unit) : RecyclerView.ViewHolder(viewItem) {
    init {
        viewItem.setOnClickListener {
            onClickListener(adapterPosition)
        }
    }
    fun bindData(data: EventsItem , position : Int){
            if((data.intHomeScore!!.equals("null")||data.intAwayScore!!.equals("null"))&&!data.strHomeTeam!!.equals("null")) {
                itemView.date.text = getLongDate(data.dateEvent)
                itemView.ID_HOME_TEAM.text = data.strHomeTeam
                itemView.ID_HOME_SCORE.visibility = View.GONE
                itemView.ID_AWAY_TEAM.text = data.strAwayTeam
                itemView.ID_AWAY_SCORE.visibility = View.GONE
            }else if(!data.strHomeTeam!!.equals("null")) {
                itemView.date.text = getLongDate(data.dateEvent)
                itemView.ID_HOME_TEAM.text = data.strHomeTeam
                itemView.ID_HOME_SCORE.text = data.intHomeScore
                itemView.ID_AWAY_TEAM.text = data.strAwayTeam
                itemView.ID_AWAY_SCORE.text = data.intAwayScore
                itemView.ID_HOME_SCORE.visibility = View.VISIBLE
                itemView.ID_AWAY_SCORE.visibility = View.VISIBLE
            }else{
                itemView.date.text = getLongDate(data.dateEvent)
                itemView.ID_HOME_TEAM.text = ""
                itemView.ID_HOME_SCORE.text = ""
                itemView.ID_AWAY_TEAM.text = ""
                itemView.ID_AWAY_SCORE.text = ""
            }
        }
    }