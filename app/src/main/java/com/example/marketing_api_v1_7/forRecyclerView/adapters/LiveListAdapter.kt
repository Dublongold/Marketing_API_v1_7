package com.example.marketing_api_v1_7.forRecyclerView.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.models.live.LiveSportsEvents
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.ZoneOffset


class LiveListAdapter: ListAdapter<LiveSportsEvents, LiveListAdapter.MatchViewHolder>(
    object: DiffUtil.ItemCallback<LiveSportsEvents>() {
        override fun areItemsTheSame(
            oldItem: LiveSportsEvents,
            newItem: LiveSportsEvents
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: LiveSportsEvents,
            newItem: LiveSportsEvents
        ): Boolean {
            return oldItem == newItem
        }

    }
) {

    val gameStatusMap = mapOf(
        0 to "Match in progress",
        1 to "Match finished",
        2 to "Halftime",
        4 to "Match canceled",
        8 to "Opponent 1 takes a penalty",
        16 to "Opponent 2 takes a penalty"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.live_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val item = currentList[position]

        holder.run {
            matchTitleText.text = item.tournamentNameLocalization
            opponent1Name.text = item.opponent1NameLocalization
            val drawable: Drawable? = null
            Picasso.get()
                .load("https://nimblecd.com/sfiles/logo_teams/${item.imageOpponent1.first()}")
                .into(opponent1Image)
            opponent2Name.text = item.opponent2NameLocalization
            Picasso.get()
                .load("https://nimblecd.com/sfiles/logo_teams/${item.imageOpponent2.first()}")
                .into(opponent2Image)
            val dateTime = LocalDateTime.ofEpochSecond(item.startDate.toLong(), 0, ZoneOffset.UTC)
            matchDate.text = holder.itemView.resources.getString(R.string.match_date, dateTime.toLocalDate().toString())
            matchTime.text = dateTime.toLocalTime().toString()
            matchVenue.text = "Unknown"
            gameStatus.text = gameStatusMap[item.gameStatus]
            fullScore.text = itemView.resources.getString(
                R.string.full_score,
                item.fullScore.sc1,
                item.fullScore.sc2)
        }
    }

    class MatchViewHolder(view: View): ViewHolder(view) {
        val matchTitleText: TextView = view.findViewById(R.id.matchTitleText)
        val opponent1Name: TextView = view.findViewById(R.id.opponent1Name)
        val opponent1Image: ImageView = view.findViewById(R.id.opponent1Image)
        val opponent2Name: TextView = view.findViewById(R.id.opponent2Name)
        val opponent2Image: ImageView = view.findViewById(R.id.opponent2Image)
        val gameStatus: TextView = view.findViewById(R.id.gameStatus)
        val fullScore: TextView = view.findViewById(R.id.fullScore)
        val matchVenue: TextView = view.findViewById(R.id.matchVenue)
        val matchDate: TextView = view.findViewById(R.id.matchDate)
        val matchTime: TextView = view.findViewById(R.id.matchTime)
    }
}