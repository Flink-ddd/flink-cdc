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
 * 搜索-数据源管理表
 * </p>
 *
 * @author system
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MSearchDatabase对象", description="搜索-数据源管理表")
public class MSearchDatabase implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "数据源")
    @TableField("db_url")
    private String dbUrl;

    @ApiModelProperty(value = "主机名称")
    @TableField("db_host")
    private String dbHost;

    @ApiModelProperty(value = "端口")
    @TableField("db_port")
    private Integer dbPort;

    @ApiModelProperty(value = "用户名")
    @TableField("db_username")
    private String dbUsername;

    @ApiModelProperty(value = "密码")
    @TableField("db_password")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名称")
    @TableField("db_name")
    private String dbName;

    @ApiModelProperty(value = "数据库类型（1：MySQL、2：oracle、3：Hbase、4：SqlServer、5：PostgreSql）")
    @TableField("db_type")
    private Integer dbType;

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
