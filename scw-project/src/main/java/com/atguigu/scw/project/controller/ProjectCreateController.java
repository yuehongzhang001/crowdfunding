package com.atguigu.scw.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.atguigu.scw.enums.ProjectStatusEnume;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.component.OssTemplate;
import com.atguigu.scw.project.constant.ProjectConstant;
import com.atguigu.scw.project.service.TProjectService;
import com.atguigu.scw.project.vo.req.BaseVo;
import com.atguigu.scw.project.vo.req.ProjectBaseVo;
import com.atguigu.scw.project.vo.req.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.req.ProjectReturnVo;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "Project creating module")
@RequestMapping("/project/create")
@RestController
public class ProjectCreateController {

	@Autowired
	TProjectService projectService;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	OssTemplate ossTemplate;

	@ApiOperation(value = "1-init project")
	@PostMapping("/init")
	public AppResponse<Object> init(BaseVo vo) {
		try {
			//1. check login
			// 1.1 check if the token exists in the request.
			String accessToken = vo.getAccessToken();
			if (StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("The request must offer an accessToken");
				return fail;
			}

			// 1.2 check if the memberId represented by the accessToken exist.
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(memberId)) {// not exists, so the user not login yet.
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("You need to login before creating an project");
				return fail;
			}
			
			//2 init bigVo: copy the data in the request vo to a temp project value object(bigVo)
			
			ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
			BeanUtils.copyProperties(vo, bigVo);
			String projetToken = UUID.randomUUID().toString().replaceAll("-", "");// form a token for project
			bigVo.setProjectToken(projetToken);
			
			// 3. save this bigVo in redis for further data copy.
			String jsonStr = JSON.toJSONString(bigVo);// transform the projectRedisVo object to json string
			stringRedisTemplate.opsForValue().set(ProjectConstant.TEM_PROJECT_PREFIX + projetToken, jsonStr);// save the
																												// and
			log.debug("bigVo:{}", bigVo);
			return AppResponse.ok(bigVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("request dealing failed", e.getMessage());
			return AppResponse.fail(null);
		}
	}

	@ApiOperation(value = "2-project basic info")
	@PostMapping("/basicInfo")
	public AppResponse<Object> basicInfo(ProjectBaseVo vo) {
		try {
			//1. check login
			// 1.1 check if the token exists in the request.
			String accessToken = vo.getAccessToken();
			if (StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("The request must offer an accessToken");
				return fail;
			}

			// 1.2 check if the memberId represented by the accessToken exist.
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(memberId)) {// not exists, so the user not login yet.
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("You need to login before creating an project");
				return fail;
			}
			
			//2 copy data from vo to bigVo
			String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEM_PROJECT_PREFIX+vo.getProjectToken());
			ProjectBaseVo bigVo = JSON.parseObject(bigStr,ProjectBaseVo.class);
			BeanUtils.copyProperties(vo, bigVo);
			bigStr = JSON.toJSONString(bigVo);
			stringRedisTemplate.opsForValue().set(ProjectConstant.TEM_PROJECT_PREFIX+bigVo.getProjectToken(), bigStr);
			log.debug("bigVo:{}", bigVo );
			return AppResponse.ok(bigVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("request dealing failed", e.getMessage());
			return AppResponse.fail(null);
		}
		
	}

	@ApiOperation(value = "3-project return detail")
	@PostMapping("/returnDetail")
	public AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> pro) {
		
		if(pro==null || pro.isEmpty()) {
			AppResponse<Object> fail = AppResponse.fail(null);
			fail.setMsg("The request must contain at least one return!");
			return fail;
		}
		try {
			//1. check login
			// 1.1 check if the token exists in the request.
			String accessToken = pro.get(0).getAccessToken();
			if (StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("The request must offer an accessToken");
				return fail;
			}

			// 1.2 check if the memberId represented by the accessToken exist.
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(memberId)) {// not exists, so the user not login yet.
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("You need to login before creating an project");
				return fail;
			}
			
			//2 copy data from pro to bigVo
			String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEM_PROJECT_PREFIX+pro.get(0).getProjectToken());
			ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr,ProjectRedisStorageVo.class);
			for (ProjectReturnVo vo : pro) {
				TReturn rt = new TReturn();
				BeanUtils.copyProperties(vo, rt);
				bigVo.getRetunList().add(rt);
			}
			
			bigStr = JSON.toJSONString(bigVo);
			stringRedisTemplate.opsForValue().set(ProjectConstant.TEM_PROJECT_PREFIX+bigVo.getProjectToken(), bigStr);
			log.debug("bigVo:{}", bigVo );
			return AppResponse.ok(bigVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("request dealing failed", e.getMessage());
			return AppResponse.fail(null);
		}
	}

	@ApiOperation(value = "upload pic")
	@PostMapping("/uploadPic")
	public AppResponse<Object> uploadPic(@RequestParam("uploadfile") MultipartFile[] files) {
		List<String> filePathList = new ArrayList();
		try {
			for (MultipartFile file : files) {
				String filename = file.getOriginalFilename();
				filename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + filename;
				String path = ossTemplate.upload(filename, file.getInputStream());
				filePathList.add(path);
			}
			log.debug("upload file path={}", filePathList);
			return AppResponse.ok(filePathList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> fail = AppResponse.fail(null);
			fail.setMsg(e.getMessage());
			return fail;
		}
	}

	/*
	 * @ApiOperation(value = "company info")
	 * 
	 * @PostMapping("/companyInfo") public AppResponse<Object> companyInfo(){ return
	 * AppResponse.ok("ok"); }
	 */
	/*
	 * @ApiOperation(value = "temp save")
	 * 
	 * @PostMapping("/tempSave") public AppResponse<Object> tempSave(){ return
	 * AppResponse.ok("ok"); }
	 */
	@ApiOperation(value = "4-sumit project")
	@PostMapping("/sumit")
	public AppResponse<Object> sumit(String accessToken,String projectToken,String option) {
		try {
			//1. check login
			// 1.1 check if the token exists in the request.
			if (StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("The request must offer an accessToken");
				return fail;
			}
			// 1.2 check if the memberId represented by the accessToken exist.
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(memberId)) {// not exists, so the user not login yet.
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("You need to login before creating an project");
				return fail;
			}
			if("0".equals(option)) {
				return AppResponse.ok("ok");
			}else if ("1".equals(option)) {
				projectService.save(accessToken,projectToken,ProjectStatusEnume.DRAFT.getCode());
				return AppResponse.ok("ok");
			}else{
				AppResponse<Object> fail = AppResponse.fail(null);
				fail.setMsg("Unsupported request");
				return fail;
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("request failed");
			return AppResponse.fail(null);
		}
	}

}





