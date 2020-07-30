package com.cnr.cnrmodule.bean;

import com.cnr.httpz.BaseResponse;

import java.util.List;

public class DemoEntity extends BaseResponse {

    /**
     * code : 1
     * message : success
     * notice : {"softwareCount":0,"newsCount":23}
     * result : {"items":[{"detail":"","href":"https://www.oschina.net/news/117358/cassandra-4-0-beta-released","id":117358,"img":"https://static.oschina.net/uploads/img/202007/27103450_LBnk.png","name":"Apache Cassandra 4.0 beta 发布，史上最稳定版本","pubDate":"2020-07-27 17:56:52","type":6,"weight":10},{"detail":"","href":"https://www.oschina.net/news/117284/procmon-for-linux","id":117284,"img":"https://static.oschina.net/uploads/space/2020/0718/082706_O8XS_4105562.jpg","name":"经典进程监控工具 Procmon 推出 Linux 版本","pubDate":"2020-07-27 17:57:39","type":6,"weight":9},{"detail":"","href":"https://my.oschina.net/editorial-story/blog/4427285","id":4427285,"img":"https://static.oschina.net/uploads/img/202007/27103732_wHR2.png","name":"又是不懂开源协议惹的祸，唯品会 Saturn 未声明上游项目版权被拒","pubDate":"2020-07-27 17:58:14","type":3,"weight":8},{"detail":"","href":"https://gitee.com/Selected-Activities/Adapted-game?utm_source=appbanner","id":110941,"img":"https://static.oschina.net/uploads/cooperation/event/guangzhou/event/2201438_97f5fa3b-5a35-4e86-a94a-98f22dd9c64e.jpeg","name":"速来围观 | Python 贪吃蛇魔改大赛来袭！","pubDate":"2020-07-29 10:22:13","type":0,"weight":4}],"nextPageToken":"A4398F94EDF60667","prevPageToken":"B7DC67FBB70C320E","requestCount":4,"responseCount":4,"totalResults":4}
     * time : 2020-07-29 17:36:03
     */

    private int code;
    private String message;
    private NoticeBean notice;
    private ResultBean result;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class NoticeBean {
        /**
         * softwareCount : 0
         * newsCount : 23
         */

        private int softwareCount;
        private int newsCount;

        public int getSoftwareCount() {
            return softwareCount;
        }

        public void setSoftwareCount(int softwareCount) {
            this.softwareCount = softwareCount;
        }

        public int getNewsCount() {
            return newsCount;
        }

        public void setNewsCount(int newsCount) {
            this.newsCount = newsCount;
        }
    }

    public static class ResultBean {
        /**
         * items : [{"detail":"","href":"https://www.oschina.net/news/117358/cassandra-4-0-beta-released","id":117358,"img":"https://static.oschina.net/uploads/img/202007/27103450_LBnk.png","name":"Apache Cassandra 4.0 beta 发布，史上最稳定版本","pubDate":"2020-07-27 17:56:52","type":6,"weight":10},{"detail":"","href":"https://www.oschina.net/news/117284/procmon-for-linux","id":117284,"img":"https://static.oschina.net/uploads/space/2020/0718/082706_O8XS_4105562.jpg","name":"经典进程监控工具 Procmon 推出 Linux 版本","pubDate":"2020-07-27 17:57:39","type":6,"weight":9},{"detail":"","href":"https://my.oschina.net/editorial-story/blog/4427285","id":4427285,"img":"https://static.oschina.net/uploads/img/202007/27103732_wHR2.png","name":"又是不懂开源协议惹的祸，唯品会 Saturn 未声明上游项目版权被拒","pubDate":"2020-07-27 17:58:14","type":3,"weight":8},{"detail":"","href":"https://gitee.com/Selected-Activities/Adapted-game?utm_source=appbanner","id":110941,"img":"https://static.oschina.net/uploads/cooperation/event/guangzhou/event/2201438_97f5fa3b-5a35-4e86-a94a-98f22dd9c64e.jpeg","name":"速来围观 | Python 贪吃蛇魔改大赛来袭！","pubDate":"2020-07-29 10:22:13","type":0,"weight":4}]
         * nextPageToken : A4398F94EDF60667
         * prevPageToken : B7DC67FBB70C320E
         * requestCount : 4
         * responseCount : 4
         * totalResults : 4
         */

        private String nextPageToken;
        private String prevPageToken;
        private int requestCount;
        private int responseCount;
        private int totalResults;
        private List<ItemsBean> items;

        public String getNextPageToken() {
            return nextPageToken;
        }

        public void setNextPageToken(String nextPageToken) {
            this.nextPageToken = nextPageToken;
        }

        public String getPrevPageToken() {
            return prevPageToken;
        }

        public void setPrevPageToken(String prevPageToken) {
            this.prevPageToken = prevPageToken;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }

        public int getResponseCount() {
            return responseCount;
        }

        public void setResponseCount(int responseCount) {
            this.responseCount = responseCount;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * detail :
             * href : https://www.oschina.net/news/117358/cassandra-4-0-beta-released
             * id : 117358
             * img : https://static.oschina.net/uploads/img/202007/27103450_LBnk.png
             * name : Apache Cassandra 4.0 beta 发布，史上最稳定版本
             * pubDate : 2020-07-27 17:56:52
             * type : 6
             * weight : 10
             */

            private String detail;
            private String href;
            private int id;
            private String img;
            private String name;
            private String pubDate;
            private int type;
            private int weight;

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPubDate() {
                return pubDate;
            }

            public void setPubDate(String pubDate) {
                this.pubDate = pubDate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }
}
