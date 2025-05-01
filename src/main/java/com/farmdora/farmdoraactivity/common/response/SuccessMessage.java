package com.farmdora.farmdoraactivity.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    SEARCH_ORDER_SUCCESS("주문 목록 조회에 성공하였습니다."),
    SEARCH_SALES_SUCCESS("상품 목록 조회에 성공하였습니다."),
    SEARCH_SALESTATUS_SUCCESS("매출 현황 조회에 성공하였습니다."),
    SEARCH_STATUSRATIO_SUCCESS("반품 및 교환율 조회에 성공하였습니다."),
    SEARCH_PRODUCTRATIO_SUCCESS("제품별 판매 비율 조회에 성공하였습니다."),
    SEARCH_TOTALSALES_SUCCESS("사이트 매출 조회에 성공하였습니다."),
    SEARCH_TOTALUSERS_SUCCESS("사이트 가입자 수 조회에 성공하였습니다."),
    SEARCH_USERDASHBOARDINFO_SUCCESS("내 정보 조회에 성공하였습니다."),
    SEARCH_ORDERSTATUSINFO_SUCCESS("주문 현황 조회에 성공하였습니다."),
    SEARCH_WISHLIST_SUCCESS("찜 리스트 조회에 성공하였습니다."),
    REVIEW_DELETE_SUCCESS("리뷰가 성공적으로 삭제되었습니다."),
    SEARCH_REVIEW_DETAIL_SUCCESS("리뷰 상세조회에 성공하였습니다."),
    SEARCH_REVIEW_ALL_SUCCESS("리뷰 전체 조회에 성공하였습니다."),
    SEARCH_LIKE_SUCCESS("찜 리스트 조회에 성공하였습니다."),
    SEARCH_DELETELIKE_SUCCESS("찜 항목 삭제에 성공하였습니다."),
    SEARCH_QUESTIONINFO_SUCCESS("내 문의 내역 조회에 성공하였습니다."),
    SEARCH_ORDERMANAGECOUNT_SUCCESS("주문 관리 갯수 조회에 성공하였습니다."),
    SEARCH_ORDERDETAILINFO_SUCCESS("주문 상세정보 조회에 성공하였습니다."),
    SEARCH_REFUNDINFO_SUCCESS("반품 정보 조회에 성공하였습니다.");





    private final String message;
}