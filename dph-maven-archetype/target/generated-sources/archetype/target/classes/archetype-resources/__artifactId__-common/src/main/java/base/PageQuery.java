#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.base;

import com.dph.common.utils.service.Base;

/**
 * description: 基础分页信息类，此类定义分页需要公共字段
 * saras_xu@163.com 2017-04-25 10:45 创建
 */
public class PageQuery extends Base {
    private static final long serialVersionUID = 4241150782965043000L;
    /**
     * 页码，从1开始
     */
    private Integer pageNum = 1;
    /**
     * 页面大小
     */
    private Integer pageSize = 10;
    /**
     * 查询开始时间
     */
    private String startTime;
    /**
     * 查询结束时间
     */
    private String endTime;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
}
