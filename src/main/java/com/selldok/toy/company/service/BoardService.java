package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Gogisung
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 구직 정보 등록
     * */
    public Long create(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 파일 업로드
     * */
    public String saveUploadFile(MultipartFile upload_file) {
        String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
        try{
            upload_file.transferTo(new File("C:/Users/ggs/Documents/workspaces/CLONE-PROJECT/src/main/resources/upload/" + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }

    // 게시글 리스트 모두 가져오기
    public List<Board> findAllBoard() {
        return boardRepository.findAll();
    }

}
