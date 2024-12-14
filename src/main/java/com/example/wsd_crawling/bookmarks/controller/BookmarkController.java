package com.example.wsd_crawling.bookmarks.controller;

import com.example.wsd_crawling.bookmarks.dto.BookmarkRequest;
import com.example.wsd_crawling.bookmarks.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<String> toggleBookmark(@RequestBody BookmarkRequest bookmarkRequest) {
        Long userId = bookmarkRequest.getUserId();
        Long jobPostingId = bookmarkRequest.getJobPostingId();

        boolean isBookmarked = bookmarkService.toggleBookmark(userId, jobPostingId);

        if (isBookmarked) {
            return ResponseEntity.ok("Bookmark added successfully.");
        } else {
            return ResponseEntity.ok("Bookmark removed successfully.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<?>> getBookmarks(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<?> bookmarks = bookmarkService.getUserBookmarks(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
