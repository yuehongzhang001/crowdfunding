package com.atguigu.scw.project.vo.req;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;

import lombok.Data;

@Data
public class ProjectRedisStorageVo extends BaseVo{

	private String projectToken;//project tmp token
	
	private List<Integer> typeids = new ArrayList();//project type ids
	
	private List<Integer> tagids = new ArrayList();//project tag ids
	
	private String name;
	private String remark;
	private Long money;
	private Integer day;
	
	private String headerImage;
	private List<String> detailImages = new ArrayList();
	
	//TODO: initiator info: introduction, phone, customer service phone
	/* private TProjectInitiator initiator; */
	
	private List<TReturn> retunList = new ArrayList<TReturn>();
	
	//TODO: confirm info: 收款账号和法人
		/*  */
	
	
}
