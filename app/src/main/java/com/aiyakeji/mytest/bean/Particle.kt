package com.aiyakeji.mytest.bean

/**
 * @author CWQ
 * @date 2020/10/20
 */
class Particle(
        var x: Float,
        var y: Float,
        var radius: Float,
        var speed: Float,
        var alpha: Int,
        var maxOffset: Float,//最大移动距离
        var offset: Float,//当前移动距离
        var angle: Double//粒子角度
)