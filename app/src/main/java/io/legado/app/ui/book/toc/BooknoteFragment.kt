package io.legado.app.ui.book.toc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import io.legado.app.R
import io.legado.app.base.VMBaseFragment
import io.legado.app.data.appDb
import io.legado.app.data.entities.Booknote
import io.legado.app.databinding.FragmentBooknoteBinding
import io.legado.app.lib.theme.primaryColor
import io.legado.app.ui.widget.recycler.UpLinearLayoutManager
import io.legado.app.ui.widget.recycler.VerticalDivider
import io.legado.app.utils.setEdgeEffectColor
import io.legado.app.utils.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BooknoteFragment : VMBaseFragment<TocViewModel>(R.layout.fragment_booknote),
    BooknoteAdapter.Callback,
    TocViewModel.BookmarkCallBack{
    override val viewModel by activityViewModels<TocViewModel>()
    private val binding by viewBinding(FragmentBooknoteBinding::bind)
    private val mLayoutManager by lazy { UpLinearLayoutManager(requireContext()) }
    private val adapter by lazy { BooknoteAdapter(requireContext(), this) }
    private var durChapterIndex = 0

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.bookMarkCallBack = this
        initRecyclerView()
        viewModel.bookData.observe(this) {
            durChapterIndex = it.durChapterIndex
            upBookmark(null)
        }
    }

    override fun upBookmark(searchKey: String?) {
        val book = viewModel.bookData.value ?: return
        launch {
            withContext(IO) {
                when {
                    searchKey.isNullOrBlank() -> appDb.booknoteDao.getByBook(book.name, book.author)
                    else -> appDb.booknoteDao.search(book.name, book.author, searchKey)
                }
            }.let {
                adapter.setItems(it)
                var scrollPos = 0
                withContext(Dispatchers.Default) {
                    adapter.getItems().forEachIndexed { index, booknote ->
                        if (booknote.chapterIndex >= durChapterIndex) {
                            return@withContext
                        }
                        scrollPos = index
                    }
                }
                mLayoutManager.scrollToPositionWithOffset(scrollPos, 0)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.setEdgeEffectColor(primaryColor)
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.addItemDecoration(VerticalDivider(requireContext()))
        binding.recyclerView.adapter = adapter
    }

    override fun onClick(booknote: Booknote) {
        activity?.run {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("index", booknote.chapterIndex)
                putExtra("chapterPos", booknote.chapterPos)
            })
            finish()
        }
    }

}