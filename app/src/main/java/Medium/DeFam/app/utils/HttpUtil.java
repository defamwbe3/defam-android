package Medium.DeFam.app.utils;

public class HttpUtil {
    //发送验证码
    public static final String SMSCODE = "/api/sms/code";
    //手机验证码登录
    public static final String CODE = "/api/login/code";
    //登录(账号密码)
    public static final String APILOG = "/api/login";
    //注册
    public static final String REGISTER = "/api/register";
    //邮箱注册(新)
    public static final String REGISTEREMAIL = "/api/registerEmail";
    //首页-轮播图
    public static final String BANNER = "/api/banner";
    //协议
    public static final String AGREEMENT = "/api/agreement";
    //注销用户
    public static final String DELMEMBER = "/api/member/delmemebr";
    //企业认证-申请
    public static final String AUTHENTERPRISE = "/api/authenterprise";
    //首页-热门资讯-列表
    public static final String INDEXREALINFO = "/api/realinfo/index_realinfo";
    //资讯-详情
    public static final String REALINFOREAD = "/api/realinfo/read";
    //文章-评论列表带俩子评论
    public static final String APICOMMENT = "/api/comment";
    //评论-点赞or取消
    public static final String APICOMMENTDOLIKE = "/api/comment/dolike";
    //活动-活动方列表
    public static final String ACTIVEPARTY = "/api/authenterprise/activeParty";
    //通用-点赞(不包括评论)
    public static final String THUMBSUPDOLIKE = "/api/thumbsup/dolike";
    //收藏-添加收藏or删除
    public static final String COLLECTIONSAVE = "/api/membercollection/collection_save";
    //文章-评论列表所有子评论
    public static final String COMMENTCHILDREN = "/api/comment/comment_children";
    //评论-发表评论
    public static final String COMMENTSAVE = "/api/comment/save";
    //热门活动等
    public static final String HOTACTIVITY = "/api/activity/hot_activity";
    //活动-详情
    public static final String ACTIVITYDETAIL = "/api/activity/detail";
    //关注-关注or取消关注(12.28新增用户id)
    public static final String ISFOLLOWMEMBER = "/api/memberfollow/is_followMember";
    //活动-报名
    public static final String ACTIVITYSIGNUP = "/api/activitysignup/activitySignUp";
    //获取用户信息
    public static final String MEMBERINFO = "/api/member/info";
    //更新用户信息
    public static final String MEMBERUPDATE = "/api/member/update";
    //修改手机号
    public static final String MEMBERMOBILE = "/api/member/mobile";
    //忘记密码
    public static final String PASSWORDFORGET = "/api/password/forget";
    //关注-我的-粉丝列表
    public static final String FANSLIST = "/api/memberfollow/fans_list";
    //首页-热门文章-列表
    public static final String INDEX = "/api/article/index_article";
    //文章-详情(与帖子通用)
    public static final String ARTICLEREAD = "/api/article/read";
    //资讯-分类列表 快讯-分类列表
    public static final String REALINFOCATEGORY = "/api/realinfocategory";
    //kol分类
    public static final String KOLCATEGORY = "/api/realinfocategory/kolcategory";
    //文章分类
    public static final String ARTICLECATEGORY = "/api/articlecategory";
    //资讯-列表
    public static final String REALINFO = "/api/realinfo";
    //kol列表
    public static final String KOLLIST = "/api/realinfo/kollist";
    //钱包地址
    public static final String APIWALLETADDRESS = "/api/walletaddress";
    //删除钱包地址
    public static final String APIWALLETADDRESSDEL = "/api/walletaddress/";
    //修改钱包地址
    public static final String APIWALLETADDRESSUPDATE = "/api/walletaddress/update/";
    //圈子-列表
    public static final String APIARTICLE = "/api/article";
    //用户发布观点
    public static final String RELEASESHORT = "/api/article/release_short";
    //企业认证-详情
    public static final String AUTHENTERPRISEDETAIL = "/api/authenterprise/detail";
    //我的活动
    public static final String ACTIVITYLIST = "/api/activity/activitylist";
    //关注-我的-关注列表
    public static final String FOLLOWLIST = "/api/memberfollow/follow_list";
    //收藏-列表
    public static final String MEMBERCOLLECTIONLIST = "/api/membercollection/list";
    //列表
    public static final String APIGOODS = "/api/goods";
    //获取详情
    public static final String APIGOODSGOODSID = "/api/goods/";
    //兑换记录
    public static final String APIORDER = "/api/order";
    //兑换记录详情
    public static final String APIORDERORDERID = "/api/order/";
    //下单
    public static final String APIORDERPUT = "/api/order";
    //我的碎片/藏宝图列表
    public static final String USERMAP = "/api/usermap";
    //配置
    public static final String CINFIG = "/api/config";
    //获取用户团队数据
    public static final String MEMBERGROUP = "/api/member/group";
    //获取用户名片
    public static final String MEMBERMEMBERINFO = "/api/member/memberinfo";
    //藏宝图兑换-详情
    public static final String USERMAPDETAIL = "/api/usermap/newdetail";
    //执行藏宝图兑换
    public static final String EXCHANGE = "/api/usermap/newexchange";
    //财务列表
    public static final String FINANCE = "/api/finance";
    //搜索历史
    public static final String SEARCHLIST = "/api/searchlist";
    //删除搜索历史
    public static final String SEARCHDEL = "/api/searchlist/searchdel";
    //消息通知列表
    public static final String NOTIFYINDEX = "/api/notify/index";
    //我的-用户文章or观点(添加type=all类型)与他人主页的文章列表通用
    public static final String MEMBERARTICLE = "/api/article/member_article";
    //我的-用户文章or观点删除
    public static final String MEMBERARTICLEDEL = "/api/article/member_article_del";
    //兑换记录
    public static final String USERMAPLOG = "/api/usermap/usermaplog";
    //系统更新
    public static final String GETUPGRADE = "/api/get_upgrade";
    //分享奖励
    public static final String MEMBERSHARE = "/api/member/share";
    //阅读时间奖励
    public static final String READREWARD = "/api/member/readreward";
    //公告列表
    public static final String NOTICE = "/api/notice";
    //我的碎片新
    public static final String FRAGMENTLIST = "/api/usermap/fragmentlist";
    //精选推荐-资讯-列表
    public static final String RECOMMENDLIST = "/api/realinfo/recommend_list";
    //精选推荐-文章-列表
    public static final String RECOMMENDARTICLE = "/api/article/recommend_article";
    //发送邮箱验证码(新增)
    public static final String SMSEMAIL = "/api/sms/email";
    //登录(短信邮箱验证码)(新)
    public static final String LOGINEMAIL = "/api/login/email";
    //藏宝图提现
    public static final String MAPWITH = "/api/usermap/mapWith";
}
