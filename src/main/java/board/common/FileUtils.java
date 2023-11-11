package board.common;

import java.io.File;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;
import board.board.entity.BoardFileEntity;

@Component
public class FileUtils {

//    mybatis update
    public List<BoardFileDto> parseFileInfo1(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
			return null;
		}

        List<BoardFileDto> fileList = new ArrayList<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/" + current.format(format);
        File file = new File(path);
        if (file.exists() == false) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;
//        파일 크기가 너무 크면 익셉션 발생
        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                contentType = multipartFile.getContentType();

                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                } else {
					originalFileExtension = "";
				}

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                BoardFileDto boardFile = new BoardFileDto();
                boardFile.setBoardIdx(boardIdx);
                boardFile.setFileSize((multipartFile.getSize()/1024));
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath(path + "/" + newFileName);
                fileList.add(boardFile);

                file = new File(path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
//    jpa update
    public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<BoardFileEntity> fileList = new ArrayList<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/" + current.format(format);
        File file = new File(path);
        if (file.exists() == false) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if (multipartFile.isEmpty() == false) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        if (contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else {
                            originalFileExtension = "";
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    BoardFileEntity boardFile = new BoardFileEntity();
                    boardFile.setFileSize(multipartFile.getSize());
                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    boardFile.setStoredFilePath(path + "/" + newFileName);
                    boardFile.setCreatorId("admin");
                    fileList.add(boardFile);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }

//    jpa 삭제 메서드
    public void deleteFile_Path(List<String> FilePath) {
        for(String filePath : FilePath){
            File file = new File(filePath);
            file.delete();
        }
    }

    public void deleteBoardFile(List<BoardFileEntity> FileList) {
        while (FileList.size() != 0) {
            String storedFilePath = Paths.get("").toAbsolutePath() + "\\" + FileList.get(0).getStoredFilePath();
            File file = new File(storedFilePath);
            file.delete();

            FileList.remove(0);
        }
//		파일 위까지 절대경로 = Paths.get("").toAbsolutePath()
//		사진이름겸 경로 = boardFileImg.getStoredFilePath()
    }

//    mybatis 삭제 메서드
    public void deleteBoardFile2(List<BoardFileDto> FileList) {
        while (FileList.size() != 0) {
            String storedFilePath = Paths.get("").toAbsolutePath() + "\\" + FileList.get(0).getStoredFilePath();
            File file = new File(storedFilePath);
            file.delete();

            FileList.remove(0);
        }
//		파일 위까지 절대경로 = Paths.get("").toAbsolutePath()
//		사진이름겸 경로 = boardFileImg.getStoredFilePath()
    }

}
