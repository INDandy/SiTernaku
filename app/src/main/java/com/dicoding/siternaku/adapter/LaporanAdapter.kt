package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.data.LaporanAyam

class LaporanAdapter :
    ListAdapter<LaporanAyam, LaporanAdapter.LaporanViewHolder>(DIFF) {

    inner class LaporanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        private val tvDetail = itemView.findViewById<TextView>(R.id.tvDetail)

        fun bind(data: LaporanAyam) {
            tvTanggal.text = data.tanggal
            tvDetail.text = data.ringkasan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laporan, parent, false)
        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<LaporanAyam>() {
            override fun areItemsTheSame(oldItem: LaporanAyam, newItem: LaporanAyam) =
                oldItem.tanggal == newItem.tanggal

            override fun areContentsTheSame(oldItem: LaporanAyam, newItem: LaporanAyam) =
                oldItem == newItem
        }
    }
}