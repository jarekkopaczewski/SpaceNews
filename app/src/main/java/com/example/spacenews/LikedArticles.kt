package com.example.spacenews

class LikedArticles {
    companion object  {
        private var likedArticles: MutableList<Int> = mutableListOf()
        private var tempCount: Int  = 0

        fun addToLiked(id: Int)
        {
            println(likedArticles.size)
            this.likedArticles.add(id)
        }

        fun removeFromLiked(id: Int)
        {
            println(likedArticles.size)
            try {
                this.likedArticles.removeAt(likedArticles.indexOf(id))
            } catch (e: ArrayIndexOutOfBoundsException) {
                println("Index")
            }
        }

        fun getLikedArticles():MutableList<Int> = this.likedArticles


        fun setCount(number: Int)
        {
            this.tempCount = number
        }

        fun getCount(): Int = this.tempCount
    }
}
