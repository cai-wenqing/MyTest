package com.aiyakeji.mytest.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.core.content.res.ResourcesCompat
import com.aiyakeji.mytest.R
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @Author:CWQ
 * @DATE:2023/1/5
 * @DESC:
 */
object EmojiUtils {

    private val weChatList: ArrayList<String> = arrayListOf()
    private val weChatMap: HashMap<String, Int> = hashMapOf()
    private val qqHashMap: HashMap<String, Int> = hashMapOf()
    private val qqList: ArrayList<String> = arrayListOf()

    private const val reg = "\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]" //校验表情正则
    private val pattern: Pattern = Pattern.compile(reg)

    val symbolList: ArrayList<String> = arrayListOf(
        "(눈_눈)",
        "⊙_⊙",
        "( ꒪ͧ⌓꒪ͧ)",
        "(๑•̌.•̑๑)ˀ",
        "-_-||",
        "┐（─__─）┌",
        "ヽ（・＿・；)ノ",
        "ヽ(≧Д≦)ノ",
        "⊙▽⊙",
        "(●°u°●)\u200B 」",
        "(>﹏<)",
        "(〜￣▽￣)〜",
        "(￣o￣) . z Z",
        "(ﾟoﾟ;",
        ",,Ծ^Ծ,,",
        "(✪▽✪)",
        "→_→",
        "٩(๑^o^๑)۶",
        "(≧∇≦)/",
        "(ﾟoﾟ;"
    )



    init {
        weChatMap["[呵呵]"] = R.drawable.d_hehe
        weChatMap["[可爱]"] = R.drawable.d_keai;
        weChatMap["[太开心]"] = R.drawable.d_taikaixin;
        weChatMap["[鼓掌]"] = R.drawable.d_guzhang;
        weChatMap["[嘻嘻]"] = R.drawable.d_xixi;
        weChatMap["[哈哈]"] = R.drawable.d_haha;
        weChatMap["[笑哭]"] = R.drawable.d_xiaoku;
        weChatMap["[调皮]"] = R.drawable.d_jiyan;
        weChatMap["[大馋嘴]"] = R.drawable.d_chanzui;
        weChatMap["[懒得理你]"] = R.drawable.d_landelini;
        weChatMap["[黑线]"] = R.drawable.d_heixian;
        weChatMap["[挖鼻屎]"] = R.drawable.d_wabishi;
        weChatMap["[哼]"] = R.drawable.d_heng;
        weChatMap["[怒]"] = R.drawable.d_nu;
        weChatMap["[抓狂]"] = R.drawable.d_zhuakuang;
        weChatMap["[委屈]"] = R.drawable.d_weiqu;
        weChatMap["[可怜]"] = R.drawable.d_kelian;
        weChatMap["[失望]"] = R.drawable.d_shiwang;
        weChatMap["[悲伤]"] = R.drawable.d_beishang;
        weChatMap["[流泪]"] = R.drawable.d_lei;
        weChatMap["[害羞]"] = R.drawable.d_haixiu;
        weChatMap["[捂]"] = R.drawable.d_wu;
        weChatMap["[爱你]"] = R.drawable.d_aini;
        weChatMap["[亲亲]"] = R.drawable.d_qinqin;
        weChatMap["[花心]"] = R.drawable.d_huaxin;
        weChatMap["[舔]"] = R.drawable.d_tian;
        weChatMap["[钱]"] = R.drawable.d_qian;
        weChatMap["[神烦狗]"] = R.drawable.d_doge;
        weChatMap["[喵]"] = R.drawable.d_miao;
        weChatMap["[二哈]"] = R.drawable.d_erha;
        weChatMap["[酷]"] = R.drawable.d_ku;
        weChatMap["[坏笑]"] = R.drawable.d_huaixiao;
        weChatMap["[阴险]"] = R.drawable.d_yinxian;
        weChatMap["[偷笑]"] = R.drawable.d_touxiao;
        weChatMap["[思考]"] = R.drawable.d_sikao;
        weChatMap["[疑问]"] = R.drawable.d_yiwen;
        weChatMap["[晕]"] = R.drawable.d_yun;
        weChatMap["[傻眼]"] = R.drawable.d_shayan;
        weChatMap["[衰]"] = R.drawable.d_shuai;
        weChatMap["[骷髅]"] = R.drawable.d_kulou;
        weChatMap["[嘘]"] = R.drawable.d_xu;
        weChatMap["[闭嘴]"] = R.drawable.d_bizui;
        weChatMap["[汗]"] = R.drawable.d_han;
        weChatMap["[吃惊]"] = R.drawable.d_chijing;
        weChatMap["[感冒]"] = R.drawable.d_ganmao;
        weChatMap["[生病]"] = R.drawable.d_shengbing;
        weChatMap["[吐]"] = R.drawable.d_tu;
        weChatMap["[拜拜]"] = R.drawable.d_baibai;
        weChatMap["[鄙视]"] = R.drawable.d_bishi;
        weChatMap["[左哼哼]"] = R.drawable.d_zuohengheng;
        weChatMap["[右哼哼]"] = R.drawable.d_youhengheng;
        weChatMap["[怒骂]"] = R.drawable.d_numa;
        weChatMap["[打脸]"] = R.drawable.d_dalian;
        weChatMap["[敲头]"] = R.drawable.d_ding;
        weChatMap["[打哈气]"] = R.drawable.d_dahaqi;
        weChatMap["[困]"] = R.drawable.d_kun;
        weChatMap["[互粉]"] = R.drawable.f_hufen;
        weChatMap["[抱抱]"] = R.drawable.d_baobao;
        weChatMap["[摊手]"] = R.drawable.d_tanshou;
        weChatMap["[心]"] = R.drawable.l_xin;
        weChatMap["[伤心]"] = R.drawable.l_shangxin;
        weChatMap["[鲜花]"] = R.drawable.w_xianhua;
        weChatMap["[男孩儿]"] = R.drawable.d_nanhaier;
        weChatMap["[女孩儿]"] = R.drawable.d_nvhaier;
        weChatMap["[握手]"] = R.drawable.h_woshou;
        weChatMap["[作揖]"] = R.drawable.h_zuoyi;
        weChatMap["[赞]"] = R.drawable.h_zan;
        weChatMap["[耶]"] = R.drawable.h_ye;
        weChatMap["[好]"] = R.drawable.h_good;
        weChatMap["[弱]"] = R.drawable.h_ruo;
        weChatMap["[不要]"] = R.drawable.h_buyao;
        weChatMap["[好的]"] = R.drawable.h_ok;
        weChatMap["[我爱你]"] = R.drawable.h_haha;
        weChatMap["[来]"] = R.drawable.h_lai;
        weChatMap["[熊猫]"] = R.drawable.d_xiongmao;
        weChatMap["[兔子]"] = R.drawable.d_tuzi;
        weChatMap["[猪头]"] = R.drawable.d_zhutou;
        weChatMap["[神兽]"] = R.drawable.d_shenshou;
        weChatMap["[奥特曼]"] = R.drawable.d_aoteman;
        weChatMap["[太阳]"] = R.drawable.w_taiyang;
        weChatMap["[月亮]"] = R.drawable.w_yueliang;
        weChatMap["[浮云]"] = R.drawable.w_fuyun;
        weChatMap["[下雨]"] = R.drawable.w_xiayu;
        weChatMap["[沙尘暴]"] = R.drawable.w_shachenbao;
        weChatMap["[微风]"] = R.drawable.w_weifeng;
        weChatMap["[飞机]"] = R.drawable.o_feiji;
        weChatMap["[照相机]"] = R.drawable.o_zhaoxiangji;
        weChatMap["[话筒]"] = R.drawable.o_huatong;
        weChatMap["[音乐]"] = R.drawable.o_yinyue;
        weChatMap["[给力]"] = R.drawable.f_geili;
        weChatMap["[囧]"] = R.drawable.f_jiong;
        weChatMap["[萌]"] = R.drawable.f_meng;
        weChatMap["[神马]"] = R.drawable.f_shenma;
        weChatMap["[织]"] = R.drawable.f_zhi;
        weChatMap["[最右]"] = R.drawable.d_zuiyou;
        weChatMap["[蜡烛]"] = R.drawable.o_lazhu;
        weChatMap["[围观]"] = R.drawable.o_weiguan;
        weChatMap["[干杯]"] = R.drawable.o_ganbei;
        weChatMap["[蛋糕]"] = R.drawable.o_dangao;
        weChatMap["[礼物]"] = R.drawable.o_liwu;
        weChatMap["[囍]"] = R.drawable.f_xi;
        weChatMap["[钟]"] = R.drawable.o_zhong;
        weChatMap["[肥皂]"] = R.drawable.d_feizao;
        weChatMap["[绿丝带]"] = R.drawable.o_lvsidai;
        weChatMap["[围脖]"] = R.drawable.o_weibo;


        weChatList.add("[呵呵]");
        weChatList.add("[可爱]");
        weChatList.add("[太开心]");
        weChatList.add("[鼓掌]");
        weChatList.add("[嘻嘻]");
        weChatList.add("[哈哈]");
        weChatList.add("[笑哭]");
        weChatList.add("[调皮]");
        weChatList.add("[大馋嘴]");
        weChatList.add("[懒得理你]");
        weChatList.add("[黑线]");
        weChatList.add("[挖鼻屎]");
        weChatList.add("[哼]");
        weChatList.add("[怒]");
        weChatList.add("[抓狂]");
        weChatList.add("[委屈]");
        weChatList.add("[可怜]");
        weChatList.add("[失望]");
        weChatList.add("[悲伤]");
        weChatList.add("[流泪]");
        weChatList.add("[害羞]");
        weChatList.add("[捂]");
        weChatList.add("[爱你]");
        weChatList.add("[亲亲]");
        weChatList.add("[花心]");
        weChatList.add("[舔]");
        weChatList.add("[钱]");
        weChatList.add("[神烦狗]");
        weChatList.add("[喵]");
        weChatList.add("[二哈]");
        weChatList.add("[酷]");
        weChatList.add("[坏笑]");
        weChatList.add("[阴险]");
        weChatList.add("[偷笑]");
        weChatList.add("[思考]");
        weChatList.add("[疑问]");
        weChatList.add("[晕]");
        weChatList.add("[傻眼]");
        weChatList.add("[衰]");
        weChatList.add("[骷髅]");
        weChatList.add("[嘘]");
        weChatList.add("[闭嘴]");
        weChatList.add("[汗]");
        weChatList.add("[吃惊]");
        weChatList.add("[感冒]");
        weChatList.add("[生病]");
        weChatList.add("[吐]");
        weChatList.add("[拜拜]");
        weChatList.add("[鄙视]");
        weChatList.add("[左哼哼]");
        weChatList.add("[右哼哼]");
        weChatList.add("[怒骂]");
        weChatList.add("[打脸]");
        weChatList.add("[敲头]");
        weChatList.add("[打哈气]");
        weChatList.add("[困]");
        weChatList.add("[互粉]");
        weChatList.add("[抱抱]");
        weChatList.add("[摊手]");
        weChatList.add("[心]");
        weChatList.add("[伤心]");
        weChatList.add("[鲜花]");
        weChatList.add("[男孩儿]");
        weChatList.add("[女孩儿]");
        weChatList.add("[握手]");
        weChatList.add("[作揖]");
        weChatList.add("[赞]");
        weChatList.add("[耶]");
        weChatList.add("[好]");
        weChatList.add("[弱]");
        weChatList.add("[不要]");
        weChatList.add("[好的]");
        weChatList.add("[我爱你]");
        weChatList.add("[来]");
        weChatList.add("[熊猫]");
        weChatList.add("[兔子]");
        weChatList.add("[猪头]");
        weChatList.add("[神兽]");
        weChatList.add("[奥特曼]");
        weChatList.add("[太阳]");
        weChatList.add("[月亮]");
        weChatList.add("[浮云]");
        weChatList.add("[下雨]");
        weChatList.add("[沙尘暴]");
        weChatList.add("[微风]");
        weChatList.add("[飞机]");
        weChatList.add("[照相机]");
        weChatList.add("[话筒]");
        weChatList.add("[音乐]");
        weChatList.add("[给力]");
        weChatList.add("[囧]");
        weChatList.add("[萌]");
        weChatList.add("[神马]");
        weChatList.add("[织]");
        weChatList.add("[最右]");
        weChatList.add("[蜡烛]");
        weChatList.add("[围观]");
        weChatList.add("[干杯]");
        weChatList.add("[蛋糕]");
        weChatList.add("[礼物]");
        weChatList.add("[囍]");
        weChatList.add("[钟]");
        weChatList.add("[肥皂]");
        weChatList.add("[绿丝带]");
        weChatList.add("[围脖]");


        qqHashMap["[ecf]"] = R.drawable.ecf;
        qqHashMap["[ecv]"] = R.drawable.ecv;
        qqHashMap["[ecb]"] = R.drawable.ecb;
        qqHashMap["[ecy]"] = R.drawable.ecy;
        qqHashMap["[ebu]"] = R.drawable.ebu;
        qqHashMap["[ebr]"] = R.drawable.ebr;
        qqHashMap["[ecc]"] = R.drawable.ecc;
        qqHashMap["[eft]"] = R.drawable.eft;
        qqHashMap["[ecr]"] = R.drawable.ecr;
        qqHashMap["[ebs]"] = R.drawable.ebs;
        qqHashMap["[ech]"] = R.drawable.ech;
        qqHashMap["[ecg]"] = R.drawable.ecg;
        qqHashMap["[ebh]"] = R.drawable.ebh;
        qqHashMap["[ebg]"] = R.drawable.ebg;
        qqHashMap["[ecp]"] = R.drawable.ecp;
        qqHashMap["[deg]"] = R.drawable.deg;
        qqHashMap["[ecd]"] = R.drawable.ecd;
        qqHashMap["[ecj]"] = R.drawable.ecj;
        qqHashMap["[ebv]"] = R.drawable.ebv;
        qqHashMap["[ece]"] = R.drawable.ece;
        qqHashMap["[ebl]"] = R.drawable.ebl;
        qqHashMap["[eca]"] = R.drawable.eca;
        qqHashMap["[ecn]"] = R.drawable.ecn;
        qqHashMap["[eco]"] = R.drawable.eco;
        qqHashMap["[eeo]"] = R.drawable.eeo;
        qqHashMap["[eep]"] = R.drawable.eep;
        qqHashMap["[eci]"] = R.drawable.eci;
        qqHashMap["[ebj]"] = R.drawable.ebj;
        qqHashMap["[eer]"] = R.drawable.eer;
        qqHashMap["[edi]"] = R.drawable.edi;
        qqHashMap["[ebq]"] = R.drawable.ebq;
        qqHashMap["[eeq]"] = R.drawable.eeq;
        qqHashMap["[ecq]"] = R.drawable.ecq;
        qqHashMap["[ebt]"] = R.drawable.ebt;
        qqHashMap["[ede]"] = R.drawable.ede;
        qqHashMap["[eew]"] = R.drawable.eew;
        qqHashMap["[eex]"] = R.drawable.eex;
        qqHashMap["[dga]"] = R.drawable.dga;
        qqHashMap["[ebp]"] = R.drawable.ebp;
        qqHashMap["[ebo]"] = R.drawable.ebo;


        qqList.add("[ecf]");
        qqList.add("[ecv]");
        qqList.add("[ecb]");
        qqList.add("[ecy]");
        qqList.add("[ebu]");
        qqList.add("[ebr]");
        qqList.add("[ecc]");
        qqList.add("[eft]");
        qqList.add("[ecr]");
        qqList.add("[ebs]");
        qqList.add("[ech]");
        qqList.add("[ecg]");
        qqList.add("[ebh]");
        qqList.add("[ebg]");
        qqList.add("[ecp]");
        qqList.add("[deg]");
        qqList.add("[ecd]");
        qqList.add("[ecj]");
        qqList.add("[ebv]");
        qqList.add("[ece]");
        qqList.add("[ebl]");
        qqList.add("[eca]");
        qqList.add("[ecn]");
        qqList.add("[eco]");
        qqList.add("[eeo]");
        qqList.add("[eep]");
        qqList.add("[eci]");
        qqList.add("[ebj]");
        qqList.add("[eer]");
        qqList.add("[edi]");
        qqList.add("[ebq]");
        qqList.add("[eeq]");
        qqList.add("[ecq]");
        qqList.add("[ebt]");
        qqList.add("[ede]");
        qqList.add("[eew]");
        qqList.add("[eex]");
        qqList.add("[dga]");
        qqList.add("[ebp]");
        qqList.add("[ebo]");
    }

    /**
     * 获取名称列表
     * @param type Int 0微信 1qq
     * @return ArrayList<String>
     */
    fun getEmojiList(type: Int): ArrayList<String> {
        return if (type == 0) {
            weChatList
        } else {
            qqList
        }
    }


    fun getEmojiMap(type: Int): HashMap<String, Int> {
        return if (type == 0) {
            weChatMap
        } else {
            qqHashMap
        }
    }


    fun getEmojiResId(type: Int, name: String): Int? {
        return getEmojiMap(type)[name]
    }


    /**
     * 解析EmoJi表情
     *
     * @param type
     * @param context
     * @param content
     * @return
     */
    fun parseEmoJi(type: Int, context: Context, content: String): SpannableString {
        val spannable = SpannableString(content)
        val matcher: Matcher = pattern.matcher(content)
        while (matcher.find()) {
            val regEmoJi: String = matcher.group() //获取匹配到的emoji字符串
            val start: Int = matcher.start() //匹配到字符串的开始位置
            val end: Int = matcher.end() //匹配到字符串的结束位置
            val resId: Int? = getEmojiMap(type)[regEmoJi] //通过emoji名获取对应的表情id
            if (resId != null) {
                val drawable: Drawable? =
                    ResourcesCompat.getDrawable(context.resources, resId, null)
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    val imageSpan = ImageSpan(drawable, content)
                    spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        return spannable
    }
}