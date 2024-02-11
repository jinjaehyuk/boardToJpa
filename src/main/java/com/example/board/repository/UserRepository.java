package com.example.board.repository;

import com.example.board.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//Spring Data JPA Repository 완성!
//UserRepository를 구현하는 Bean을 자동으로 생성한다.
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(int userId);
    Optional<User> findByName(String name); //query method - Spring Data JPA

    //name = ? and email = ?
    Optional<User>  findByNameAndEmail(String name, String email);

    //where name like ? or email = ?
    List<User> findByNameOrEmail(String name, String email);

    //where user_id between ? and ?
    List<User>  findByUserIdBetween(int startUserId, int endUserId);

    //where user_id < ?
    List<User> findByUserIdLessThan(int UserId);

    //where user_id <= ?
    List<User> findByUserIdLessThanEqual(int UserId);

    //where user_id > ?
    List<User> findByUserIdGreaterThan(int UserId);


    //where user_id >= ?
    List<User> findByUserIdGreaterThanEqual(int UserId);

    //where regdate > ?
    List<User> findByRegdateAfter(LocalDateTime day);
    //where regdate < ?
    List<User> findByRegdateBefore(LocalDateTime day);

    //where name is null
    List<User> findByNameIsNull();
    //where name is not null
    List<User> findByNameIsNotNull();

    List<User> findByNameLike(String name);

    List<User> findByNameStartingWith(String name);

    List<User> findByNameEndingWith(String name);

    List<User> findByNameContaining(String name);

    List<User> findByOrderByNameAsc();

    List<User> findByNameNot(String name);

    List<User> findByUserIdIn(Collection<Integer> userIds);

    List<User> findByUserIdNotIn(Collection<Integer> userIds);

    Long countByNameLike(String name);

    Long countBy();

    boolean existsByEmail(String email);

    int deleteByName(String name);

    List<User> findDistinctByName(String name);

    Page<User> findBy(Pageable pageable);

    Page<User> findByName(String name, Pageable pageable);
}

//Page<Board> findBy(Pageable pageable);
//Page<Board> findByName(String name, Pageable pageable);
//Page<Board> findByTitleContaining(String title, Pageable pageable);
//Page<Board> findByContentContaining(String content, Pageable pageable);
//Page<Board> findByTitleContainingOrContentContaining(String Title, String content, Pageable pageable);
