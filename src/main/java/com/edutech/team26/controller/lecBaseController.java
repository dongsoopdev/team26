package com.edutech.team26.controller;

import com.edutech.team26.util.PageUtil;


public class lecBaseController {

	public String getPaperHtml(long totalCount, long pageSize, 
								long pageIndex, String queryString) {
		
		PageUtil pageUtil  = new PageUtil(totalCount, pageSize, pageIndex, queryString);
		
		return pageUtil.pager();
	}
}
