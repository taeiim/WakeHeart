# WakeHeart API

### 로그인

* `/API/login`


* 요청
  * POST
* 인자
  * id: String
  * Password: String



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



### 비밀번호 변경

* `/API/changePassword`
* 요청
  * POST
* 인자
  * user: String (사용자 이름)
  * password: String (현재 비밀번호)
  * changePassword: String (변경할 비밀번호)

### 명언 데이터 가져오기

* `/API/phrases/:number`


* 요청
  * GET
* 인자
  * number: 명언 가져올 갯수



### 상태 가져오기

* `/API/status/`
* 요청
  * POST
* 인자
  * user: String
  * status: Int



### 휴게소 가져오기

* `/API/rests/`
* 요청
  * latitude: Float
  * longitude: Float



### 기기 등록

* `/API/device/add`
* 요청
  * POST 
* 인자
  * imei: String (안드로이드 고유 값)
