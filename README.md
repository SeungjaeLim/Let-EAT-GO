# Let EAT Go Server
    http://172.10.5.55
##  api - users

  ### 모든 계정 조회하기
   - HTTP Protocol
   
    /api/users/all
   - respond - 모든 User의 id와 email을 json으로 응답
   
    [{"id":"ID 1","email":"Email 1"},{"id":"ID 2","email":"Email 2"}]
   
   - request example
   
    /api/users/all
   
   
   - respond example
   
    [{"id":"11111","email":"cs496@kaist.ac.kr"},{"id":"22222","email":"cs330@kaist.ac.kr"}]
   
   ### 계정 등록
   - HTTP Protocol
   
    /api/users/:id/:email
   - respond - 기존에 계정이 없을 경우 등록 후 기존 계정 여부와 무관하게 id와 email을 json으로 응답
   
    [{"id":":id","email":":email"}]
   
   - request example
   
    /api/users/11111/cs496@kaist.ac.kr
   
   - respond example
   
    [{"id":"11111","email":"cs496@kaist.ac.kr"}]



##  api - mypage

  ### 호스팅 하고 있는 파티 조회하기
   - HTTP Protocol
   
    /api/mypage/host/:userid
   - respond - userid가 호스팅 하고 있는 파티들을 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    /api/mypage/host/11111
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":"22222","Participant2":"33333","Participant3":"44444"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"11111","Participant1":null,"Participant2":null,"Participant3":null}]
    
   ### 참가 하고 있는 파티 조회하기
   - HTTP Protocol
   
    /api/mypage/participate/:userid
   - respond - userid가 참가 하고 있는 파티 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    /api/mypage/participate/11111
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"22222","Participant1":"11111","Participant2":"33333","Participant3":"44444"},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"33333","Participant1":"11111","Participant2":null,"Participant3":null}]
  
##  api - partys

  ### 파티 만들기
   - HTTP Protocol
   
    /api/partys/create/:userid/:category/:name/:maxjoin/:time
    
   - respond - 생성된 파티의 정보를 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":1,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":null,"Participant2":null,"Participant3":null}]
    
   - request example
   
    /api/partys/create/11111/어은동/밀과보리/4/20220107183000
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":1,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":null,"Participant2":null,"Participant3":null}]
    
  ### 파티 참여하기
   - HTTP Protocol
   
    /api/partys/participate/:userid/:jobid
    
   - respond - 참여한 파티의 정보를 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - respond - 이미 참여하고 있는 파티인 경우
   
    already joined
    
   - respond - 파티의 정원이 꽉 찬 경우
   
    err-FULL PARTY
    
   - request example
   
    /api/partys/participate/22222/1017182317
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":2,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":"22222","Participant2":null,"Participant3":null}]
    
  ### 파티 삭제하기
   - HTTP Protocol
   
    /api/partys/delete/:userid/:jobid
    
   - respond - 삭제 성공 여부를 String으로 응답
   
    Delete Successed
    
   - respond - userid가 호스트가 아닌 경우
   
    Delete Failed
    
   - respond - jobid가 잘못된 경우
   
    Delete Failed
    
   - request example
   
    /api/partys/delete/11111/9017181057
   
   - respond example
   
    Delete Successed
    
  ### 파티 전체 조회
   - HTTP Protocol
   
    /api/partys/show/all
    
   - respond - 모든 파티의 정보를 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3},{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    /api/partys/show/all
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":"22222","Participant2":"33333","Participant3":"44444"},{"id":"6017181238","Category":"Gung","Name":"청년다방","Joined":2,"MAXjoin":4,"time":"2022-01-07T17:00:00.000Z","host":"22222","Participant1":"11111","Participant2":null,"Participant3":null},{"id":"7017181033","Category":"Eoeun","Name":"두발네발","Joined":2,"MAXjoin":4,"time":"2022-01-07T23:00:00.000Z","host":"22222","Participant1":"11111","Participant2":null,"Participant3":null},{"id":"7017181466","Category":"Eoeun","Name":"투다리","Joined":1,"MAXjoin":4,"time":"2022-01-07T22:00:00.000Z","host":"11111","Participant1":null,"Participant2":null,"Participant3":null}]
    
   ### 특정 파티 조회
   - HTTP Protocol
   
    /api/partys/show/:jobid
    
   - respond - 해당 파티의 정보를 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    /api/partys/show/1017182317
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":4,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":"22222","Participant2":"33333","Participant3":"44444"}]
    
   ### 카테고리 조희
   - HTTP Protocol
   
    /api/partys/category/:category
    
   - respond - 해당 카테고리에  파티의 정보를 json으로 응답
   
    [{"id":"PARTY ID","Category":"CATEGORY","Name":"NAME","Joined":MEMBER,"MAXjoin":MAXJOIN,"time":"YEAR-MONTH-DAYTHOUR:MIN:SEC.MILLISECZ","host":"USER ID","Participant1":MEMBER1,"Participant2":MEMBER2,"Participant3":MEMBER3}]
    
   - request example
   
    /api/partys/category/Eoeun
   
   - respond example
   
    [{"id":"1017182317","Category":"Eoeun","Name":"밀과보리","Joined":2,"MAXjoin":4,"time":"2022-01-07T18:30:00.000Z","host":"11111","Participant1":"22222","Participant2":null,"Participant3":null}]
    
    
    
