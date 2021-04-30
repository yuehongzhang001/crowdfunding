package com.atguigu.scw.project.vo.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;

import lombok.Data;
import lombok.ToString;



/**
 * This is a view object(vo) containing detail info to be used by project info page. 
 * URL: /project/projectInfo
 * @author User
 *
 */
@ToString
@Data
public class ProjectDetailVo implements Serializable{

	private Integer id;

    private String name;

    private String remark;

    private Long money;

    private Integer day;

    private String status;

//    private String deploydate;

    private Long supportmoney=0L;

    private Integer supporter=0;

    private Integer completion;

    private Integer memberid;

    private String createdate;

    private Integer follower=100;
    
    private String headerImage;
    private List<String> detailImageList = new ArrayList();
    private List<TReturn> returnList;
    private TProjectInitiator projectInitiator;
}
