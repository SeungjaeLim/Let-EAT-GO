# Let EAT Go API Server
    http://192.249.18.138
##  API - users

  ### 모든 계정 조회하기
   - HTTP Protocol
   
    /api/users/all
   - respond - 모든 `User`의 `id`와 `email`을 `json`으로 응답
   
    [{"id":"ID 1","email":"Email 1"},{"id":"ID 2","email":"Email 2"}]
   
   - request example
   
    http://192.249.18.138/api/users/all
   
   
   - respond example
   
    [{"id":"2022012496","email":"몰입캠프"},{"id":"2022345496","email":"크래프톤"}]
   
   ### 계정 등록
   - HTTP Protocol
   
    /api/users/:id/:nickname
   - respond - 기존에 계정이 없을 경우 등록 후 기존 계정 여부와 무관하게 `id`와 `nickname`을 `json`으로 응답
   
    [{"id":":id","email":":email"}]
   
   - request example
   
    http://192.249.18.138/api/users/2022012496/몰입캠프
   
   - respond example
   
    [{"id":"2022012496","nickname":"몰입캠프"}]



##  API - mypage

  ### 호스팅 하고 있는 파티 조회하기
   - HTTP Protocol
   
    /api/mypage/host/:userid
   - respond - `userid`가 호스팅 하고 있는 파티들을 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    http://192.249.18.138/api/mypage/host/2022012496
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012496","Participant1":"2022345496","Participant2":"2022678496","Participant3":"2022901496"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"2022012496","Participant1":null,"Participant2":null,"Participant3":null}]
    
   ### 참가 하고 있는 파티 조회하기
   - HTTP Protocol
   
    /api/mypage/participate/:userid
   - respond - `userid`가 참가 하고 있는 파티 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    http://192.249.18.138/api/mypage/participate/2022012496
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022345496","Participant1":"2022012496","Participant2":"2022346496","Participant3":"3122345496"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"0123456789","Participant1":"2022012496","Participant2":null,"Participant3":null}]
  
##  API - partys

  ### Format of Instance
  
   - `userid` : `카카오톡SDK` 유저 `ID`
    
   - `category` : `KAIST`, `Eoeun`, `Gung` **Must Be English**

   - `name` : 식당 이름

   - `maxjoin` : `integer` **1 ~ 4**

   - `time` : `YYYYMMDDHHMMSS` - `year + month + day + hour + minute + second`
    
  ### 파티 만들기
   - HTTP Protocol
   
    /api/partys/create/:userid/:category/:name/:maxjoin/:time
    
   - respond - 생성된 파티의 정보를 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":1,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":null,"Participant2":null,"Participant3":null}]
    
   - request example
   
    http://192.249.18.138/api/partys/create/2022012496/어은동/밀과보리/4/20220107183000
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":1,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012496","Participant1":null,"Participant2":null,"Participant3":null}]
    
  ### 파티 참여하기
   - HTTP Protocol
   
    /api/partys/participate/:userid/:jobid
    
   - respond - 참여한 파티의 정보를 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - respond - 이미 참여하고 있는 파티인 경우
   
    already joined
    
   - respond - 파티의 정원이 꽉 찬 경우
   
    err-FULL PARTY
    
   - request example
   
    http://192.249.18.138/api/partys/participate/2022012496/1017182317
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":2,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012330","Participant1":"2022012496","Participant2":null,"Participant3":null}]
    
  ### 파티 삭제하기
   - HTTP Protocol - `host`만 파티를 삭제 할 수 있음
   
    /api/partys/delete/:userid/:jobid
    
   - respond - 삭제 성공 여부를 `String`으로 응답
   
    Delete Successed
    
   - respond - `userid`가 호스트가 아닌 경우
   
    Delete Failed
    
   - respond - `jobid`가 잘못된 경우
   
    Delete Failed
    
   - request example
   
    http://192.249.18.138/api/partys/delete/2022012496/9017181057
   
   - respond example
   
    Delete Successed
    
   ### 파티 탈퇴하기
   - HTTP Protocol - `host`가 아닌 유저만 파티를 탈퇴 할 수 있음
   
    /api/partys/resign/:userid/:jobid
    
   - respond - 삭제 성공 여부를 `String`으로 응답, `Participant 1,2,3 Column` 앞에서부터 재정렬
   
    Resign Successed
    
   - respond - `userid`가 호스트인 경우
   
    host cant resign
    
   - respond - 탈퇴에 실패한 경우
   
    Resign Failed
    
   - request example
   
    http://192.249.18.138/api/partys/resign/2022012496/9017181057
   
   - respond example
   
    Resign Successed
    
  ### 파티 전체 조회
   - HTTP Protocol
   
    /api/partys/show/all
    
   - respond - 모든 파티의 정보를 `time`을 `ascending order`로 정렬해 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    http://192.249.18.138/api/partys/show/all
   
   - respond example
   
     [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012496","Participant1":"2022345496","Participant2":"2022678496","Participant3":"2022901496"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"2022012496","Participant1":null,"Participant2":null,"Participant3":null},{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022345496","Participant1":"2022012496","Participant2":"2022346496","Participant3":"3122345496"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"0123456789","Participant1":"2022012496","Participant2":null,"Participant3":null}]
    
   ### 특정 파티 조회
   - HTTP Protocol
   
    /api/partys/show/:jobid
    
   - respond - 해당 파티의 정보를 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    http://192.249.18.138/api/partys/show/1017182317
   
   - respond example
   
        [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":1,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012496","Participant1":null,"Participant2":null,"Participant3":null}]
    
   ### 카테고리 조희
   - HTTP Protocol
   
    /api/partys/category/:category
    
   - respond - 해당 카테고리에 파티의 정보를 `json`으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    http://192.249.18.138/api/partys/category/Eoeun
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":2,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"2022012496","Participant1":"2022012497","Participant2":null,"Participant3":null}]
    
    
    
