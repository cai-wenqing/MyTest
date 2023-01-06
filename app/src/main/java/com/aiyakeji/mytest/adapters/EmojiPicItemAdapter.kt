package com.sinovoice.hci.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.EmojiUtils

/**
 * @Author:CWQ
 * @DATE:2022/12/30
 * @DESC:
 */
class EmojiPicItemAdapter(private val type: Int) : Adapter<EmojiPicItemAdapter.EmojiItemHolder>() {

    private var mList: List<String> = EmojiUtils.getEmojiList(type)
    private val mEmojiMap: HashMap<String, Int> = EmojiUtils.getEmojiMap(type)

    private var mListener: ((Int, String, Int?) -> Unit)? = null

    /**
     *
     * @param listener Function3<type, emojiName, emojiResId?, Unit>
     */
    fun setOnItemListener(listener: ((Int, String, Int?) -> Unit)) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_emoji_pic, parent, false)
        return EmojiItemHolder(view)
    }

    override fun onBindViewHolder(holder: EmojiItemHolder, position: Int) {
        val emojiResId = mEmojiMap[mList[position]]
        emojiResId?.let {
            holder.ivEmoji.setImageResource(it)
        }
        holder.itemView.setOnClickListener {
            mListener?.invoke(type, mList[position], mEmojiMap[mList[position]])
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class EmojiItemHolder(item: View) : ViewHolder(item) {
        val ivEmoji: ImageView = item.findViewById(R.id.iv_emoji)
    }
}