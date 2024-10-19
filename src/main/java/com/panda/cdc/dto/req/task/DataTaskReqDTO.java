package com.panda.cdc.dto.req.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("操作中断或继续同步任务的请求参数")
public class DataTaskReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "自定义topic名称")
    private String topicName;

    @ApiModelProperty(value = "操作状态（1：中断、2：继续）")
    private Integer operateStatus;


}
