package io.legado.app.ui.book.toc

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import io.legado.app.R
import io.legado.app.base.BaseDialogFragment
import io.legado.app.data.appDb
import io.legado.app.data.entities.Booknote
import io.legado.app.databinding.DialogBooknoteBinding
import io.legado.app.lib.theme.primaryColor
import io.legado.app.ui.book.read.SearchMenu
import io.legado.app.utils.activity
import io.legado.app.utils.setLayout
import io.legado.app.utils.viewbindingdelegate.viewBinding
import io.legado.app.utils.visible
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 笔记弹窗
 */
class BookNoteDialog() : BaseDialogFragment(R.layout.dialog_booknote) {

    companion object{
        const val BOOK_NOTE = "booknote"
        const val EDIT_POS = "editPos"
    }

    constructor(booknote: Booknote, editPos: Int = -1) : this() {
        arguments = Bundle().apply {
            putInt(EDIT_POS, editPos)
            putParcelable(BOOK_NOTE, booknote)
        }
    }

    private val binding by viewBinding(DialogBooknoteBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onStart() {
        super.onStart()
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolBar.setBackgroundColor(primaryColor)
        val arguments = arguments ?: let {
            dismiss()
            return
        }
        val booknote = arguments.getParcelable<Booknote>(BOOK_NOTE)
        booknote ?: let {
            dismiss()
            return
        }
        val editPos = arguments.getInt(EDIT_POS, -1)
        binding.tvFooterLeft.visible(editPos >= 0)
        binding.run {
            tvChapterName.text = booknote.chapterName
            editBookText.setText(booknote.content)
            tvCancel.setOnClickListener {
                callBack.cancelNote()
                dismiss()
            }
            tvOk.setOnClickListener {
                booknote.content = editBookText.text?.toString() ?: ""
                launch {
                    withContext(IO) {
                        appDb.booknoteDao.insert(booknote)
                    }
//                    getCallback()?.upBooknote(editPos, booknote)
                    dismiss()
                }
            }
            tvFooterLeft.setOnClickListener {
                launch {
                    withContext(IO) {
                        appDb.booknoteDao.delete(booknote)
                    }
//                    getCallback()?.deleteBooknote(editPos)
                    dismiss()
                }
            }
        }
    }

    fun getCallback(): Callback? {
        return parentFragment as? Callback
    }

    private val callBack: Callback get() = activity as Callback

    interface Callback {

//        fun upBooknote(pos: Int, booknote: Booknote){}
//
//        fun deleteBooknote(pos: Int){}

        fun cancelNote()

    }

}