package com.edutech.team26.biz;

import com.edutech.team26.domain.Files;
import com.edutech.team26.repository.FilesRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class FilesServiceImpl implements FilesService{

    private final FilesRepository filesRepository;

    @Override
    public String filesDownload(Long fileNo, HttpServletRequest request, HttpServletResponse response) throws Exception {String urlPath = request.getHeader("referer");

        Optional<Files> optionalFiles = filesRepository.findById(fileNo);
        if(optionalFiles.isEmpty()) {
            return "";
        }

        Files files = optionalFiles.get();

        String saveFolder = files.getFileSaveFolder();
        String originalFile = files.getFileOriginNm();
        String saveFile = files.getFileSaveNm();
        File file = new File(saveFolder, saveFile);

        response.setContentType("apllication/download; charset=UTF-8");
        response.setContentLength((int) file.length());

        String header = request.getHeader("User-Agent");
        boolean isIE = header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1;
        String fileName = null;
        // IE는 다르게 처리
        if (isIE) {
            fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
        } else {
            fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                    out.flush();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }

        return urlPath;
    }

}