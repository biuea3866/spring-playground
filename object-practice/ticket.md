![관계도1.png](src/img/%EA%B4%80%EA%B3%84%EB%8F%841.png)

## Theater의 역할

소극장은 관람객의 가방을 열어 초대장이 들어있는지 확인한다. \
가방 안에 초대장이 들어있으면 판매원은 매표소에 보관돼 있는 티켓을 관람객의 가방으로 옮긴다. \
가방 안에 초대장이 없으면 관람객의 가방에서 티켓 금액만큼의 현금을 꺼내 매표소에 적립한 후 매표소에 보관돼 있는 티켓을 관람객의 가방으로 옮긴다.

### 문제점

* 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재이다.

[관람객]
* 소극장이라는 제 3자가 초대장을 확인하기 위해 가방을 열어본다. \
-> 누군가가 허락없이 나의 가방을 열어보는 행위이다.

[판매원]
* 소극장이 판매원의 허락 없이 매표소에 보관중인 티켓과 현금을 마음대로 접근한다.
* 티켓을 꺼내 관람객의 가방에 집어넣고 받은 돈을 매표소에 적립하는 일을 한다.


### 문제점에 대한 결론

이해 가능한 코드란 동작이 우리의 예상을 크게 벗어나지 않음을 의미한다. \
하지만 예제는 예상을 벗어나며, 주체의 대상인 매표소, 관람객, 판매원의 동작없이 소극장이 모든 것을 수행한다. \
이는 소극장이 관람객과 판매원을 통제하고 있음을 의미한다. 

더불어 이렇게 소극장이 모든 것을 통제할 경우 매표소, 관람객, 판매원이 변경될 경우 소극장도 함께 변경이 되는 문제가 발생할 수 있다. \
가령 현금이 아닌 신용카드를 이용한 결제라던지, 매표소 외부에서 판매를 할 경우 코드가 깨질 수 있다. \
이는 객체 간의 의존성이 생겼기 때문이며 의존성은 변경에 대한 영향을 암시한다.

## 설계 개선

### 관람객과 판매원이 주체가 되도록 변경
소극장이 관객과 판매원에 대해 세세한 부분까지 컨트롤하지 못하게 한다. \
소극장이 원하는 바는 관람객이 소극장에 입장하는 것이니 이에 맞게 변경이 필요하다.

![관계도2.png](src/img/%EA%B4%80%EA%B3%84%EB%8F%842.png)

관람객과 판매원이 주체가 되도록 변경하여 내부의 구현을 외부에 노출하지않고, 자신의 문제를 스스로 책임지고 해결한다. \
이는 자율적인 존재가 됐음을 의미하며 변경의 용이성 관점에서 격리가 되었으며, 내부만 변경하면 됨을 의미한다. \