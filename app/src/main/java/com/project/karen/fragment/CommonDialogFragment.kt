package com.project.karen.fragment

import com.project.base.R
import com.project.base.ui.BaseDialogFragment

class CommonDialogFragment : BaseDialogFragment(R.layout.base_dialog_common) {
    override fun bindContent(holder: ViewHolder) {
        holder.setText(R.id.tv_title, "警告")
        for (id in intArrayOf(R.id.btn_left, R.id.btn_right, R.id.top_button, R.id.bottom_button)) {
            holder.setOnClickListener(id) { holder.close() }
        }
        holder.root.setOnClickListener { holder.close() }
    }
}
