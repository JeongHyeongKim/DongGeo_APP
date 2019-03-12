# APP_DongGeo



## Dependencies
- android studio 3.2.1
- JRE : 1.8.0_152-release-1136-b06 x86_64
- JVM : OpenJDK 64-Bit Server VM by JetBrains
- kakao SDK 1.1.14
- https://github.com/nekocode/Badge

## 처리해야 할 과제들 ver.20190310
  1. 파일 이름의 가독성 개선
  2. 환율 업데이트 주기 일 4회 (현재 splash activity 실행 시 환율 업데이트)
  3. server RESTful하게 개선
  4. 댓글 거래 승인기능과 승인 시 개인 연락하는 기능
  5. 거래중인 상황에 회원 탈퇴 불가
  6. 공지사항 기능 추가
  7. 댓글이 없을 경우의 UI
  8. 현재 가지고 오는 환율 정보가 매매가인지 매입가인지 확인 요망  -> (현찰 살때+현찰 팔때)/2  로 확인되었음 20190311
  9. mypage의 "삽니다"의 경우 거래 상황 parameter에 따라 badge로 구별
  10. mypage의 fragment와 검색 기능을 통한 fragment의 로직이 다름 -> 통일
  11. 글 및 댓글 수정기능
  12. 대소문자 구별없이 검색가능 하게 수정 요망 (외국환 약어 검색등 ..)

## 뒤늦게 쓰기 시작한 개발일지
- 20190310
  1. 흩어져 있는 메뉴바 통일 완료
  2. 사용자 검색용 아이디의 등록 유무 판별 알고리즘 수정
