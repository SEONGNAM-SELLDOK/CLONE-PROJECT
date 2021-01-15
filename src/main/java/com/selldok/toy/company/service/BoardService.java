package com.selldok.toy.company.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.model.BoardReadResponse;
import com.selldok.toy.company.model.BoardUpdateRequest;
import com.selldok.toy.company.model.NewHireListResponse;
import com.selldok.toy.company.model.RecommendThisWeekResponse;
import com.selldok.toy.config.CelldokFileUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author Gogisung
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardService {
	private final BoardRepository boardRepository;

	private final CelldokFileUtil celldokFileUtil;

	public List<BoardReadResponse> findBoard(Long id) {
		return boardRepository.findByBoardInfo(id);
	}

	public List<NewHireListResponse> newHireByBoardInfo() {
		return boardRepository.newHireByBoardInfo();
	}

	public List<RecommendThisWeekResponse> recommendThisWeek() {
		return boardRepository.recommendThisWeek();
	}

	@Cacheable(value = "countPlus")
	public int boardCountPlus(Long id) {
		return boardRepository.boardCountPlus(id);
	}

	@CacheEvict(value = "countPlus", allEntries = true)
	public void refresh(Long id) {
		slowQuery(60000);
		log.info(id + "의 Cache Clear!!");
	}

	// 빅쿼리를 돌린다는 가정
	private void slowQuery(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
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
		return celldokFileUtil.upload(upload_file);
	}

}
