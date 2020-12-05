package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.BoardSearchCondition;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.model.BoardReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * @author Gogisung
 */
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    private Environment env;

    public List<BoardReadResponse> findBoard(Long boardId) {
        return boardRepository.read(boardId);
    }

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
            upload_file.transferTo(new File(new Locale(env.getProperty("path.upload")) + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }


}
