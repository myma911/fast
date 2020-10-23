package cn.aaron911.log.core.dto;


public class TraceLogMessage extends BaseLogMessage {


    /**
     * 执行耗时（毫秒）
     */
    private Long time;

    /**
     * 执行的位置 开始 or 结束
     */
    private String position;

    /**
     * 执行位置数
     */
    private Integer positionNum;

    public Integer getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(Integer positionNum) {
        this.positionNum = positionNum;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
