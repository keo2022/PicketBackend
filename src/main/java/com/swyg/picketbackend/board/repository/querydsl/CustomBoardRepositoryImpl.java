package com.swyg.picketbackend.board.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.QBoard;
import com.swyg.picketbackend.board.dto.req.BoardListRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Slice<Board> findByList(BoardListRequestDTO boardListRequestDTO) {
        QBoard board = QBoard.board;

        // parameter setting
        String keyword = boardListRequestDTO.getKeyword(); // 키워드
        List<String> categoryList = boardListRequestDTO.getCategoryList();  // 해당 카테고리 목록
        int page = boardListRequestDTO.getPage(); // 페이지 번호
        int size = boardListRequestDTO.getSize(); // 페이지 사이즈


        // 카테고리 조건
        BooleanExpression categoryExpression = board.categoryList.any().name.in(boardListRequestDTO.getCategoryList());

        // 검색어 조건
        BooleanExpression keywordExpression = board.title.containsIgnoreCase(boardListRequestDTO.getKeyword())
                .or(board.content.containsIgnoreCase(boardListRequestDTO.getKeyword()));

        // 카테고리 + 검색어 조건
        BooleanExpression condition = categoryExpression.and(keywordExpression);

        // 카테고리 선택 안했으면 카테고리 조건 무시
        if (categoryList == null || categoryList.isEmpty()) {
            condition = keywordExpression;
        }

//        return jpaQueryFactory.selectFrom(board) //  검색어 있을 시
//                .where(
//                        board.categoryList.any().name.in(boardListRequestDTO.getCategoryList()),
//                        board.title.containsIgnoreCase(boardListRequestDTO.getKeyword()))
//                .fetch();
        return null;

    }
}

