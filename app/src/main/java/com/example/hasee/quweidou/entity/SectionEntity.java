package com.example.hasee.quweidou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ken on 2017/1/7.17:08
 */

public class SectionEntity implements Serializable {


    /**
     * id : 1
     * type : feedSection
     * header : null
     * itemList : [{"type":"video","data":{"dataType":"VideoBeanForClient","id":12366,"title":"特条丨「幽灵行动：荒野」真人宣传片","description":"这条韵味十足的「Tom Clancy\u2019s Ghost Recon: Wildlands」真人广告，抓住了白色的猫与「幽灵」的天然关系，令人意犹未尽。虐猫小心报复哟~From Ubisoft Official Channel 官方中英文頻道 //","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"游戏","author":null,"cover":{"feed":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","detail":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","blurred":"http://img.kaiyanapp.com/864ef0f6ab7119d1e047dd35db899783.jpeg?imageMogr2/quality/60","sharing":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=default","duration":92,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=12366","forWeibo":"http://wandou.im/3l4oy2"},"releaseTime":1483754368000,"playInfo":[{"height":270,"width":480,"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=low"},{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=normal"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=high"}],"consumption":{"collectionCount":554,"shareCount":644,"replyCount":23},"campaign":null,"waterMarks":null,"adTrack":null,"tags":[{"id":16,"name":"广告","actionUrl":"eyepetizer://tag/16/?title=%E5%B9%BF%E5%91%8A","adTrack":null},{"id":30,"name":"游戏","actionUrl":"eyepetizer://tag/30/?title=%E6%B8%B8%E6%88%8F","adTrack":null},{"id":146,"name":"666","actionUrl":"eyepetizer://tag/146/?title=666","adTrack":null},{"id":538,"name":"喵星人","actionUrl":"eyepetizer://tag/538/?title=%E5%96%B5%E6%98%9F%E4%BA%BA","adTrack":null},{"id":204,"name":"精致","actionUrl":"eyepetizer://tag/204/?title=%E7%B2%BE%E8%87%B4","adTrack":null},{"id":2,"name":"创意","actionUrl":"eyepetizer://tag/2/?title=%E5%88%9B%E6%84%8F","adTrack":null}],"type":"NORMAL","idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1483750800000,"promotion":null,"label":null,"collected":false,"played":false}}]
     * footer : {}
     * count : 7
     * adTrack : null
     */

    private int id;
    private String type;
    private Object header;
    private FooterBean footer;
    private int count;
    private Object adTrack;
    /**
     * type : video
     * data : {"dataType":"VideoBeanForClient","id":12366,"title":"特条丨「幽灵行动：荒野」真人宣传片","description":"这条韵味十足的「Tom Clancy\u2019s Ghost Recon: Wildlands」真人广告，抓住了白色的猫与「幽灵」的天然关系，令人意犹未尽。虐猫小心报复哟~From Ubisoft Official Channel 官方中英文頻道 //","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"游戏","author":null,"cover":{"feed":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","detail":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","blurred":"http://img.kaiyanapp.com/864ef0f6ab7119d1e047dd35db899783.jpeg?imageMogr2/quality/60","sharing":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=default","duration":92,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=12366","forWeibo":"http://wandou.im/3l4oy2"},"releaseTime":1483754368000,"playInfo":[{"height":270,"width":480,"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=low"},{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=normal"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=high"}],"consumption":{"collectionCount":554,"shareCount":644,"replyCount":23},"campaign":null,"waterMarks":null,"adTrack":null,"tags":[{"id":16,"name":"广告","actionUrl":"eyepetizer://tag/16/?title=%E5%B9%BF%E5%91%8A","adTrack":null},{"id":30,"name":"游戏","actionUrl":"eyepetizer://tag/30/?title=%E6%B8%B8%E6%88%8F","adTrack":null},{"id":146,"name":"666","actionUrl":"eyepetizer://tag/146/?title=666","adTrack":null},{"id":538,"name":"喵星人","actionUrl":"eyepetizer://tag/538/?title=%E5%96%B5%E6%98%9F%E4%BA%BA","adTrack":null},{"id":204,"name":"精致","actionUrl":"eyepetizer://tag/204/?title=%E7%B2%BE%E8%87%B4","adTrack":null},{"id":2,"name":"创意","actionUrl":"eyepetizer://tag/2/?title=%E5%88%9B%E6%84%8F","adTrack":null}],"type":"NORMAL","idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1483750800000,"promotion":null,"label":null,"collected":false,"played":false}
     */

    private List<ItemListBean> itemList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public FooterBean getFooter() {
        return footer;
    }

    public void setFooter(FooterBean footer) {
        this.footer = footer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getAdTrack() {
        return adTrack;
    }

    public void setAdTrack(Object adTrack) {
        this.adTrack = adTrack;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class FooterBean {
    }

    public static class ItemListBean implements Serializable {
        private String type;
        /**
         * dataType : VideoBeanForClient
         * id : 12366
         * title : 特条丨「幽灵行动：荒野」真人宣传片
         * description : 这条韵味十足的「Tom Clancy’s Ghost Recon: Wildlands」真人广告，抓住了白色的猫与「幽灵」的天然关系，令人意犹未尽。虐猫小心报复哟~From Ubisoft Official Channel 官方中英文頻道 //
         * provider : {"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"}
         * category : 游戏
         * author : null
         * cover : {"feed":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","detail":"http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60","blurred":"http://img.kaiyanapp.com/864ef0f6ab7119d1e047dd35db899783.jpeg?imageMogr2/quality/60","sharing":null}
         * playUrl : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=default
         * duration : 92
         * webUrl : {"raw":"http://www.eyepetizer.net/detail.html?vid=12366","forWeibo":"http://wandou.im/3l4oy2"}
         * releaseTime : 1483754368000
         * playInfo : [{"height":270,"width":480,"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=low"},{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=normal"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=high"}]
         * consumption : {"collectionCount":554,"shareCount":644,"replyCount":23}
         * campaign : null
         * waterMarks : null
         * adTrack : null
         * tags : [{"id":16,"name":"广告","actionUrl":"eyepetizer://tag/16/?title=%E5%B9%BF%E5%91%8A","adTrack":null},{"id":30,"name":"游戏","actionUrl":"eyepetizer://tag/30/?title=%E6%B8%B8%E6%88%8F","adTrack":null},{"id":146,"name":"666","actionUrl":"eyepetizer://tag/146/?title=666","adTrack":null},{"id":538,"name":"喵星人","actionUrl":"eyepetizer://tag/538/?title=%E5%96%B5%E6%98%9F%E4%BA%BA","adTrack":null},{"id":204,"name":"精致","actionUrl":"eyepetizer://tag/204/?title=%E7%B2%BE%E8%87%B4","adTrack":null},{"id":2,"name":"创意","actionUrl":"eyepetizer://tag/2/?title=%E5%88%9B%E6%84%8F","adTrack":null}]
         * type : NORMAL
         * idx : 0
         * shareAdTrack : null
         * favoriteAdTrack : null
         * webAdTrack : null
         * date : 1483750800000
         * promotion : null
         * label : null
         * collected : false
         * played : false
         */

        private DataBean data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getSubTitle(){
            //处理时间
            int time = this.getData().getDuration();
            String date = (time / 60) + "'" + (time % 60) + "''";


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("#");
            stringBuilder.append(this.getData().getCategory());
            stringBuilder.append(" / ");
            stringBuilder.append(date);
            return stringBuilder.toString();
        }

        public static class DataBean implements Serializable {
            private String dataType;
            private int id;
            private String title;
            private String description;
            /**
             * name : YouTube
             * alias : youtube
             * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
             */

            private ProviderBean provider;
            private String category;
            private Object author;
            /**
             * feed : http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60
             * detail : http://img.kaiyanapp.com/8919ae3771617cdfd783ff12d32bb752.jpeg?imageMogr2/quality/60
             * blurred : http://img.kaiyanapp.com/864ef0f6ab7119d1e047dd35db899783.jpeg?imageMogr2/quality/60
             * sharing : null
             */

            private CoverBean cover;
            private String playUrl;
            private int duration;
            /**
             * raw : http://www.eyepetizer.net/detail.html?vid=12366
             * forWeibo : http://wandou.im/3l4oy2
             */

            private WebUrlBean webUrl;
            private long releaseTime;
            /**
             * collectionCount : 554
             * shareCount : 644
             * replyCount : 23
             */

            private ConsumptionBean consumption;
            private Object campaign;
            private Object waterMarks;
            private Object adTrack;
            private String type;
            private int idx;
            private Object shareAdTrack;
            private Object favoriteAdTrack;
            private Object webAdTrack;
            private long date;
            private Object promotion;
            private Object label;
            private boolean collected;
            private boolean played;
            /**
             * height : 270
             * width : 480
             * name : 流畅
             * type : low
             * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=12366&editionType=low
             */

            private List<PlayInfoBean> playInfo;
            /**
             * id : 16
             * name : 广告
             * actionUrl : eyepetizer://tag/16/?title=%E5%B9%BF%E5%91%8A
             * adTrack : null
             */

            private List<TagsBean> tags;

            private List<ItemListBean> itemList;

            private Header header;

            public Header getHeader() {
                return header;
            }

            public void setHeader(Header header) {
                this.header = header;
            }

            public List<ItemListBean> getItemList() {
                return itemList;
            }

            public void setItemList(List<ItemListBean> itemList) {
                this.itemList = itemList;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public ProviderBean getProvider() {
                return provider;
            }

            public void setProvider(ProviderBean provider) {
                this.provider = provider;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public Object getAuthor() {
                return author;
            }

            public void setAuthor(Object author) {
                this.author = author;
            }

            public CoverBean getCover() {
                return cover;
            }

            public void setCover(CoverBean cover) {
                this.cover = cover;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public WebUrlBean getWebUrl() {
                return webUrl;
            }

            public void setWebUrl(WebUrlBean webUrl) {
                this.webUrl = webUrl;
            }

            public long getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(long releaseTime) {
                this.releaseTime = releaseTime;
            }

            public ConsumptionBean getConsumption() {
                return consumption;
            }

            public void setConsumption(ConsumptionBean consumption) {
                this.consumption = consumption;
            }

            public Object getCampaign() {
                return campaign;
            }

            public void setCampaign(Object campaign) {
                this.campaign = campaign;
            }

            public Object getWaterMarks() {
                return waterMarks;
            }

            public void setWaterMarks(Object waterMarks) {
                this.waterMarks = waterMarks;
            }

            public Object getAdTrack() {
                return adTrack;
            }

            public void setAdTrack(Object adTrack) {
                this.adTrack = adTrack;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIdx() {
                return idx;
            }

            public void setIdx(int idx) {
                this.idx = idx;
            }

            public Object getShareAdTrack() {
                return shareAdTrack;
            }

            public void setShareAdTrack(Object shareAdTrack) {
                this.shareAdTrack = shareAdTrack;
            }

            public Object getFavoriteAdTrack() {
                return favoriteAdTrack;
            }

            public void setFavoriteAdTrack(Object favoriteAdTrack) {
                this.favoriteAdTrack = favoriteAdTrack;
            }

            public Object getWebAdTrack() {
                return webAdTrack;
            }

            public void setWebAdTrack(Object webAdTrack) {
                this.webAdTrack = webAdTrack;
            }

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public Object getPromotion() {
                return promotion;
            }

            public void setPromotion(Object promotion) {
                this.promotion = promotion;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public boolean isCollected() {
                return collected;
            }

            public void setCollected(boolean collected) {
                this.collected = collected;
            }

            public boolean isPlayed() {
                return played;
            }

            public void setPlayed(boolean played) {
                this.played = played;
            }

            public List<PlayInfoBean> getPlayInfo() {
                return playInfo;
            }

            public void setPlayInfo(List<PlayInfoBean> playInfo) {
                this.playInfo = playInfo;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class Header{
                private String cover;
                private String title;
                private String subTitle;

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }
            }

            public static class ProviderBean implements Serializable {
                private String name;
                private String alias;
                private String icon;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }

            public static class CoverBean implements Serializable {
                private String feed;
                private String detail;
                private String blurred;
                private Object sharing;

                public String getFeed() {
                    return feed;
                }

                public void setFeed(String feed) {
                    this.feed = feed;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getBlurred() {
                    return blurred;
                }

                public void setBlurred(String blurred) {
                    this.blurred = blurred;
                }

                public Object getSharing() {
                    return sharing;
                }

                public void setSharing(Object sharing) {
                    this.sharing = sharing;
                }
            }

            public static class WebUrlBean implements Serializable {
                private String raw;
                private String forWeibo;

                public String getRaw() {
                    return raw;
                }

                public void setRaw(String raw) {
                    this.raw = raw;
                }

                public String getForWeibo() {
                    return forWeibo;
                }

                public void setForWeibo(String forWeibo) {
                    this.forWeibo = forWeibo;
                }
            }

            public static class ConsumptionBean implements Serializable {
                private int collectionCount;
                private int shareCount;
                private int replyCount;

                public int getCollectionCount() {
                    return collectionCount;
                }

                public void setCollectionCount(int collectionCount) {
                    this.collectionCount = collectionCount;
                }

                public int getShareCount() {
                    return shareCount;
                }

                public void setShareCount(int shareCount) {
                    this.shareCount = shareCount;
                }

                public int getReplyCount() {
                    return replyCount;
                }

                public void setReplyCount(int replyCount) {
                    this.replyCount = replyCount;
                }
            }

            public static class PlayInfoBean implements Serializable {
                private int height;
                private int width;
                private String name;
                private String type;
                private String url;

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class TagsBean implements Serializable {
                private int id;
                private String name;
                private String actionUrl;
                private Object adTrack;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getActionUrl() {
                    return actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }
            }
        }
    }
}
