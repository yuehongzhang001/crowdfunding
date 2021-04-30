package com.atguigu.scw.webui.vo.resp;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * This is a view object(vo) containing info to be used by index page of the website. url: /index
 * @author User
 *
 */
@ToString
@Data
public class ProjectVo implements Serializable{

	private Integer id;
	private String name;
	private String remark;
	private Long money;
	private String headerImageUrl;
	
}
