# sendQuiz

원하는 분야의 CS 면접 질문을 원하는 문제 수만큼 매일 아침마다 이메일로 전송해주는 서비스입니다.     

[Send Quiz 서비스 이용하기](https://send-quiz.store)

## Trouble Shooting

- [로컬 캐시를 적용하여 이메일로 전송하고자 하는 퀴즈 캐싱하기](https://320hwany.tistory.com/73)      

   - 퀴즈 정보는 업데이트가 빈번하게 일어나지 않고 업데이트가 일어나더라도 실시간으로 이루어지지 않아도 됩니다.   
   - 매번 DB에 접근하지 않고 ehcache 로컬 캐시를 이용해 성능을 개선해보았습니다.   

- [가상 메모리를 사용하여 서버의 부족한 RAM 해결하기](https://320hwany.tistory.com/74)  

   - AWS EC2를 프리티어로 사용하면 RAM은 1GB까지만 사용할 수 있습니다.
   - 부족한 RAM으로 인해 프로젝트 build시 발생한 문제를 가상 메모리를 사용하여 해결해보았습니다.

- [EC2에 Nginx 구성하여 도메인, Https 적용하기](https://320hwany.tistory.com/77)      

   - 같은 AWS EC2 서버에 프론트엔드, 백엔드 모두 배포하였습니다.    
   - Nginx를 이용하여 가비아에서 구매한 도메인, Https를 적용해보았습니다.    
