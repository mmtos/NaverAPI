# Notification Service

:star2: [`Notification Service MSA 분리하기`](https://github.com/t0e8r1r4y/-MSA-Notification_Service/blob/main/README.md) 프로젝트의 기본 뼈대입니다.

:small_orange_diamond: 키술키워드 : Spring, [cache](https://github.com/t0e8r1r4y/blogContents/blob/main/Cache/Cache.md), [EDP](https://github.com/t0e8r1r4y/blogContents/blob/main/ArchitecturalPatterns/EDA.md)

✅ 프로젝트 개요

- Notification 서비스를 회사 장애 알림 시스템 형태로 구현하고 싶었는데, 기회가 되지 않아 사이드 프로젝트로 만들었습니다.
- 네이버 블로그를 운영함에 내가 관심있는 키워드의 최신글을 주기적으로 수신 받는 Notification 서비스입니다. 사용자는 구글 계정으로 로그인이 가능하며, 키워드를 등록 할 수 있습니다. 어플리케이션은 등록된 키워드를 기반으로 네이버 검색 api에 해당 키워드의 아티클을 조회합니다. 그리고 조회 결과를 사용자가 로그인한 계정으로 발송합니다.
- 아티클의 종류는 blog, cafe, news 게시 목록을 제공하며, api 응답 결과가 있다면 가장 최신 5개의 글을 추려서 메일로 제공합니다.

✅ 주요 기능

- User
    - 사용자는 Notification Service를 이용하는 사용자이다.
- Keyword
    - 키워드는 사용자가 조회를 원하는 단어를 의미한다.
    - 사용자는 키워드를 등록할 수 있다. 키워드는 최소 1개부터 최대 10개까지 입력 할 수 있다.
    - 키워드는 ‘ , ’(콤마) 로 구분하여 등록 할 수 있다.
    - 키워드를 새로 입력하면 기존 키워드는 삭제가 되며 새로운 키워드로 업데이트 된다.
- Article
    - 아티클은 네이버 API를 통해 조회한 Blog, Cafe, News 게시글에 대한 정보이다.
    - 어플리케이션 스케쥴러는 등록된 키워드를 조회하여 주기적으로 네이버 검색 API를 조회하여 결과를 DB에 저장한다.
- Notification
    - 알림은 아티클 도메인의 하위 종속 도메인으로 어플리케이션이 사용자에게 아티클을 발송하는 행위와 관련된다.
    - 매일 일정 시간이 되면 사용자는 등록한 키워드의 아티클을 최신순으로 5개 조회하여 그 결과를 이메일로 받을 수 있다. 이 행위를 알림이라고 한다.

✅ 어플리케이션 구조 설계

- User와 Keyword 관련 로직은 `REST API`로 제공한다.
- Spring Scheduler를 사용하여 주기적으로 Naver 검색 Api를 조회한다. 검색 Api 조회 결과는 DB에 저장한다.
- 알림을 발송하는 로직은 `EDP를 적용`하여 구현한다. 알림을 이벤트 단위로 생성하고 발행하는 Publisher와 이벤트를 수신하여 이메일을 발송하는 Consumer( EventWorker )로 구분한다.
    
    ![EDP%E1%84%80%E1%85%AE%E1%84%8C%E1%85%A9 drawio_(2)](https://user-images.githubusercontent.com/91730236/195518963-04d1744e-bf0e-41ac-8da1-6b3d19de92c7.png)
    

✅ 데이터베이스 설계 전략

- ERD는 아래와 같다.
    
    ![%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%90%E1%85%B5%E1%84%90%E1%85%B5_%E1%84%89%E1%85%A5%E1%86%AF%E1%84%80%E1%85%A8 drawio](https://user-images.githubusercontent.com/91730236/195519021-e4e32907-a953-480e-8d41-d3680d9ea4d9.png)
    

✅ 배포

- 해당 프로젝트에서는 `배포는 고려하지 않음`.
- 단 아래 2가지 방법을 사용해 본 경험이 있기에, 안정성 검증 단계에 따라 배포와 운영 환경을 달리한다.
    - AWS Elastic BeansTalk + CodeDeploy → 테스트 및 초기 서비스 검증 단계에서 사용
    - AWS EC2 + RDS + TravisCI + CodePipeLine + Nginx(리버스 프록시) → 운영

✅ Technical Issue ( 프로젝트를 진행하며 고민한 사항과 주안점 )

- 로그인 기능 구현에 있어 `Google OAuth` 사용
    - 유저 입장에서는 Email/Password 방식의 로그인을 선호하지 않는 추세
- `Validation`을 사용하여 키워드 입력에 대한 검증 로직 추가
- 읽기 성능 향상을 목적으로 캐시적용
    - API에서 제공하는 응답데이터가 정보를 상세하게 제공하지 `않음` ( 업데이트 일시, 삭제유무 등등 ), 또한 요청 갯수 제한
    - 서비스에서 저장하는 데이터와 api 서버의 데이터가 필연적으로 불일치
    - 데이터 중복이나 변경에 대해서 `스냅샷` 기반으로 데이터를 저장하는 방식과 HashSet으로 중복 데이터를 제거하는 방법을 고려하였고, 후자를 선택함
    - 중복을 제거하는 것이 별도의 로직으로 저장된 링크를 체크하여 삭제 flag를 걸어주는 것이 바람직한 방법이겠으나, api 요청 갯수 제한으로 확인이 불가능 할 수 있음.
    - api 요청 결과와 기존 DB 저장 로직에 대해서 중복을 제거하는 과정에서 DB 조회가 빈번하게 발생함. 캐시(caffein cache)를 적용하여 성능 향상
    - Scale Out을 고려할 규모가 아니라고 판단하기에 `Local Cache` 만 적용
    - 캐시를 적용하지 않는다면 `파티셔닝 테이블`을 적용하는 것도 조회 성능을 향상 시킬 수 있는 대안임
- `처리 지연의 방지`를 위한 `EDP`와 `Multi-thread 기반 병렬 처리`
    - 알림을 생성하고 발송하는 모든 단계를 한 흐름으로 처리할 경우 `처리지연`이 발생함 ( DB 조회 및 SMTP로 메일 발송 )
    - 따라서 EDP를 적용하여 알림 생성과 발송을 구분하고, 비동기 처리하여 처리지연을 방지한다.
    - SMTP 서버로 메일을 발송하는 과정에서 6~10초 가량의 처리 지연이 발생함. 이를 해결하기 위해서 `multi-thread` 를 할당하고 비동기 처리하여 지연을 방지한다.
    - EDP를 EDA로 확장하면 MSA 구성이 용이하여 확장성 있는 구조로 차후 리팩토링 시 분리가 용이함.
- 배포 환경 고려
    - 스케쥴러가 주기적으로 동작하는 어플리케이션이기에 Serverless 보다는 EC2를 사용하는 것이 유리하다고 판단함. 단 개발 초기 서비스 검증 단계에서 빠른 수정과 배포 목적으로 Elastic BeansTalk를 사용함.

✅ 어플리케이션 UI

- 어플리케이션 구동
- [localhost:8080/user](http://localhost:8080/user) 접속
    
    <img width="367" alt="%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-10-12_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_1 44 17" src="https://user-images.githubusercontent.com/91730236/195519175-8fd1c49b-20a0-4f82-963e-ed31cbcc7e46.png">
    
- 구글 로그인
    
    <img width="370" alt="%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-10-12_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_1 45 14" src="https://user-images.githubusercontent.com/91730236/195519254-22122cf3-f1de-40d5-ba15-bbae618209a7.png">

    
- 키워드 등록
    
    !<img width="401" alt="%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-10-12_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_1 45 44" src="https://user-images.githubusercontent.com/91730236/195519308-40f59efe-6b0e-4bdf-b65b-4aaaa7d31778.png">
    
- 이메일 확인
    
    <img width="1385" alt="%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-10-12_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_1 46 43" src="https://user-images.githubusercontent.com/91730236/195519355-4957a3e2-1d7a-44cb-be25-27598749bd83.png">
