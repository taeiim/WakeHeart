# WakeHeart API

### 로그인

*  `/API/login`
* 요청
  * POST 
* 인자
  * id: String
  * password: String
* 동작
  * 로그인 성공 시 메인페이지로 이동하며, 쿠키 (키값 "user")에 사용자 아이디를 저장한다.
* 반환값
  * `{"success": false}` - 실패
  * `{"error": e.printStackTrace(), "success": false} `- 예외가 발생할 경우 실패
  * `{"success": true}` - 성공

### 회원가입

* `/API/register`
* 요청
  * POST
* 인자
  * id: String
  * password: String
  * nickname: String
  * gender: int
    * 0: 남자
    * 1: 여자
  * age: int
* 동작
  * 회원가입 성공 시 메인페이지로 이동하며,  쿠키 (키값 "user")에 사용자 아이디를 저장한다.
* 반환값
  * `{"error": e.printStackTrace(), "success": false} `- 예외가 발생할 경우 실패
  * `{"success": true}` - 성공

### 로그아웃

- `/API/logout`
- 요청
  - POST
- 인자
  - 없음
- 동작
  - 로그아웃 성공 시, 쿠키 (키값 "user")을 삭제한다.
- 반환값
  - 없음

### 비밀번호 변경

* `/API/changePassword`
* 요청
  * POST
* 인자
  * user: String (사용자 이름)
  * password: String (현재 비밀번호)
  * changePassword: String (변경할 비밀번호)

### 명언 데이터 가져오기

* `/API/phrase/all`
* 요청
  * GET
* 인자
  * 없음
* 반환값
  * `{"phrase":{"description":"공부열심히하자1"}, {"description":"공부열심히하자2"}}`

### 상태 가져오기

* `/API/status/`
* 요청
  * POST
* 인자
  * user: String
  * status: Int

### 휴게소 가져오기

* `/API/rest/all`
* 요청
  * GET
* 인자
  * 없음

### 기기 등록

* `/API/device/add`
* 요청
  * POST 
* 인자
  * imei: String (안드로이드 고유 값)
