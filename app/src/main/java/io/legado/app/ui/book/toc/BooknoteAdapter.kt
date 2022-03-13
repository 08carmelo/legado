package io.legado.app.ui.book.toc

import android.content.Context
import android.view.ViewGroup
import io.legado.app.base.adapter.ItemViewHolder
import io.legado.app.base.adapter.RecyclerAdapter
import io.legado.app.data.entities.Booknote
import io.legado.app.databinding.ItemBooknoteBinding
import splitties.views.onLongClick

class BooknoteAdapter(context: Context, val callback: Callback) :
    RecyclerAdapter<Booknote, ItemBooknoteBinding>(context) {

    override fun getViewBinding(parent: ViewGroup): ItemBooknoteBinding {
        return ItemBooknoteBinding.inflate(inflater, parent, false)
    }

    override fun convert(
        holder: ItemViewHolder,
        binding: ItemBooknoteBinding,
        item: Booknote,
        payloads: MutableList<Any>
    ) {
        binding.tvChapterName.text = item.chapterName
        binding.tvContent.text = item.content
    }

    override fun registerListener(holder: ItemViewHolder, binding: ItemBooknoteBinding) {
        binding.root.setOnClickListener {
            getItem(holder.layoutPosition)?.let { booknote ->
                callback.onClick(booknote)
            }
        }
    }

    interface Callback {
        fun onClick(booknote: Booknote)
    }

}