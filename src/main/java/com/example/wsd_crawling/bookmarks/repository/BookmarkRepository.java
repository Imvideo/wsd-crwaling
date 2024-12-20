package com.example.wsd_crawling.bookmarks.repository;

import com.example.wsd_crawling.bookmarks.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 특정 사용자의 특정 공고에 대한 북마크 조회
    Optional<Bookmark> findByUserIdAndJobPostingId(Long userId, Long jobPostingId);

    // 특정 사용자의 북마크 목록 조회 (페이징)
    Page<Bookmark> findAllByUserId(Long userId, Pageable pageable);
}
