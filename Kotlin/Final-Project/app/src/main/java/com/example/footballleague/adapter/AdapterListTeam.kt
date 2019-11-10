package com.example.footballleague.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.activity.TeamDetailActivity
import com.example.footballleague.data.api.pojo.team.Team
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.team_list.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity

class AdapterListTeam(
    private val context: Context,
    private val items: List<Team>
) : RecyclerView.Adapter<AdapterListTeam.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], context)
        holder.view.setOnClickListener {
            context.startActivity<TeamDetailActivity>(
                "idTeam" to items[position].idTeam
            )
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(items: Team, context: Context) {
            val detailLigaViewModel: DetailLigaViewModel by lazy {
                ViewModelProviders.of(context as FragmentActivity)
                    .get(DetailLigaViewModel::class.java)
            }

            itemView.team_name.text = items.strTeam
            Picasso.get().load(items.strTeamBadge).into(itemView.team_image)
            var fav = items.favorite
            when (items.favorite) {
                true -> {
                    itemView.fav_team_icon.image =
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                }
                else -> {
                    itemView.fav_team_icon.image =
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_favorite_border_red
                        )
                }
            }

            itemView.fav_team_icon.setOnClickListener {
                var message: String
                when (fav) {
                    false -> {
                        itemView.fav_team_icon.image =
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                        fav = true
                        message = "Add to favorite successfully"
                    }
                    else -> {
                        itemView.fav_team_icon.image =
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_favorite_border_red
                            )
                        fav = false
                        message = "remove from favorite successfully"
                    }
                }
                detailLigaViewModel.updateFavoriteTeamStatus(fav, items.idTeam.toString())
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}