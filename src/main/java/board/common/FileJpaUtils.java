package board.common;

import board.board.dto.BoardFileReqDto;
import board.board.dto.BoardFileResDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileJpaUtils {

    public List<BoardFileResDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<BoardFileResDto> fileList = new ArrayList<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/"+current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while(iterator.hasNext()){
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list){
                contentType = multipartFile.getContentType();
                if(contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                }
                else if(contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                }
                else if(contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                else {
                    originalFileExtension = "";
                }

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                BoardFileResDto boardFile = new BoardFileResDto();
                boardFile.setBoardIdx(boardIdx);
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath(path + "/" + newFileName);
                fileList.add(boardFile);

                file = new File(path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }

    public List<BoardFileReqDto> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<BoardFileReqDto> fileList = new ArrayList<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/"+current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while(iterator.hasNext()){
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list){
                contentType = multipartFile.getContentType();

                if(contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                }
                else if(contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                }
                else if(contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                else{
                    originalFileExtension = "";
                }

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                BoardFileReqDto boardFile = new BoardFileReqDto();
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath(path + "/" + newFileName);
                boardFile.setCreatorId("admin");
                fileList.add(boardFile);

                file = new File(path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}
