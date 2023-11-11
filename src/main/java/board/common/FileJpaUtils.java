package board.common;

import board.board.dto.BoardFileReqDto;
import board.board.dto.BoardFileResDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileJpaUtils {

    public List<BoardFileResDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<BoardFileResDto> fileList = new ArrayList<>();

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
//                  이 조건을 체크해야 빈파일을 리스트에 넣지않음, 빈파일이 들어가면 상세페이지에서 이름이 빈 파일이 추가됨
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        continue;
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
            }
        }
        return fileList;
    }

    public List<BoardFileReqDto> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<BoardFileReqDto> fileList = new ArrayList<>();

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
//                  이 조건을 체크해야 빈파일을 리스트에 넣지않음, 빈파일이 들어가면 상세페이지에서 이름이 빈 파일이 추가됨
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
        }
        return fileList;
    }

    public void deleteFile_Path(List<String> FilePath) {
        for (String filePath : FilePath) {
            File file = new File(filePath);
            file.delete();
        }
    }

    public void deleteBoardFile(List<BoardFileResDto> FileList) {
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
