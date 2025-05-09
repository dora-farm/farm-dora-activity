package com.farmdora.farmdoraactivity.admin.controller;

import com.farmdora.farmdoraactivity.admin.dto.PopupDTO.*;
import com.farmdora.farmdoraactivity.admin.dto.SearchDTO;
import com.farmdora.farmdoraactivity.admin.dto.SortType;
import com.farmdora.farmdoraactivity.admin.service.PopupService;
import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/popup")
@RequiredArgsConstructor
public class PopupController {

    private final PopupService popupService;

    @GetMapping("/types")
    public ResponseEntity<?> getPopupTypes() {
        List<PopupTypeInfo> popupTypes = popupService.getPopTypes();

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, "팝업 타입 조회에 성공했습니다.", popupTypes));
    }

    /**
     * 팝업/배너 등록
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPopup(
            @ModelAttribute PopupRequest popupRequest,
            @RequestParam("file") MultipartFile file) {

        try {
            PopupRequest request = PopupRequest.builder()
                    .typeId(popupRequest.getTypeId())
                    .title(popupRequest.getTitle())
                    .startDate(popupRequest.getStartDate())
                    .endDate(popupRequest.getEndDate())
                    .build();

            popupService.createPopup(request, file);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new HttpResponse(HttpStatus.CREATED, "팝업/배너 등록에 성공했습니다.", null));

        } catch (Exception e) {
            log.error("팝업/배너 등록 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "팝업/배너 등록 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }

    /**
     * 팝업/배너 목록 조회
     */
    @GetMapping
    public ResponseEntity<?> getPopups(
            @RequestParam(required = false) SortType sortType,
            SearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page
    ) {
        try {
            PageResponseDto<PopupListResponse> popups = popupService.getPopups(sortType, searchDTO, page);
            return ResponseEntity.ok()
                    .body(new HttpResponse(HttpStatus.OK, "팝업/배너 목록 조회에 성공했습니다.", popups));
        } catch (Exception e) {
            log.error("팝업/배너 목록 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "팝업/배너 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }
    /**
     * 팝업/배너 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPopup(@PathVariable Integer id) {
        try {
            PopupResponse popup = popupService.getPopup(id);

            return ResponseEntity.ok()
                    .body(new HttpResponse(HttpStatus.OK, "팝업/배너 조회에 성공했습니다.", popup));

        } catch (Exception e) {
            log.error("팝업/배너 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "팝업/배너 조회 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }

    /**
     * 팝업/배너 수정
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePopup(
            @PathVariable Integer id,
            PopupRequest popupRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("팝업/배너 수정 요청: id={}, typeId={}, title={}", id, popupRequest.getTypeId(), popupRequest.getTitle());

        try {
            PopupRequest request = PopupRequest.builder()
                    .typeId(popupRequest.getTypeId())
                    .title(popupRequest.getTitle())
                    .startDate(popupRequest.getStartDate())
                    .endDate(popupRequest.getEndDate())
                    .build();

            PopupResponse updatedPopup = popupService.updatePopup(id, request, file);

            return ResponseEntity.ok()
                    .body(new HttpResponse(HttpStatus.OK, "팝업/배너 수정에 성공했습니다.", updatedPopup));

        } catch (Exception e) {
            log.error("팝업/배너 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "팝업/배너 수정 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }

    /**
     * 팝업/배너 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePopup(@PathVariable Integer id) {
        try {
            popupService.deletePopup(id);

            return ResponseEntity.ok()
                    .body(new HttpResponse(HttpStatus.OK, "팝업/배너 삭제에 성공했습니다.", true));

        } catch (Exception e) {
            log.error("팝업/배너 삭제 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "팝업/배너 삭제 중 오류가 발생했습니다: " + e.getMessage(), false));
        }
    }
}