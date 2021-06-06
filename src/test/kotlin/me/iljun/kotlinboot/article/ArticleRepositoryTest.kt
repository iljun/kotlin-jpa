package me.iljun.kotlinboot.article

import me.iljun.kotlinboot.user.User
import me.iljun.kotlinboot.user.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ArticleRepositoryTest @Autowired constructor(
    val articleRepository: ArticleRepository,
    var userRepository: UserRepository
){

    var title = "kotlin jpa practice"
    var headline = "kotlin jpa"
    var content = "kotlin pratice with jpa"
    var author = User("login", "ethan", "won")
    var slug = "kotlin"

    fun saveArticle() : Article {
        author = userRepository.save(author)
        val article = Article(title, headline, content, author, slug)
        return articleRepository.save(article)
    }

    @Test
    fun `save Article entity`() {
        val article = saveArticle()

        Assertions.assertNotNull(article.id)
        Assertions.assertEquals(article.title, title)
        Assertions.assertEquals(article.headline, headline)
        Assertions.assertEquals(article.content, content)
        Assertions.assertEquals(article.author, author)
        Assertions.assertEquals(article.slug, slug)
        Assertions.assertNotNull(article.addedAt)
    }

    @Test
    fun `update Article entity`() {
        val article = saveArticle()
        val slug = "update slug"
        val articleId = article.id
        article.slug = slug
        articleRepository.save(article)

        Assertions.assertEquals(article.id, articleId)

        Assertions.assertEquals(article.title, title)
        Assertions.assertEquals(article.headline, headline)
        Assertions.assertEquals(article.content, content)
        Assertions.assertEquals(article.author, author)
        Assertions.assertEquals(article.slug, slug)
        Assertions.assertNotNull(article.addedAt)
    }

    @Test
    fun `find Article entity`() {
        val article = saveArticle()
        val articleId = article.id

        articleId?.let {
            val selectArticle = articleRepository.findById(it).get()
            Assertions.assertEquals(articleId, selectArticle.id)
        }
    }

    @Test
    fun `multiple Author`() {
        val article = saveArticle()
        val author = article.author

        val secondArticle = Article(title, headline, content, author, slug)
        articleRepository.save(secondArticle)

        val articles = articleRepository.findByAuthor(author)
        Assertions.assertNotNull(articles)
    }
}
