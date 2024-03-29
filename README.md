# 애완동물 관련 시설 API

이 프로젝트는 애완동물 관련 시설을 조회하고 리뷰를 등록할 수 있는 API를 제공합니다. Spring Boot 3.0 및 MariaDB 10.0을 기반으로 개발되었으며, AWS EC2와 RDS를 사용하여 배포되었습니다. CI/CD는 GitHub Actions와 Docker를 활용하고 있습니다.

## 개발 환경

- Java 17
- Spring Boot 3.0
- MariaDB 10.0
- AWS EC2 및 RDS
- Gradle
- Swagger

## ERD

![pet_db](https://github.com/sky7214sky72/pet/assets/45224987/c338b724-e258-4f75-addb-440175e1204b)


## 기능

- 애완동물 시설 목록 조회
- 애완동물 시설 상세 조회
- 애완동물 시설 등록
- 애완동물 시설 수정
- 애완동물 시설 삭제
- 리뷰 조회
- 리뷰 등록
- 리뷰 수정
- 리뷰 삭제
- 로그인 (JWT 방식, 핸드폰 번호를 ID로 사용, 로그인으로 발급받은 accessToken 헤더에 Authorization : bearer 토큰값으로 하면 인증됨)
- 유저 목록 조회

## API 문서

API 문서는 Swagger를 통해 자동 생성되고 있습니다. 
cws070993@gmail.com을 통해 링크를 요청해주시면 확인 후 전송해드리겠습니다.


## 로컬 환경 세팅

1. 이 저장소를 클론
2. 제 이메일을 통해 docker-compose 파일을 요청해 주시면 필요한 application.yml 파일과 docker-compose 파일을 전송해드리겠습니다.



## 추가로 개발할 일

- 애완동물 시설에 대한 평점 기능 추가
- 애완동물 시설 검색 기능 개발
- 애완동물 시설 사진 업로드 기능 추가
- 유저 정보 수정 기능 개발
- 애완동물 시설 분류 기능 개발
- 유저 채팅 기능 추가

[CodePen - 애완동물 시설 조회](https://codepen.io/rahgdihv-the-sans/pen/yLRdqqY)
