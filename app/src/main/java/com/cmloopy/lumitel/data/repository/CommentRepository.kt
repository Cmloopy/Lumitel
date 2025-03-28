package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.models.Comment

class CommentRepository {
    fun getCommentExample(): List<Comment>{
        return listOf(
            Comment("These are really commendable. The more you try to learn engineering, the more interesting it becomes. Sir I request you to make a detailed video on Ultra High "),
            Comment("The more you try to learn engineering, the more interesting it becomes."),
            Comment("The more you try to learn engineering, the more interesting it becomes. The more you try to learn engineering, the more interesting it becomes."),
            Comment("These are really commendable. The more you try to learn engineering, the more interesting it becomes. Sir I request you to make a detailed video on Ultra High "),
            Comment("The more you try to learn engineering, the more interesting it becomes."),
            Comment("The more you try to learn engineering, the more interesting it becomes. The more you try to learn engineering, the more interesting it becomes.")
        )
    }
}