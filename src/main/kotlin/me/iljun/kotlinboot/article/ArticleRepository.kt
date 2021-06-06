package me.iljun.kotlinboot.article

import me.iljun.kotlinboot.user.User
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findByAuthor(author: User): Iterable<Article>
}
