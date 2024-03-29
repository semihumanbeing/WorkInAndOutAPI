# WorkInAndOutApi
my very first kotlin multiplatform mobile application 

![image](https://github.com/semihumanbeing/WorkInAndOutApp/assets/99929191/298e0aef-48ba-4a5a-836b-b92d7d7657c7)


회사에 출퇴근 기록 관리를 위한 웹사이트가 있는데 <br>
출퇴근 버튼 누르는 것을 웹사이트에서만 할 수 있는 점이 불편해서 시도한 개인적인 앱 프로젝트<br>
자바 11 스프링 부트 API를 사용한다. (https://github.com/semihumanbeing/WorkInAndOutAPI)<br>

---
기능
- 로그인, 회원가입
  - 회원가입 시 이름, 출퇴근기록관리 사이트의 아이디랑 비번을 넣고 가입한다.
  - 암호화 되어 저장 되나 같은 계정 정보로 원격 로그인을 하기 때문에 복호화 할 수 있음
- 메인 화면
  - 로그인 시 기록관리 웹사이트의 출퇴근 버튼이 눌러져 있는지 조회한다.
  - 이미 출근, 퇴근 되어 있으면 해당 버튼은 비활성화 된다. (스케줄러로 매일 밤 12시 초기화)
- 출근, 퇴근 버튼
  - 자바 서버에서 나의 계정으로 로그인 한 다음 각종 팝업이 있으면 끄고 출근, 퇴근 버튼을 눌러준다.
  - 셀레니움을 사용해서 직접 요소를 클릭해 준다 .. 이는 최대 10초가 걸리므로 비동기 처리된다.
  - 또한 윈도우 서버를 사용하고, 셀레니움으로 항상 화면이 조회되도록 처리해야한다.
 
---

트러블슈팅
- 출근 버튼을 누르고 출근 처리가 되기까지 거의 10초의 시간이 소요되던 점
- 그러나 출근이 완료되었는지 여부를 확인해서 앱에 결과를 보여주어야 했음
- CompletableFuture를 사용해 나온 결과를 반환하게 하려고 했지만 너무 의존성이 높아지는 것 같았음
- Stateless하게 만들고싶어 우선 결과를 반환하고 메인화면에서 결과가 반환되면 db를 조회해 화면에 반영되게 하였음
