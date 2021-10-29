package com.example.spacenews

class LikedArticles
{
    companion object
    {
        private var likedArticles: MutableList<Int> = mutableListOf()
        private var tempCount: Int  = 1
        private var homeCount: Int  = 1

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
                println("$id")
            }
        }

        fun setHomeCount(number: Int)
        {
            this.homeCount = number
        }

        fun setCount(number: Int)
        {
            this.tempCount = number
        }

        fun getHomeCount(): Int = this.homeCount

        fun getCount(): Int = this.tempCount

        fun getLikedArticles():MutableList<Int> = this.likedArticles
    }
}
