package com.cnr.cnrmodule.bean;

import androidx.databinding.ObservableList;

import com.cnr.httpz.BaseResponse;

import java.util.List;

public class MianBeanResponse extends BaseResponse {


    private List<RecommendInfoBean> recommendInfo;

    public List<RecommendInfoBean> getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(List<RecommendInfoBean> recommendInfo) {
        this.recommendInfo = recommendInfo;
    }



    public static class RecommendInfoBean {
        /**
         * programList : [{"playId":"20190909110457714","playType":2,"imgUrl":null,"title":"战狼2","rslogan":"战狼2","score":"","rurl":"","is_icon":"","chargeUnit":{"needCharge":false,"chargeId":0,"leftFreeSeconds":0,"is_vip":0}},{"playId":"20190909110654648","playType":2,"imgUrl":null,"title":"流浪地球","rslogan":"流浪地球","score":"","rurl":"","is_icon":"","chargeUnit":{"needCharge":false,"chargeId":0,"leftFreeSeconds":0,"is_vip":0}},{"playId":"20190909110914865","playType":2,"imgUrl":null,"title":"哪吒之魔童降世","rslogan":"哪吒之魔童降世","score":"","rurl":"","is_icon":"","chargeUnit":{"needCharge":false,"chargeId":0,"leftFreeSeconds":0,"is_vip":0}},{"playId":"20190909111034528","playType":2,"imgUrl":null,"title":"超时空同居","rslogan":"超时空同居","score":"","rurl":"","is_icon":"","chargeUnit":{"needCharge":false,"chargeId":0,"leftFreeSeconds":0,"is_vip":0}}]
         * programListName : 国产
         * hasMore : false
         * dType : 0
         * showType :
         * mould_type : 0
         * showId :
         * showDisplay : 1
         * adList : []
         */

        private String programListName;
        private boolean hasMore;
        private int dType;
        private int showType;
        private int tv_type;
        private int mould_type;
        private String showId;

        private int showDisplay;
        private String programListCode;
        private int showChannge;
        private int programTotal;
        private int showChanngePage;
        private int programListNum;
        private List<ProgramListBean> programList;
        private List<AdListBean> adList;

        public int getProgramListNum() {
            return programListNum;
        }

        public void setProgramListNum(int programListNum) {
            this.programListNum = programListNum;
        }

        public int getShowChanngePage() {
            return showChanngePage;
        }

        public void setShowChanngePage(int showChanngePage) {
            this.showChanngePage = showChanngePage;
        }

        public int getProgramTotal() {
            return programTotal;
        }

        public void setProgramListCode(String programListCode) {
            this.programListCode = programListCode;
        }

        public int getShowChannge() {
            return showChannge;
        }

        public void setProgramTotal(int programTotal) {
            this.programTotal = programTotal;
        }

        public String getProgramListCode() {
            return programListCode;
        }

        public void setdType(int dType) {
            this.dType = dType;
        }

        public void setShowChannge(int showChannge) {
            this.showChannge = showChannge;
        }

        public int getTv_type() {
            return tv_type;
        }

        public void setTv_type(int tv_type) {
            this.tv_type = tv_type;
        }

        public String getProgramListName() {
            return programListName;
        }

        public void setProgramListName(String programListName) {
            this.programListName = programListName;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public int getDType() {
            return dType;
        }

        public void setDType(int dType) {
            this.dType = dType;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getMould_type() {
            return mould_type;
        }

        public void setMould_type(int mould_type) {
            this.mould_type = mould_type;
        }

        public String getShowId() {
            return showId;
        }

        public void setShowId(String showId) {
            this.showId = showId;
        }

        public int getShowDisplay() {
            return showDisplay;
        }

        public void setShowDisplay(int showDisplay) {
            this.showDisplay = showDisplay;
        }

        public List<ProgramListBean> getProgramList() {
            return programList;
        }

        public void setProgramList(List<ProgramListBean> programList) {
            this.programList = programList;
        }

        public List<AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<AdListBean> adList) {
            this.adList = adList;
        }

        public static class ProgramListBean {
            /**
             * playId : 20190909110457714
             * playType : 2
             * imgUrl : null
             * title : 战狼2
             * rslogan : 战狼2
             * score :
             * rurl :
             * is_icon :
             * chargeUnit : {"needCharge":false,"chargeId":0,"leftFreeSeconds":0,"is_vip":0}
             */

            private String playId;
            private int playType;
            private String imgUrl;
            private String title;
            private String rslogan;
            private String score;
            private String rurl;
            private int is_icon;
            private String programName;
            private String sTime;
            private String eTime;

            public String getsTime() {
                return sTime;
            }

            public String getProgramName() {
                return programName;
            }

            public String geteTime() {
                return eTime;
            }

            public void setsTime(String sTime) {
                this.sTime = sTime;
            }

            public void setProgramName(String programName) {
                this.programName = programName;
            }

            public void seteTime(String eTime) {
                this.eTime = eTime;
            }

            private ChargeUnitBean chargeUnit;

            public String getPlayId() {
                return playId;
            }

            public void setPlayId(String playId) {
                this.playId = playId;
            }

            public int getPlayType() {
                return playType;
            }

            public void setPlayType(int playType) {
                this.playType = playType;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRslogan() {
                return rslogan;
            }

            public void setRslogan(String rslogan) {
                this.rslogan = rslogan;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getRurl() {
                return rurl;
            }

            public void setRurl(String rurl) {
                this.rurl = rurl;
            }

            public int getIs_icon() {
                return is_icon;
            }

            public void setIs_icon(int is_icon) {
                this.is_icon = is_icon;
            }

            public ChargeUnitBean getChargeUnit() {
                return chargeUnit;
            }

            public void setChargeUnit(ChargeUnitBean chargeUnit) {
                this.chargeUnit = chargeUnit;
            }

            public static class ChargeUnitBean {
                /**
                 * needCharge : false
                 * chargeId : 0
                 * leftFreeSeconds : 0
                 * is_vip : 0
                 */

                private boolean needCharge;
                private int chargeId;
                private int leftFreeSeconds;
                private int is_vip;

                public boolean isNeedCharge() {
                    return needCharge;
                }

                public void setNeedCharge(boolean needCharge) {
                    this.needCharge = needCharge;
                }

                public int getChargeId() {
                    return chargeId;
                }

                public void setChargeId(int chargeId) {
                    this.chargeId = chargeId;
                }

                public int getLeftFreeSeconds() {
                    return leftFreeSeconds;
                }

                public void setLeftFreeSeconds(int leftFreeSeconds) {
                    this.leftFreeSeconds = leftFreeSeconds;
                }

                public int getIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(int is_vip) {
                    this.is_vip = is_vip;
                }
            }
        }
        public static class AdListBean {
            /**
             * title : 11111
             * imgUrl :
             * LinkUrl : http://api.cnrhdtv.test.cnrmobile.com/program/linkurl/NSQkRjcxRTJENUM2NzNFNzU3Q0VENkFEQkU5RDg5MEQ3Q0UkQ05STDAxMDAwMSQwJDAkMQ==/502996d40cdb52878d4cb1d2369145c3
             */

            private String title;
            private String imgUrl;
            private String LinkUrl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLinkUrl() {
                return LinkUrl;
            }

            public void setLinkUrl(String LinkUrl) {
                this.LinkUrl = LinkUrl;
            }
        }
    }
}
