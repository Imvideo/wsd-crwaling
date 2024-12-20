package com.example.wsd_crawling.bookmarks.service;

import com.example.wsd_crawling.bookmarks.model.Bookmark;
import com.example.wsd_crawling.bookmarks.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public boolean toggleBookmark(Long userId, Long jobPostingId) {
        // 북마크 여부 확인
        Bookmark bookmark = bookmarkRepository.findByUserIdAndJobPostingId(userId, jobPostingId).orElse(null);

        if (bookmark != null) {
            // 이미 존재하는 북마크 제거
            bookmarkRepository.delete(bookmark);
            return false; // 북마크 제거됨
        } else {
            // 새로운 북마크 추가
            bookmark = new Bookmark();
            bookmark.setUserId(userId);
            bookmark.setJobPostingId(jobPostingId);
            bookmarkRepository.save(bookmark);
            return true; // 북마크 추가됨
        }
    }

    public Page<Bookmark> getUserBookmarks(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return bookmarkRepository.findAllByUserId(userId, pageable);
    }
}
