package com.panda.cdc.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 搜索-索引管理表
 * </p>
 *
 * @author system
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MSearchIndex对象", description="搜索-索引管理表")
public class MSearchIndex implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "索引编码")
    @TableField("index_code")
    private String indexCode;

    @ApiModelProperty(value = "索引名称")
    @TableField("index_name")
    private String indexName;

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    @TableField("sd_scrtp")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    @TableField("id_scrvar")
    private String idScrvar;

    @ApiModelProperty(value = "注册上传机构（公司名）")
    @TableField("id_scrorg")
    private String idScrorg;

    @ApiModelProperty(value = "启用禁用（是否失效）0启用，1禁用")
    @TableField("fg_dis")
    private Integer fgDis;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    @TableField("fg_del")
    private Integer fgDel;

    @ApiModelProperty(value = "创建用户")
    @TableField("id_usr_crt")
    private Long idUsrCrt;

    @ApiModelProperty(value = "创建时间")
    @TableField("dtm_crt")
    private LocalDateTime dtmCrt;

    @ApiModelProperty(value = "最后更新用户")
    @TableField("id_usr_edt")
    private Long idUsrEdt;

    @ApiModelProperty(value = "最后更新时间")
    @TableField("dtm_edt")
    private LocalDateTime dtmEdt;

    @ApiModelProperty(value = "记录版本号")
    @TableField("ver_no")
    private String verNo;


}
