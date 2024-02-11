package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {


    //JPQL
    //sql과 모양이 비슷하지만, SQL이 아님.
    //JPQL은 객체지향 언어
    //JPQL에서 from뒤에 있는것은 엔티티 이름
    //Board 엔티티들을 조회하라. 엔티티는 결국 table과 관계.
    @Query(value = "select b from Board b join fetch b.user")
    List<Board> getBoards();

    @Query(value = "select count(b) from Board b")
    long getBoardCount();

    Page<Board> findByOrderByRegdateDesc(Pageable pageable);

//    관리자만 쓴 글만 목록을 구하는 JPQL을 작성
//    @Query(value = "select b from Board b inner join fetch b.user u inner join u.roles r where r.name = :roleName")
    @Query(value = "select b,u from Board b inner join b.user u inner join u.roles r where r.name = :roleName")
    List<Board> getBoards(@Param("roleName") String roleName);

//    @Query( value = "select b.board_id, b.title, b.content, b.user_id, u.name, b.regdate, b.view_cnt from board b, user u  where b.user_id = u.user_id",
//    nativeQuery = true)
//    List<BoardDtoIF> getBoardWithNativeQuery();

}
