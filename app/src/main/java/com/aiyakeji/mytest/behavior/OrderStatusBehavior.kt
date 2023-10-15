package com.aiyakeji.mytest.behavior

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class OrderStatusBehavior @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : CoordinatorLayout.Behavior<View>(context, attributeSet), Animator.AnimatorListener {
    companion object {
        const val MAX_PEEK = 1300f
        const val ALPHA_SPEED = 3f * 100
        const val ANIM_DURATION = 300L
        const val SCALE_PERCENT = 0.15f
    }

    var listener: OrderStatusListener? = null

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (child.translationY >= 0) {
            // header固定状态下

            // diff得出的就是rv顶部与header下边界的相差距离，也就是还差多少可以进入下一阶段
            // ALPHA_SPEED是一个阈值距离，就是多少距离开始进入渐变状态
            val diff = dependency.translationY - child.height
            if (diff < ALPHA_SPEED && diff >= 0) {
                // 这里转化为百分比
                child.alpha = (ALPHA_SPEED - diff) / ALPHA_SPEED
            } else if (diff >= ALPHA_SPEED) {
                child.alpha = 0f
            } else {
                child.alpha = 1f
            }
        }
        return true
    }

    // child是自身；target是协调的目标view；dx\dy是x\y轴的滑动，向右为x轴u正方向，向下为y轴正方向，可以尝试画图辅助理解
    // consumed是消费数组，[x,y]记录了x\y轴的滑动消费情况，如果需要消费，那就需要记录
    // 如果不消费的话，那么不管你怎么滑，Rv自身在后续环节还会自身滑动，因为没有消费完
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (animaState) {
            // 动画正在执行中，所有滑动全部吞掉
            consumed[1] += dy
            return
        }
        if (type != ViewCompat.TYPE_TOUCH) {
            if (child.translationY >= 0) {
                // 如果顶部header还在，那就屏蔽fling
                consumed[1] += dy
                return
            }
        }
        if (dy > 0) {
            // 上滑

            // 初始y为0，上推过程中会逐步变到-height
            // 初始，y被设置为一个常量，MAX_PEEK = 1300f
            val y = target.translationY
            // 上推时，translationY会<0，所以以此判断header是否还固定在原位
            if (child.translationY >= 0 && y > child.height) {
                // 如果header固定时，那childY就是第一阶段中，rv的上界
                if (dy < y - child.height) {
                    // 滑动距离不足以使得rv达到上界，即滑动距离 < rv与header的之间的距离

                    // 此时，使得rv改变Y轴即可
                    target.translationY = y - dy
                    // 记录消费
                    consumed[1] += dy
                } else {
                    // 如果一次滑动量很大，那就先让rv抵达header处，并消费全部
                    // 这里其实是个简化，理论上 下一个分发阶段需要处理，这里偷懒直接忽略
                    target.translationY = child.height.toFloat()
                    consumed[1] += dy
                }
            } else {
                // 准备一起推
                if (y > 0) {
                    // 还没把header推完
                    if (y - dy >= 0) {
                        // 也还推不倒头，就一起动
                        // 这里target.translationY -= dy是一样的，我是因为既然y都记录了，索性用了
                        target.translationY = y - dy
                        child.translationY -= dy
                        consumed[1] += dy
                    } else {
                        // 先把剩下的推推完
                        // header其实也可以直接设置-child.height，当然这里-y是异曲同工
                        child.translationY -= y
                        // rv推到头，就是y位移为0
                        target.translationY = 0f
                        // 这里是重头戏啊，因为一起推的距离是rv剩余的y位移，剩下多余的是需要交给下一轮让rv自行去推的
                        // 所以这也是为什么header为什么-y更好也更恰当
                        consumed[1] += y.toInt()
                    }
                    // ……这是一起推的阶段，还需要header进行一些scale和对外位移情况的暴露，先不关注
                    val percent = -child.translationY / child.height
                    child.scaleX = 1 - percent * SCALE_PERCENT
//                    child.scaleY = 1 - percent
                    listener?.onHeaderMove(percent, "配送中")
                } else {
                    // 推完了剩下就自己滑就好了
                }
            }
        } else {
            // 下拉
            (target as? RecyclerView)?.let {
                val offsetY = it.computeVerticalScrollOffset()
                if (offsetY > 0) {
                    // 说明原来已经滑动过了，因为前面的推动都是translationY变化，影响不到它自身

                    // 这里写了两个判断，但是没作处理，是因为…做处理的话就会太丝滑了，在fling状态下就会忽闪忽闪的
                    // 所以我们的思路是，过度消费，也就全全由rv自己先去滑，因为它最多也就滑到header消失时刻的状态
                    if (offsetY + dy < 0) {
                        // 滑动的多了
                    } else {
                        // target自己可以处理
                    }
                } else {
                    if (target.translationY >= MAX_PEEK) {
                        // 已经到底了，不允许继续下拉了，你可以尝试不加这个，看看效果Hh
                        return
                    }
                    if (target.translationY - dy > MAX_PEEK) {
                        // 拉过头就没了，这个同上，都是对PEEK_HEIGHT的兜底
                        // 对了，对于这个PEEK需要设置多少，你可以通过rv的height-需要露出的height得出
                        target.translationY = MAX_PEEK
                        return
                    }

                    // header的translationY标志着它的情况
                    if (child.translationY < 0) {
                        // 需要把header一块滑下来
                        if (child.translationY < dy) { // 因为带有方向，所以这两个都是负数，你需要理解成距离会更加合适
                            // 滑动距离不足以滑完header，那就一起动
                            child.translationY -= dy
                            target.translationY -= dy
                            consumed[1] += dy
                        } else {
                            // 如果够滑完的话，header就需要固定住了，把剩余的translationY滑掉
                            // 这里也是过度消费的思路，因为滑动距离过剩了，但我们希望先拉到固定贴合的状态先
                            // 而不是直接就下去了，太丝滑会不太好
                            // 不信邪的可以试试hhh
                            target.translationY -= child.translationY
                            child.translationY = 0f
                            consumed[1] += dy
                        }
                        // ……这是一起推的阶段，还需要header进行一些scale和对外位移情况的暴露，先不关注
                        val percent = -child.translationY / child.height
                        child.scaleX = 1 - percent * SCALE_PERCENT
//                        child.scaleY = 1 - percent
                        listener?.onHeaderMove(percent, "配送中")
                    } else {
                        // header已经固定好了，那就自己滑好了
                        target.translationY -= dy
                        consumed[1] += dy
                    }
                }
            }
        }
    }

    // child是自身，directTargetChild发起嵌套滑动的view，target也是
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        // 位运算，取vertical位，即垂直滑动
        return axes.and(ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        if (type == ViewCompat.TYPE_TOUCH) {
            // 仅处理touch，区别与not_touch，如fling
            super.onStopNestedScroll(coordinatorLayout, child, target, type)
            val childY = child.height.toFloat()
            val y = target.translationY
            if (y < MAX_PEEK && y > childY) {
                // 处于在中间状态中，即第一阶段状态

                // 这里判别阈值设置了一半，也可以根据需要自行调整
                val mid = (MAX_PEEK + childY) / 2f
                if (y > mid) {
                    // 回缩
                    peekViewAnim(target, y, MAX_PEEK)
                } else {
                    // 展开
                    peekViewAnim(target, y, childY)
                }
            }
        }
    }

    private fun peekViewAnim(view: View, start: Float, end: Float) {
        if (animaState) {
            return
        }
        animaState = true
        val anim = ObjectAnimator.ofFloat(view, "translationY", start, end)
        anim.duration = ANIM_DURATION
        anim.addListener(this)
        anim.start()
    }

    private var animaState = false
    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
        animaState = false
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }

    interface OrderStatusListener {
        fun onHeaderMove(percent: Float, title: String)
    }
}