package io.legado.app.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.legado.app.R
import io.legado.app.ui.book.read.ReadBookActivity
import io.legado.app.utils.startActivity

/**
 * author: maolin
 * date: 2022/3/11
 * des:
 */
class IndexActivity: AppCompatActivity() {

    val EPUB_BOOK_PATH = "/storage/emulated/0/007/陪孩子终身成长.epub"

    val PDF_BOOK_PATH = "/storage/emulated/0/007/陪孩子终身成长.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index)

        findViewById<Button>(R.id.tv1).setOnClickListener {
            startActivity<ReadBookActivity> {
                putExtra("bookUrl", EPUB_BOOK_PATH)
            }
        }

        findViewById<Button>(R.id.tv2).setOnClickListener {

        }
    }
}