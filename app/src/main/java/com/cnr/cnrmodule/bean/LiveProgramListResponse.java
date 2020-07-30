package com.cnr.cnrmodule.bean;

import com.cnr.httpz.BaseResponse;

import java.util.List;

public class LiveProgramListResponse extends BaseResponse {


    /**
     * status : {"code":"200","msgToShow":"成功","msgForDebug":""}
     * directProgramList : [{"id":"3","ChannelName":"CNR中华之声","imgUrl":"http://img.tv.cnrmobile.com/channelfm/b63/c84/56a7036946156.png","playType":0,"chargeUnit":{"needCharge":false,"chargeId":123,"leftFreeSeconds":100,"is_vip":1},"programInfoList":{"tvId":"58840","programName":"捷运2015","startTime":"160000","endTime":"180000","showStatus":0}},{"id":"4","ChannelName":"CNR中文环球","imgUrl":"http://img.tv.cnrmobile.com/channelfm/b3e/6f9/56a70d1b4e208.png","playType":0,"chargeUnit":{"needCharge":false,"chargeId":123,"leftFreeSeconds":100,"is_vip":1},"programInfoList":{"tvId":"60663","programName":"直播中国（晚间版）","startTime":"170000","endTime":"180000","showStatus":0}}]
     */

    private List<DirectProgramListBean> directProgramList;

    public List<DirectProgramListBean> getDirectProgramList() {
        return directProgramList;
    }

    public void setDirectProgramList(List<DirectProgramListBean> directProgramList) {
        this.directProgramList = directProgramList;
    }

    public static class DirectProgramListBean {
        public String getPlayId() {
            return playId;
        }

        public void setPlayId(String playId) {
            this.playId = playId;
        }

        /**
         * id : 3
         * ChannelName : CNR中华之声
         * imgUrl : http://img.tv.cnrmobile.com/channelfm/b63/c84/56a7036946156.png
         * playType : 0
         * chargeUnit : {"needCharge":false,"chargeId":123,"leftFreeSeconds":100,"is_vip":1}
         * programInfoList : {"tvId":"58840","programName":"捷运2015","startTime":"160000","endTime":"180000","showStatus":0}
         */

        private String playId;
        private String ChannelName;
        private String imgUrl;
        private int playType;
        private ChargeUnitBean chargeUnit;
        private ProgramInfoListBean programInfoList;


        public String getChannelName() {
            return ChannelName;
        }

        public void setChannelName(String ChannelName) {
            this.ChannelName = ChannelName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
        }

        public ChargeUnitBean getChargeUnit() {
            return chargeUnit;
        }

        public void setChargeUnit(ChargeUnitBean chargeUnit) {
            this.chargeUnit = chargeUnit;
        }

        public ProgramInfoListBean getProgramInfoList() {
            return programInfoList;
        }

        public void setProgramInfoList(ProgramInfoListBean programInfoList) {
            this.programInfoList = programInfoList;
        }

        public static class ChargeUnitBean {
            /**
             * needCharge : false
             * chargeId : 123
             * leftFreeSeconds : 100
             * is_vip : 1
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

        public static class ProgramInfoListBean {
            /**
             * tvId : 58840
             * programName : 捷运2015
             * startTime : 160000
             * endTime : 180000
             * showStatus : 0
             */

            private String tvId;
            private String programName;
            private String startTime;
            private String endTime;
            private int showStatus;

            public String getTvId() {
                return tvId;
            }

            public void setTvId(String tvId) {
                this.tvId = tvId;
            }

            public String getProgramName() {
                return programName;
            }

            public void setProgramName(String programName) {
                this.programName = programName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(int showStatus) {
                this.showStatus = showStatus;
            }
        }
    }
}
