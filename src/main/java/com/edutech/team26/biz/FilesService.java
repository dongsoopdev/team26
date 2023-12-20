package com.edutech.team26.biz;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface FilesService {

    String filesDownload(Long fileNo, HttpServletRequest request, HttpServletResponse response) throws Exception;

}