# CloakBlog
프라이빗한 블로깅 플랫폼

- 위키 형식의 블로그에서 이용하고자 하는 그룹원들의 테마에 맞는 프라이빗한 블로그로 방향성 수정
  - 그룹원들의 테마 란 ?
    - 예) 여행 그룹이라면 그룹 인원들만 공유 가능한 지역과 사진, 영상을 남기는 블로그
    - 예) 스터디 그룹이라면 그룹 인원들만 공유 가능한 ToDo List 형식으로 우선 순위와 그날 그날의 학습 및 코드 기록
- 추가 하고싶은 기능
  - 가능할지 모르겠지만, 카카오톡 개인 채팅방 처럼 각각의 테마 블로그는 소수의 인원 만 사용 가능
    - 만약 A가 스터디 블로그를 선택한다면 남은 스터디 멤버 B, C, D에게 특수한 코드를 제공.
    - 코드를 받은(초대받은) 멤버들은 회원가입시 코드를 입력하면, 로그인 시 A가 선택한 스터디 블로그로 자동 입장
  - 가능할 경우, 고려해야 될 사항
    - 블로그 템플릿을 정적으로 만들어서 제공할 것인지, 동적으로 제공할 것인지. 동적이라면 어떻게 제공할 것인지 고민
    - 선택한 인원이 프라이빗한 블로그를 시작하게 된다면 랜덤한 값을 제공 받고, 
    이를 기반으로 공유(초대)하여 초대받은 인원이 회원가입시 기입한다면 role을 기준으로 프라이빗한 공간을 공유.. 가 가능한지,,
  - 그 외 
    - 이미지, 영상 데이터를 업로드할 경우 데이터 최적화
    - 회원 세션 관리
    - 최소한의 HTTP endpoint
  - 기술적으로 특이사항이 없다면 버전은 바꾸지 않을 예정

## 기술스택
- Java: openjdk 17.0.12 LTS
- Spring Boot 3.3.2
- JPA
- H2 (임시방편)
- thymeleaf

## [ERD](https://www.erdcloud.com/d/dRg8giLK3qJa8h3Rf)
- Member
  - 회원 정보 테이블
- Blog
  - 블로그 정보 테이블
  - 회원은 1개의 블로그만 개설 가능
  - 초대 받은 회원은 블로그를 개설하거나 개설된 다른 블로그에 초대 불가능
- BlogMember
  - 블로그에 초대된 회원 정보
- Post
  - 개설된 블로그의 게시글 정보
- Category
  - 게시글 카테고리
- PostCategory_JoinTable
  - 게시글과 카테고리의 ManyToMany 관계를 Join Table 생성
- Comment
  - 게시글의 댓글 정보

## 과제
- 추가 기능 정의
  - ToDo List
  - 회원 관련
    - 회원 가입
    - 로그인
    - 세션 처리
  - 인증과 권한
  - Map API
  - Image, Video
    - 이미지 관련
      - 단일 파일이 아닌 다수 파일 저장
        - 다수의 이미지일 경우 대표 이미지 선정
  - 게시판 관련
    - 카테고리 테이블에 값이 없을 경우 에러 발생. 유연한 처리 필요
  - Content Write Editor
  - 실시간 소통 관련 (Websocket)
    - session 기반으로 특정 사용자 식별
    - DB 연결을 통한 이전 메시지 제공
  - 카톡으로 초대 알람(ex. 애니팡)
  - [초대 코드는 난수로](https://safety.google/intl/ko/authentication/)
  - 프라이빗~
- 배포
- ERP
  - 권한
    - 통합 관리자(운영자): admin
    - 블로그 호스팅(블로그 운영자): Manager
    - 블로그 멤버(블로그 회원): Member
  - 회원 일시정지 등 관리

## 고민
- 블로그 생성 관련
  - 회원가입 후 블로그 호스팅 시 블로그명 작성(validation check: en, number)
  - 블로그명을 엔드포인트로 설정. 하지만, 접근을 어떻게 해야되는지 고민..
    - 블로그 자체가 프라이빗하기 때문에 로그인이 필수. 따라서 로그인한 사용자 기준 블로그 명을 주입
      (예시: 로그인 후 블로그 접근("/blog/" + user.blogName), 포스트 접근("/blog/" + user.blogName + "/post/"))
      `예시 케이스 : velog`
  - 초대 코드의 경우 블로그명을 해싱해야 될지 고민..
    - 이유는 블로그 명으로 할 경우, 누구나 블로그 멤버로 가입이 가능하여 프라이빗하지 못함
  - 위 문제를 해결하며, 필요에 따라 DB Migration
- 게시글 생성 관련
  - 카테고리 여부에 따라 에러 발생
  - 카테고리의 경우, 기존에 생성된 카테고리가 없을 경우 카테고리 생성을 우선으로 처리
 

## 프로젝트 대면 회의
- 일정 09/03 (화)
- 프로젝트 현황 브리핑
- Monolithic Architecture vs Micro Service Architecture
- Convention
  - [Java](https://github.com/JunHoPark93/google-java-styleguide)
    - Google Java Style Guide
    - Oracle의 경우 마지막 업데이트가 99년도로, 현 자바 버전과의 스타일이 맞지 않을것 같은 이유로 구글 선택
  - [GitHub Commit](https://overcome-the-limits.tistory.com/entry/%ED%98%91%EC%97%85-%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B8%B0%EB%B3%B8%EC%A0%81%EC%9D%B8-git-%EC%BB%A4%EB%B0%8B%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0)
  - GitHub Branch
    - GitHub Projects -> Kanban Board -> Issues <br> 
    (feature/{Issues number}login)
- Database
  - MySQL vs PostgreSQL
- 프로젝트 시작
  - 기능 재정의
  - 작업 우선 순위 정하기
  - 작업 별 기간 정하기
    - 전체 기간은 유지보수 꾸준히
    - 기능 구현은 명확한 기간 정하기
- 기능 추가 여부 검토
  - 기존 기능 외 추가 기능 여부
- 프론트 기술 스택
  - TypeScript
    - React
    - Angular
    - Vue
