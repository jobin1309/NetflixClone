package com.example.netflixclone

import androidx.recyclerview.widget.DiffUtil
import com.example.netflixclone.model.MoviesResult

class MovieDiffUtil(
    private val oldList: List<MoviesResult>,
    private val newList: List<MoviesResult>

) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}