## 아키텍처와 컨벤션

## Presentation Layer
프레젠테이션 레이어는 사용자와의 상호작용을 담당한다.

Application Layer의 제공한 결과물을 가공하여 사용자에게 전달하거나, 다른 Application Layer에 전달하여 처리할 수 있다.

### admin-api
관리자 페이지와 소통하는 API 서버이다.

e.g) XXXAdminApiController

### api

일반 유저와 소통하는 API 서버이다.

e.g) XXXApiController

### batch

주기적으로 실행되는 작업을 처리하는 서버이다.

e.g) XXXBatch

### Worker

관심사에 대한 메세지를 구독하고, 이를 처리하는 서버이다.

e.g) XXXWorker

### Socket

실시간으로 유저와 소통하는 서버이다.

## application layer
도메인 레이어의 어그리거트와 레포지토리를 호출하여 비즈니스 로직을 수행한다.

여러 어그리거트를 호출하여 서로 상호작용을 할 수 있다.

트랜잭션을 관리하는 단위이다.

e.g) XXXFacade

## domain layer
도메인 모델에 비즈니스 규칙, 정보를 정의하는 계층이다.

e.g) XXXAggregate

## infrastructure layer
외부 시스템(스토리지, 메일, 외부 서버 등)과의 연결을 담당한다.
