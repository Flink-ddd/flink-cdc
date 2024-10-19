package com.panda.cdc.dto.resp.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("数据任务的返回参数")
public class DataTaskRespDTO {
    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "topic名称")
    private String topicName;

    @ApiModelProperty(value = "数据状态（1：进行中、2：中断、3：继续）")
    private Integer taskStatus;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    private Integer fgDel;

    @ApiModelProperty(value = "创建用户")
    private Long idUsrCrt;

    @ApiModelProperty(value = "创建时间")
    private Date dtmCrt;

    @ApiModelProperty(value = "最后更新用户")
    private Long idUsrEdt;

    @ApiModelProperty(value = "最后更新时间")
    private Date dtmEdt;

    @ApiModelProperty(value = "记录版本号")
    private String verNo;

}
