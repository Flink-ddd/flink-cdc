package com.panda.cdc.dto.req.monitor;


import com.panda.cdc.dto.CommonReqDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("自定义topic的请求参数")
public class MonitorTopicReqDTO extends CommonReqDTO {
    @ApiModelProperty(value = "topicID")
    private String topicId;

    @ApiModelProperty(value = "业务组ID")
    private String businessGroupId;

    @ApiModelProperty(value = "监控的数据库")
    private String monitorDb;

    @ApiModelProperty(value = "监控的数据表")
    private String monitorTable;

    @ApiModelProperty(value = "topic来源 1：rabbitMQ、2：rocketMQ、3：kafka")
    private Integer topicSource;

    @ApiModelProperty(value = "自定义topic名称")
    private String topicName;
}
