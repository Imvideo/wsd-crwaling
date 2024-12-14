package com.example.wsd_crawling.bookmarks.controller;

import com.example.wsd_crawling.bookmarks.dto.BookmarkRequest;
import com.example.wsd_crawling.bookmarks.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@Tag(name = "Bookmarks", description = "즐겨찾기 관련 API")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "즐겨찾기 추가/삭제", description = "사용자가 채용 공고를 즐겨찾기에 추가하거나 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 상태 변경 성공", content = @Content(schema = @Schema(example = "Bookmark added successfully."))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> toggleBookmark(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "즐겨찾기 요청 데이터", required = true)
            BookmarkRequest bookmarkRequest) {

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
    @Operation(summary = "즐겨찾기 목록 조회", description = "사용자가 즐겨찾기한 채용 공고 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Page<?>> getBookmarks(
            @PathVariable @Schema(description = "사용자 ID", example = "1") Long userId,
            @RequestParam(defaultValue = "0") @Schema(description = "페이지 번호", example = "0") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "페이지 크기", example = "10") int size) {

        Page<?> bookmarks = bookmarkService.getUserBookmarks(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
