
# Let EAT Go
> 혼밥은 쉽고, 맛밥은 어려워

<p>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=Java&logoColor=white"/>
 </p>
 <p>
  <img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=Node.js&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-000000?style=flat-square&logo=MySQL&logoColor=white"/>
  <img src="https://img.shields.io/badge/express-000000?style=flat-square&logo=express&logoColor=white">

</p>

<img src="https://user-images.githubusercontent.com/74184274/148947164-7913af20-7262-4429-9bb8-2ccce84f7ea0.png" width="150" height="150">

|학식 메뉴 확인|밥팟 탐색|마이페이지|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947325-882c67ad-2be2-4d43-ae63-9912968d91d0.jpg" width="210" height="400">|<img src = "https://user-images.githubusercontent.com/74184274/148947332-ab84385a-a924-4f19-b5fd-4dcf43e2b163.jpg" width = "210" height = "400">|<img src = "https://user-images.githubusercontent.com/74184274/148947340-f33daa24-7084-4a50-9130-0fbb7d15a241.jpg" width = "210" height = "400">| 

<br/>

<br/>

##  카카오톡 로그인
> 카카오톡 계정을 이용하여 서비스
    
|타이틀|로그인 화면|
|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947336-c326b09d-905d-402f-a2b0-492ebf20674e.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947339-5a73923c-d333-4326-a943-11b875f07b40.jpg" width="145" height="300">|  
   - `KakaoTalk Login SDK`를 사용하여 `DB` 에  `ID` 와  `name` 저장 
<br/>

<br/>

##  학식 메뉴 확인
> "오늘도 학식 별로네..."
### 인삿말
<img src="https://user-images.githubusercontent.com/74184274/148947324-fa9a7042-7634-48e3-8502-7afcebac608d.jpg" width="145" height="300">
    
- 현재 시간에 따라 알맞은 인삿말을 건내줍니다.
  - `Date` 클래스를 이용하여 현재 시간을 받아오고, 이를 바탕으로 `TextView`에 문구를 설정합니다.
### 메뉴 확인 탭
  |오늘 메뉴 확인|끼니별 메뉴 확인|금주 메뉴 확인|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947325-882c67ad-2be2-4d43-ae63-9912968d91d0.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947327-316625a3-722e-4f4a-a288-b7255a400f42.jpg" width="145" height="300">|<img src = "https://user-images.githubusercontent.com/74184274/148947330-d5ca4fff-60ee-4195-b107-9e230b44ea56.jpg" width="145" height="300">|
   - 해당 식당의 메뉴를 보여줍니다.
     - `jsoup`를 사용하여 카이스트 학식 안내 웹 페이지에서 메뉴 부분을 크롤링하여 텍스트 뷰로 보여줍니다. 메뉴 위쪽의 스피너를 통해 원하는 날짜를 선택하면, 해당 날짜의 학식 메뉴를 확인할 수 있습니다. 텍스트 뷰의 뒤에 블러 뷰를 배치하여 가독성을 높였습니다.
   - 종 스크롤을 통해 메뉴를 전부 확인 할 수 있습니다.
     - `NestedScrollView` 를 사용하여, 블러 뷰 안의 영역에서 스크롤 시 전체 메뉴를 확인할 수 있습니다.
   - 횡 스크롤을 통해 아침, 점심, 저녁 메뉴를 확인 할 수 있습니다.
     - `ViewPager2` 를 사용하여 스와이프를 통해 메뉴 전환이 가능합니다.
   - 상단 스피너를 통해 금주의 메뉴를 확인 할 수 있습니다.
     - `Spinner`를 통해 일주일 내의 날짜를 선택하면, 자동으로 해당 날짜의 메뉴가 로딩됩니다.
     
<br/>

<br/>

##  밥팟 탐색
> "나랑 밥 먹으러 갈 사람?" 
### 밥팟 참여
  |밥팟 조회|당겨서 새로고침|참가 팝업|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947332-ab84385a-a924-4f19-b5fd-4dcf43e2b163.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947334-aacb4f4c-fd7c-4104-b95e-d02507065230.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947342-eb1210c2-310b-4686-89b2-0d4fe7259bcd.jpg" width="145" height="300">| 
- 밥 파티의 목록을 확인하세요.
  - `API server` 로부터 목록을 `json` 으로 받아와 `Party_Item`  `class`로 `ArrayList` 에 저장합니다.
  - `MyItemRecyclerViewAdapter` 를 통해 `RecyclerView` 에 `Party_Item` 을 적용합니다.
- 목록을 위로 당겨서 새로고침하세요.
  - `SwipeFreshLayout` 을 사용해 `OnRefreshListener` 로 응답을 받아 `API server` 로부터 목록을 새로고침합니다. 
- 파티를 클릭하면 참여 팝업을 확인 할 수 있습니다.
- 참여 팝업에서는 현재 참여 중인 사람들의 이름을 확인 할 수 있고, OK를 누를 시 참가됩니다.
  - `itemView.OnclickListener` 로 응답을 받아 `mData(position)` 으로부터 `name` 을 받아와  `AlertDialog`에 표시합니다.
- 아래 + 버튼을 이용해 새로운 파티를 만들 수 있습니다.
  - `Floating Button` 을 이용해 `CreatePartyActivity` 를 호출합니다.

|참가 성공|이미 참여한 경우|꽉 차있는 경우|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947344-d2d4e613-091a-40f3-b0fe-b9cfdf37f332.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947345-e727e277-3562-4265-820e-a0d4c36015f0.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947346-b560778d-8e6f-4b57-bf97-a6dbc6b27abd.jpg" width="145" height="300">| 
  
  - 참가에 성공할 경우 성공 메시지를 띄웁니다.
  - 이미 참여한 파티에 다시 참여할 경우 실패 메시지를 띄웁니다.
  - 꽉 찬 파티에 참여한 경우 실패 메시지를 띄웁니다.
    - `API Server` 에 `participate`를 요청해 `respond` 를 받아 `Toast Message` 를 띄웁니다.

### 밥팟 생성
  |생성 화면|날짜 선택|시간 선택|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947312-10afc0a3-8b2e-4395-b209-43c6fb1e3302.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947314-3df20795-8d0b-4ff6-9734-ba6ca628cd77.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947318-1e2387f5-4e03-4cb9-91a8-a560a8570e7b.jpg" width="145" height="300">|
  
|카테고리 선택|1인 이하 선택시|
|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947316-69f74362-1bea-4798-8098-53d6d3b56bf0.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947321-f3669fdd-524a-402e-bf9f-5319b84850a8.jpg" width="145" height="300">|

  - 카테고리를 눌러 기존에 있는 카테고리 중 선택하세요.
    - `Spinner` 를 이용해 설정한 카테고리 중 선택 하게 하였습니다.
  - 식당의 이름을 입력하세요.
    - `EditText` 를 이용해 이름 입력을 받습니다.
  - 바를 드래그해 참여 인원 수를 설정하세요.
    - `SeekBar` 를 이용해 인원 수를 지정합니다.
  - 날짜를 눌러 달력에서 날짜를 고르세요.
    - `DatePickerDialog` 를 이용해 날짜를 선택합니다.
  - 시간을 눌러 시계에서 시각을 고르세요.
    - `TimePickerDialog` 를 이용해 시간을 선택합니다.
  - 생성 버튼을 누르면 파티가 생성됩니다.
    - `API server` 로 `create` 를 요청합니다.
  - 1인 이하의 인원을 선택할 경우 생성이 되지 않고, 실패 메시지를 띄웁니다.
    
<br/>

<br/>

## 마이 페이지
> 본인의 정보와 파티를 관리하세요.
### 정보 확인
  <img src = "https://user-images.githubusercontent.com/74184274/148947340-f33daa24-7084-4a50-9130-0fbb7d15a241.jpg" width="145" height="300">


### 파티 관리
|생성 파티 조회|생성 파티 삭제|당겨서 새로고침|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947348-1bd2953c-cb31-40c9-81ea-d7e25d6ae7e5.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947354-083b7f60-7df2-4388-8bb9-cc8d3ea01ad1.jpg" width="145" height="300">|<img src = "https://user-images.githubusercontent.com/74184274/148947352-ae05e6c6-baf9-4756-9acb-fed58a438575.jpg" width="145" height="300">|


|참가 파티 조회|참가 파티 탈퇴|당겨서 새로고침|
|--|--|--|
|<img src="https://user-images.githubusercontent.com/74184274/148947361-dd0e44f6-7efd-430d-b3d4-9def4b63151d.jpg" width="145" height="300">|<img src="https://user-images.githubusercontent.com/74184274/148947365-97994bee-b2c1-4717-92d0-794f81a328ac.jpg" width="145" height="300">|<img src = "https://user-images.githubusercontent.com/74184274/148947364-3dbf8826-dc12-4c47-add2-780ceee605fd.jpg" width="145" height="300">|

<br/>

<br/>

## Api Server
> API Web Server with HTTP protocol
  
  ### `nodejs` 의 `Express` 를 사용


<br/>

<br/>

## Data Base
> Data Base of Users, Partys
> 
 ### `MySQL` 을 이용해 `Data` 관리

<br/>

<br/>

##  Credit
  > Minchae Kim / passa021@korea.ac.kr  
  > Seungjae Lim / seungjaelim@kaist.ac.kr

