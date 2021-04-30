package com.atguigu.scw.project.vo.resp;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProjectVo implements Serializable{

	private Integer id;
	private String name;
	private String remark;
	private Long money;
	private String headerImageUrl;
	
}
