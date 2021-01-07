package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.model.BoardReadResponse;
import com.selldok.toy.company.model.BoardUpdateRequest;
import com.selldok.toy.company.model.NewHireListResponse;
import com.selldok.toy.company.model.RecommendThisWeekResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
/**
 * @author Gogisung
 */
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardService {
    private final BoardRepository boardRepository;

    @Value("${spring.servlet.multipart.location}")
    String uploadFileDir;

    public List<BoardReadResponse> findBoard(Long id) {
        return boardRepository.findByBoardInfo(id);
    }

    public List<NewHireListResponse> newHireByBoardInfo() {
        return boardRepository.newHireByBoardInfo();
    }

    public List<RecommendThisWeekResponse> recommendThisWeek() { return boardRepository.recommendThisWeek(); }

    public int boardCountPlus(Long id) {
        return boardRepository.boardCountPlus(id);
    }
    /**
     * 구직 정보 등록
     * */
    public Long create(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 구직정보수정
     * */
    public Long update(Long id, BoardUpdateRequest request) {
        Optional<Board> board = boardRepository.findById(id);

        board.ifPresent(existingCompany -> {
            existingCompany.setTitle(request.getTitle());
            existingCompany.setContent(request.getContent());
            existingCompany.setImage(request.getImage());
            existingCompany.setEndDate(request.getEndDate());
            boardRepository.save(existingCompany);
        });

        return id;
    }

    /**
     * 파일 업로드
     * */
    public String saveUploadFile(MultipartFile upload_file) {
        String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
        try{
            upload_file.transferTo(new File(uploadFileDir + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }


}
