package com.example.wsd_crawling.bookmarks.controller;

import com.example.wsd_crawling.bookmarks.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;
    @PostMapping
    public ResponseEntity<String> toggleBookmark(
            @RequestParam Long userId,
            @RequestParam Long jobPostingId
    ) {
        boolean isBookmarked = bookmarkService.toggleBookmark(userId, jobPostingId);

        if (isBookmarked) {
            return ResponseEntity.ok("Bookmark added successfully.");
        } else {
            return ResponseEntity.ok("Bookmark removed successfully.");
        }
    }
}
