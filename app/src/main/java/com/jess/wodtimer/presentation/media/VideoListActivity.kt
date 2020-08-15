package com.jess.wodtimer.presentation.media

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.common.base.BaseRecyclerViewAdapter
import com.jess.wodtimer.common.decoration.DividerItemGrid
import com.jess.wodtimer.data.MediaData
import com.jess.wodtimer.databinding.VideoListActivityBinding
import com.jess.wodtimer.databinding.VideoListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header_view.*
import kotlinx.android.synthetic.main.video_list_activity.*

/**
 * @author jess
 * @since 2020.08.11
 */
@AndroidEntryPoint
class VideoListActivity : BaseActivity<VideoListActivityBinding, VideoListViewModel>(),
    View.OnClickListener {

    override val layoutRes get() = R.layout.video_list_activity
    override val viewModelClass get() = VideoListViewModel::class

    override fun initLayout() {
        rv_media.run {
            addItemDecoration(DividerItemGrid(resources.getDimensionPixelSize(R.dimen.dp2)))
            adapter = object : BaseRecyclerViewAdapter<MediaData, VideoListItemBinding>(
                R.layout.video_list_item
            ) {

            }.apply {
                setOnItemClickListener { view, item ->
                    item?.uri?.let {
                        startActivity(
                            Intent(Intent.ACTION_VIEW, item.uri)
                        )
                    }

                }
            }
        }

        arrayOf(iv_finish).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        initObserve()
        vm.getVideoList(this)
    }

    private fun initObserve() {
        vm.mediaList.observe(this, Observer {

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_finish -> finish()
        }
    }
}
