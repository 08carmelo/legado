package io.legado.app.ui.book.toc

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import io.legado.app.data.entities.TocActivityResultBean

class TocActivityResult : ActivityResultContract<String, TocActivityResultBean?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, TocActivity::class.java)
            .putExtra("bookUrl", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): TocActivityResultBean? {
        if (resultCode == RESULT_OK) {
            intent?.let {
                return TocActivityResultBean(
                    it.getIntExtra("index", 0),
                    it.getIntExtra("chapterPos", 0),
                    it.getIntExtra("pageIndex", 0)
                )
            }
        }
        return null
    }
}