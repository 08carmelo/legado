package io.legado.app.model

import io.legado.app.data.entities.RssArticle
import io.legado.app.data.entities.RssSource
import io.legado.app.help.coroutine.Coroutine
import io.legado.app.model.analyzeRule.AnalyzeRule
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.model.rss.RssParserByRule
import io.legado.app.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Rss {

    fun getArticles(
        rssSource: RssSource,
        scope: CoroutineScope = Coroutine.DEFAULT,
        context: CoroutineContext = Dispatchers.IO
    ): Coroutine<MutableList<RssArticle>> {
        return Coroutine.async(scope, context) {
            val analyzeUrl = AnalyzeUrl(rssSource.sourceUrl)
            val body = analyzeUrl.getResponseAwait(rssSource.sourceUrl).body
            RssParserByRule.parseXML(body, rssSource)
        }
    }

    fun getContent(
        rssArticle: RssArticle,
        ruleContent: String,
        scope: CoroutineScope = Coroutine.DEFAULT,
        context: CoroutineContext = Dispatchers.IO
    ): Coroutine<String> {
        return Coroutine.async(scope, context) {
            val body = AnalyzeUrl(rssArticle.link, baseUrl = rssArticle.origin)
                .getResponseAwait()
                .body
            val analyzeRule = AnalyzeRule()
            analyzeRule.setContent(
                body,
                NetworkUtils.getAbsoluteURL(rssArticle.origin, rssArticle.link)
            )
            analyzeRule.getString(ruleContent)
        }
    }
}