# computer-architecture

<br>

Simple Calculator, Single-cycle, PipeLine

<br>

## pipeLine이란?

프로세서에서 성능을 높이기 위해 명령어 처리 과정으로 명령어 처리를 여러 단계로 나누어 단계별로 동시에 수행하여 병렬화 시키는 것을 말합니다. 즉, 한 사이클에 한 여러 명령어가 중첩되어 실행되는 구조를 말합니다. 
이렇게 진행하면 명령어 처리 시간을 획기적으로 단축할 수 있습니다. 

![image](https://github.com/YGwan/computer-architecture/assets/50222603/385605f2-0d57-4660-9c71-f8579ae8f18d)
![image](https://github.com/YGwan/computer-architecture/assets/50222603/e692d98d-3b79-4b77-97bd-78fc49fea053)

위와 같이 이러한 형식으로 해당 작업들을 병렬적으로 수행하는 것이 파이프라인의 가장 큰 특징이라고 할 수 있습니다. 이렇게 진행되면 단계별로 독립적으로 수행되기 때문에, 불필요한 쉬는 단계가 존재하지 않습니다. 
현재는 이해하기 편하게 파이프라인을 설명할때 가장 보편적으로 사용되는 예시 이미지를 가져와 해당 단계를 표현했지만, 실제로 파이프라인에서는 이 단계가 Fetch, Decode, Execution, Memory Access, WriteBack 단계로 표시됩니다.

<br>

## * Single-Cycle, Multi-Cycle, PipeLine 비교

<br>

싱글사이클과 멀티사이클에 경우, 한 사이클에 한 명령어가 실행이 됩니다. 물론 이때 싱글사이클에 경우 모든 명령어가 동일한 실행 단계를 거치고 멀티사이클의 경우 명령어의 종류에 따라 실행 단계가 다르다는 차이점이 있지만 결론적으로 한번에 하나의 명령어만 실행되고, 하나의 명령어가 끝나면 다른 명령어가 실행된다는 공통점이 있습니다. 
하지만 파이프라인의 경우 한 사이클에 하나의 명령어만 실행되는 것이 아닌, 여러개의 명령어가 병렬적으로 동시에 실행됩니다.

<br>

<p align="center">
<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/9f4ff433-6d96-46eb-b5e5-e04bace01fc3" width = "1000" height = "800"/>
</p>

따라서 single cycle, multi cycle, pipeline의 실행 과정을 간단히 표현하자면 위와 같이 표현할 수 있습니다. 해당 과정의 발전 순서는 single cycle -> multi cycle -> pipeline 순으로 진행됩니다. 
처음에는 한 사이클에 명령어 하나가 실행되게끔 하는 형태인 싱글 사이클 형태를 가졌다가 이러한 과정에서 명령어에 따라 불필요한 단계를 실행시켜야 하는 비효율이 발생해 한 사이클에 한 명령어가 아닌, 한 사이클에 한 단계씩 실행시켜 명령어에 따라 실행하는 단계를 다르게 만드는 멀티 사이클 형태로 발전합니다. 
이렇게 하다보니 하나의 단계를 실행할때, 다른 단계는 쉬고 있는 비효율이 발생합니다. 단계마다 쓰는 하드웨어 부품은 다르기 때문에 굳이 이것을 순차적으로 실행할 필요가 없었기 때문입니다. 
따라서 이를 병렬적으로 실행할 수 있는 파이프라인의 개념이 등장해 명령어를 한번에 하나씩이 아닌 여러개를 동시에 실행 할 수 있게 되었습니다.

<br>

## * Pipeline Hazard

이렇게 병렬적으로 동시에 데이터를 처리하다보니, 생기는 문제가 있습니다. 이것을 “Hazard” 라고 부르는데, 이는 결국 파이프라인 프로세서에서 명령어 의존성을 발생시킬 수 있는 문제를 의미합니다. 명령어 의존성은 크게 데이터, 컨트롤, 자원 이렇게 3가지로 나눠지기 때문에 해저드의 종류 또한 3가지로 나눠집니다. 

<br>

## 1.  구조적 해저드 (Structural Hazard) 

<br>

<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/c0f635aa-ec07-463f-a4b4-24792436263d" width="500" height="500"/>

<br>

“구조적 해저드” 는 파이프라인에서 실행 중인 2개 이상의 명령어가 동일한 하드웨어 자원을 동시에 요구할 경우 파이프라인을 멈춰야 하는 상황을 말합니다. 하드웨어는 동시에 여러 명령 수행을 지원하지 않기 때문에 자원 충돌 현상이 나타날 경우 문제가 발생하기 때문입니다. 
예를 들어 위의 사진과 같이 fetch단계와 memory access 단계에서 메모리 자원에 동시에 접근해야 할 경우 같은 상황같은 경우를 말합니다.
이를 해결하기 위해서는 필요한 하드웨어 / 리소스 추가하거나 데이터 / 명령 메모리를 구분하거나(하버드 아키텍처), 해당 기능을 사용할 수 있을때까지 지연(Stall, Bubble)하는 방법 등이 있습니다.

<br>

## 2.  데이터 해저드 (Data Hazard)

<br>

<img src="https://github.com/YGwan/computer-architecture/assets/50222603/46eaf839-4b10-4d6f-9248-20acf5cd89c6" width="500" height="500"/>

<br>

“데이터 해저드” 는 연산할 데이터가 준비되지 않아 파이프라인을 멈춰야 하는 모든 상황이나 조건을 말합니다. 주로 선행 명령어와 후행 명령어가 사용하는 데이터 간의 종속성 문제로 발생하는데 이는 쉽게 말해 선행 명령어와 후행 명령어가 쓰는 레지스터가 같을때 주로 발생합니다. 
우리는 이것을 Data Dependence라고 합니다. 예를 들어, 선행 명령어가 레지스터의 값을 갱신하는 명령어인데, 이때 값을 갱신하는 레지스터를 뒤에 오는 명령어가 연산에 사용한다면 문제가 발생합니다. 
왜냐하면 선행 명령어가 값을 갱신하는 단계는 Writeback 단계인데, 후행 명령어가 레지스터의 값을 읽어오는 단계는 Decode 단계이기 때문에, 해당 명령어 사이의 거리가 짧다면(3이하)라면 선행 명령어가 writeBack 단계 이전에 후행 명령어가 Decode단계를 거치기 때문에 해당 겹치는 레지스터를 쓰면 엉뚱한 결과가 나오기 때문입니다. 
두 명령어가 공통으로 사용하는 데이터 관계는 RAR, RAW, WAR, WAW 총 4가지인데, 이 중 RAR관계는 프로세스의 상태를 변화시키지 않기 때문에 문제가 되지 않습니다. 나머지의 경우 위의 사진과 추가로 보충 설명하자면, 

RAW - 이전 명령이 저장한 연산 결과를 후속 명령어 읽으려고 할때를 의미합니다.
WAR - 이전 명령이 값을 읽기 전에 후속 명령이 값을 쓰는 경우를 의미합니다.
WAW - 이전 명령이 값을 쓰기 전에 후속 명령이 값을 쓰는 경우를 의미합니다.

이에 대한 해결방안은 Data Dependence가 발생했을때, 선행 명령어가 WriteBack단계까지 도달할때까지 다음 명령어를 대기(지연)시키는 stalling 방법이나 레지스터 파일에 반영되기 전에 각 단계에서 연산된 결과값을 미리 받아오는 Forwarding 방법, 
컴파일러 수준에서 데이터 해저드를 발견하고 명령어 재정렬 및 필요시 지연시키도록 nop명령어를 사이에 추가하는 Delay load, 원래 레지스터가 아닌 다른 레지스터를 할당해 사용하는 방법 등이 있습니다. 
여기서 중요한 점은, 왜 Data Hazard가 발생하냐 입니다. 레지스터를 각각 다르게 사용하면 되지 않나 라는 의문이 들 수도 있기 때문입니다. 명령어를 수행할때, 어떤 레지스터를 사용할지는 사람이 직접 정하는 것이 아닌 컴파일러가 정해줍니다. 
레지스터의 개수 또한 정해져있기 때문에 최대한 이 부분을 피할려고 해도 코드가 길어진다면 어쩔 수 없이 발생할 수 밖에 없기 때문에 레지스터를 각각 다르게 사용하는 것은 사실상 불가능합니다. 따라서 데이터 해저드가 발생할 수 밖에 없습니다.

<br>

## 3.  제어 해저드 (Control Hazard)

<br>

<img src="https://github.com/YGwan/computer-architecture/assets/50222603/01fc1a6e-1429-4c42-9107-fdae78e4c3b4" width="500" height="500"/>

<br>

제어 해저드란 분기 명령어에 의해서 발생, 분기가 결정된 시점에 수행되지 않을 명령이 파이프라인에 존재하는 상황을 말합니다. 일반적으로 pc값은 IF단계에서 변합니다. 
이때, 분기명령어의 경우 Fetch단계에서 이 명령어가 분기 명령어인지, 분기명령어의 경우 실행해야하는지에 대해서 Fetch단계에서 알 수 없다는 문제가 발생합니다. 
만약 분기명령어를 실행했는데, 결론적으로 분기명령어를 실행해야하는 상황이라면 아무런 문제가 되지 않습니다. 하지만 분기명령어를 실행했는데 결론적으로 분기명령어를 실행하면 안되는 상황이라면 그전에 fetch된 명령어들을 실행시킨다면 문제가 발생합니다. 
단순히 무의미해질 수도 있지만 그 명령어가 특정 레지스터나 값을 갱신시킨다면 저희가 원하는 결과값을 얻을 수 없을 수도 있습니다. 따라서 분기 결과에 따라 사용되거나 버리는 작업이 필요합니다. 
이를 해결하기 위해서는 단순히 결과값이 나올때까지 다음 연산을 지연시키는 Stalling,  메모리에 기존 분기문의 결과를 기록하여 분기문 발견 시 확인해 예측하는 Branch Target buffer를 사용하는 방법, 
분기문 수행 전에 분기문을 예측하여 사용률 낭비를 줄이는 Branch Prediction, 컴파일러가 분기문을 발견 후, Delay-slot에 NOP이나 분기문과 관련 없이 수행되는 명령을 추가하도록 프로그램 순서를 재배치하는 Delay Branch 등이 있습니다. 
이러한 현상들 때문에 파이프라인에서 명령어 처리 단계를 무작정 많이 둔다고 해서 성능에 무조건 적으로 효과적이라 할 수 없는 것입니다. 그렇다면 이제부터 위에서 간단히 설명한 데이터 해저드, 컨트롤 해저드에 대한 해결방법을 좀 더 자세히 설명해보도록 하겠습니다.

<br>

## * Data Dependence 해결 방법

<br>

Data Dependence 해결방법은 크게 5가지가 있습니다.

  - Data Stalling / Scoreboarding
  - Data Forwarding
  - 소프트웨어 레벨에서 종속성 감지 및 제거
  - 필요한 값을 예측하고 “추측적으로” 실행한 후 확인하기
  - Fine - grained multithreading

이중 제가 구현한 방법은 Data Forwarding 방법입니다.

<br>

## Data Forwarding

<br>

<p align="center">
<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/78464396-0b47-4556-bf02-57c491d10f0b" width = "1000" height = "500"/>
</p>

<br>

<br>

Data Forwarding이란 결국 Data Hazard가 발생하는 이유는 레지스터 값의 갱신이 Write Back 단계에서 이루어지기 때문에 그 전에 데이터 의존성이 발생하면 갱신이 반영되지 않기 때문에 생기는 문제입니다. 
따라서 디코드 단계에서 명령어에서 필요한 데이터 값을 확인하여, 의존성이 발생하면 파이프라인 후반 단계에서 직접 데이터를 가져와 Stalling을 하는 대신 값의 반영을 바로바로 하게끔 해주는 방법이 Data Forwarding입니다. 
일단 기본적으로 Decode를 기준으로 거리에 따라 필요한 값들을 보내주고, Forwarding Unit이 명령어들 사이에 의존성 확인을 해주고 의존성 여부에 따라 ForwardA, ForwardB의 Control Signal값이 변경돼 ALU의 Input값이 선택되는 흐름으로 진행됩니다.

<br>

## Data Forwarding 구현 방법

![image](https://github.com/YGwan/computer-architecture/assets/50222603/06037046-70fb-4432-8d07-3ee74e3bbf42)

<br>

데이터 포워딩을 설명하기 전에, ForwardingUnit부터 설명하도록 하겠습니다. ForwardUnit은 Data Dependence를 확인하는 Unit입니다. 
여기서 중요한 점은 EXE/MEM단계에서의 Data Dependence와 MEM/WB단계에서 동시에 Data Dependence가 발생했을경우, EXE/MEM단계에서의 Data Dependence가 우선되어야 한다는 점입니다. 
왜냐하면 EXE/MEM단계가 더 최근의 명령어이기 때문입니다. 위의 사진이 그러한 작업을 한 것입니다. ForwardingUnit을 통해 데이터 의존성을 확인해 Control Signal을 얻고 그 값을 가지고 forwarding을 해 데이터 의존성이 없으면 id_exe.readData1값을, 
의존성이 1이면 exe_mem.finalAluResult값을, 의존성이 3이면 memToRegValue값을 선택합니다. 여기서 필요한 가지수는 3개이기 때문에 일반 mux가 아닌 forwardMux를 main에 따로 만들어서 해당 ControlSignal(여기서는 Int형 값입니다.)에 따라 값을 선택하는 MUX를 만들었습니다. 

<br>

![image](https://github.com/YGwan/computer-architecture/assets/50222603/d4009f57-c789-4cb4-b72b-2ec5f2c74de1)

<br>

그 후, 아까 위에서 말한 alu에 들어가기 전에 해당 명령어의 종류에 따라 어떤 데이터 값이 들어갈지 결정해주는 MUX를 추가합니다. 이렇게해야 제가 원하는 데이터패스대로 값이 전달되고 원하는 결과값을 얻을 수 있기 때문입니다. 
이렇게 저같은 경우 DataDependence를 데이터 포워딩으로 처리했습니다. 포워딩으로 처리한 이유는 소프트웨어적으로 구현할 수 있는 것이 Stalling, Forwarding이라고 생각했는데, 이 중 포워딩이 더 효율적인 방법이라고 생각했기 때문입니다. 
Stalling은 Forwarding보다 불필요한 중지가 더 많기 때문입니다.
 
<br>

## Control Dependence 해결 방법

<br>

Control Dependence 해결방법은 크게 5가지가 있습니다.

  - wait(Stalling)
  - Guess(Branch Prediction)
  - Delayed Branching(Branch Delay Slot)
  - Fine-grained multithreading
  - Predicated execution
  - Multipath execution


이중 제가 구현한건 크게 Stalling과 Branch Prediction을 통한 방법입니다.

<br>

## Stalling

pc값은 기본적으로 pc+4로 갱신됩니다. 하지만 몇몇 명령어의 경우 pc값을 pc+4가 아닌 특정 값으로 바꾸는 명령어가 존재합니다. 이때 문제가 되는 것이 control Dependence 입니다. 
예를 들어 JUMP나 JAL의 경우 Decode단계에서 얻은 값으로 pc값이 갱신됩니다. 이때 해당 명령어가 Decode단계로 갈 동안 pc+4의 명령어가 Fetch단계로 들어오게 되고 이 명령어는 원래 실행이 되면 안되는 것인데 실행이 되기 때문에 문제가 발생하는 것입니다. 
하지만 이 경우에는 MIPS가 자체적으로 이러한 명령어 뒤에 NOP명령어를 추가하기 때문에 문제가 발생하지 않습니다. 하지만 BNE나 BEQ 같은 경우 Execution 단계에서 얻은 값을 바탕으로 pc값이 결정됩니다. 
따라서 이 경우에는 BNE, BEQ 명령어 다음에 NOP이 있다고 해도 그 다음 명령어까지 Fetch에 들어오므로 추가적인 문제가 발생합니다. 따라서 이 경우 Stalling을 통해 Fetch를 막아 문제를 해결합니다.

<br>

## Stalling 구현 방법

<br>

![image](https://github.com/YGwan/computer-architecture/assets/50222603/f9c5daa0-f552-4de8-b92c-27df03262a30)

<br>

이러한 문제를 해결하기 위한 첫번째 방법이 Stalling 입니다. 생각해보면 BNE, BEQ의 분기 여부는 execution단계에서 나오고 이때 pc값을 갱신할지 말지가 결정됩니다. 
그렇다면 이 상태에서 Fetch 단계는 BNE, BEQ 명령어의 pc값을 기준으로 pc+8된 명령어가 들어오고, Decode 단계에는 pc+4인 명령어가 들어옵니다. 
Decode 단계에 들어오는 pc+4의 명령어는 위와 동일하게 NOP이기 때문에 상관을 안해도 됩니다. 하지만 Fetch단계에서 pc+8은 어떤 명령어일지 모르기때문에 신경을 써야 합니다. 
물론 분기 여부가 거짓이라면, 아무런 문제가 되지 않습니다. 하지만 분기 여부가 참이라면 쓸데없는 명령어가 들어오기 때문에 문제가 됩니다. 
따라서 저같은 경우 Decode 단계에서 BNE, BEQ 명령어를 만나면 일단 fetch를 막습니다. 그 후, 해당 명령어가 execution 단계로 진입하면 fetch을 true로 만들어줘서 문제를 해결했습니다. 
그렇게 한다면 이미 execution단계로 진입했을때 pc값이 조건에 따라 업데이트 된 상태이기 때문에 문제를 쉽게 해결 할 수 있습니다.

<br>

## Branch Prediction

Branch Prediction이란 말 그래도 예측을 하는 것입니다. Execution 단계에서의 실제 계산을 통해 얻은 값에 따라 pc값을 업데이트 하는 것이 아닌, 일단은 예측하여 다음 pc값을 결정하는 방법을 말합니다. 
Execution 단계에서 계산한 결과가 예상한 결과와 일치하면 무의미한 명령어를 처리하지 않아도 되기 때문에 그만큼 cycle 이득을 볼 수 있지만 반대로 계산한 결과가 예상한 결과와 일치하지 않는다면 전에 실행된 명령어들을 없애고(초기화) 다시 올바른 pc값으로 업데이트해 무의미한 명령어가 들어왔다 없애는 번거로운 작업을 해야됩니다. 
이러한 과정이 성능에 무조건적으로 안좋다고 생각할 수도 있지만, 예측 값을 후에 같은 명령어가 반복되었을때, 더 효과적으로 예측하기 위한 데이터로 사용해서 예측 확률을 높이는데 사용할 수도 있기 때문에 성능 향상에 도움이 됩니다. 
즉 저희는 예측을 할때 3가지를 생각해야 합니다.
  - 가져온 명령어가 분기 명령어인지 아닌지의 여부 확인하기
  - 실제로 분기를 실행하는지 안하는지 확인하기
  - Target Address 값이 어떤 것인지 확인하기

이러한 Branch Prediction은 크게 Static Branch Prediction과 Dynamic Branch Prediction으로 나뉩니다.

<br>

## Static Branch Prediction

<br>

<p align="center">
<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/665da856-4eea-4364-bb54-892e7e8c4976" width = "500" height = "500"/>
</p>

<br>

정적 분기 예측 기법의 경우 기본 하드웨어는 분기가 항상 수행되지 않거나 항상 수행되지 않는다고 가정합니다. 즉 이 기법 같은 경우, 분기 예측이 컴파일할때 이미 결정이 되있습니다. 
항상 분기가 일어난다고 예측하는  Always Taken, 항상 분기가 일어나지 않는다고 예측하는 Always Not Taken, 분기가 일어날때 현재 pc보다 앞으로 이동할때는 분기가 일어나지 않고, 뒤로 이동할때는 분기가 일어난다고 예측하는 방법인 BTFN(Backward Taken, Forward Not Taken) 등이 있습니다. 
위의 사진은 Always Not Taken일 경우 어떤식으로 진행되는지에 대해 간단하게 설명한 그림입니다.

<br>

## Static Branch Prediction :: AlwaysTaken 구현 방법

<br>

<img src="https://github.com/YGwan/computer-architecture/assets/50222603/55e92159-fbc0-4a05-a910-582499506f3b"/>

<br>
 제가 구현한 첫번째 정적 분기 예측 방법은 alwaysTaken 방법 입니다. alwaysTaken이란 일단 분기 예측이 항상 참인 경우로 가정하고 예측을 하는 방법입니다. 따라서 taken()함수는 항상 참입니다.  
 분기 예측의 경우 Decode에서 해당 target Address와 명령어 종류를 알 수 있기 때문에 이후에 해당 target Address로 바로 이동합니다. 이때 중요한 점은 예측이 성공했을 경우에는 아무런 문제가 되지 않지만 예측이 실패했을 경우에 문제가 발생한다는 점입니다. 
 예측이 실패했을경우 해당 Fetch단계에서 들어온 명령어를 없애줘야하며, pc값을 BNE, BEQ 명령어의 pc를 기준으로 +8만큼 해줘야 된다는 것입니다.(BNE, BEQ 명령어 다음 명령어는 NOP이므로 굳이 해줄 필요가 없다.) 
 해당 Fetch단계에서 들어온 명령어를 없애주는 방법은 여러 방법이 있지만 저같은 경우 valid 값을 사용해 처리했습니다. 각 래치에 있는 valid값은 래치하고 래치 사이에 전달되는 값입니다. 초기에 설정이 된 값이 전달되면서 이 값이 true면 해당 단계의 작업을 수행하고 false면 해당 단계의 작업을 하지 않습니다. 
 따라서 if_id에 있는 valid값은 현재 fetch 작업이 이루어졌으므로 true값이 할당되어 있는 상태일테지만, 이 값을 false로 바꿔준다면 이 값이 사이클이 증가하면서 해당 명령어의 id_exe, exe_mem, mem_wb에 있는 valid값을 false로 만들어주어 해당 명령어를 추가적으로 실행하지 않게 됩니다. 
 따라서 이러한 문제를 효과적으로 해결할 수 있습니다.

<br>

<br>

## Static Branch Prediction :: AlwaysNotTaken 구현 방법

<br>

<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/fd6d31b0-b5cc-4d7e-8e26-97bc9e8a3db1"/>

<br>

제가 구현한 두번째 정적 분기 예측 방법은 alwaysNotTaken입니다. alwaysNotTaken이란 일단 분기 예측이 항상 거짓 경우로 가정하고 예측을 하는 방법입니다. 따라서 taken()함수는 항상 거짓입니다. 
이 부분의 전체적인 흐름은 AlwaysTaken과 동일하기 때문에 중복된 부분은 생략하고 예측에 실패했을 경우에 어떻게 처리해야되는지만 설명하도록 하겠습니다. 
일단 예측의 실패 여부는 해당 명령어(BEQ, BNE)가 Execution 단계에 진입했을때 알 수 있습니다. AlwaysTaken과 마찬가지로 이 경우 Fetch된 명령어를 추가적인 단계를 거치게 하지 않게 하기 위해서 valid비트를 false로 만들어줘야합니다. 
여기까진 똑같습니다. 하지만 예측이 잘못됐을경우 pc는 target Address로 가야합니다. 따라서 Decode 단계에서 해당 Target Address(next PC) 값을 id_exe 래치에 전달해 예측이 잘못되었을 경우 id_exe에 저장된 nextPc값으로 pc값을 다시 할당하여 문제를 해결합니다.

<br>

## Dynamic Branch Prediction

분기 예측이 컴파일할때 이미 결정되는 것이 아닌 실행중에 이전 예측 결과값에 따라 결정되는 방식을 동적 분기 예측 기법이라고 합니다. 이 기술은 정적 분기 예측 기법보다 높은 정확도를 가지고 있습니다. 
이 기술은 크게 Last time prediction, Two - bit counter - based prediction, Global Two - level prediction, Local Two - level Prediction, Hybrid Branch Prediction 등이 있습니다.

<br>

## Last time prediction(One-bit Prediction)

<br>

<p align="center">
<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/1f21abc8-e905-42a2-8bc6-303dd12ad49b" width = "500" height = "500"/>
</p>

<br>

Last time prediction이란 1 bit의 데이터 값을 가지고 분기 여부를 예측하는 방법입니다. 즉, 1bit 만을 가지고 예측하기 때문에 그 명령어가 바로 직전에 실행되었을때의 분기 여부를 가지고 결과 값을 예측합니다. 
하지만 이 경우 너무 자주 바뀐다는 단점이 있습니다.

<br>

## Two - bit counter

<br>

<p align="center">
<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/51704adc-9b2c-4184-93ee-8a999e951ab8"  width = "500" height = "500"/>
</p>

<br>

Two - bit counter - based prediction이란 2 bit의 데이터 값을 가지고 분기 여부를 예측하는 방법입니다. 즉 branch prediction이 두번 연속으로 틀렸을 경우에만 state가 변경되기 때문에 Last time prediction보다 상태값이 더 적게 바뀌기 때문에 더 신중하다는 장점이 있습니다.

<br>

이밖에도  Global Two - level prediction, Local Two - Level Prediction 등이 있습니다.

<br>

## Branch Prediction :: Last time prediction(One-bit Prediction) 구현 방법

<br>

![image](https://github.com/YGwan/computer-architecture/assets/50222603/bdb1c4d4-8fe2-4b7e-90e2-e8a63ce3e7a4)

<br>

제가 첫번째로 구현한 동기 분기 예측 방법은 One - Bit - Prediction입니다. one - bit - prediction이란 분기 예측을 할때 원비트의 값을 가지고 예측을 하는 것입니다. 따라서 전 분기 예측 결과값이 다음 분기 예측 결과값에 영향을 미칩니다. 
예를 들어 기본값이 true 즉, 분기한다고 예측하는 값일때, 다음번분기 상황이 오면 전 값이 참이였기 때문에 이번 분기 값도 참으로 예측합니다. 반대로, 저번 분기 값이 false면 checkBit의 값이 false로 바뀌면서 다음번 분기 예측할때 false로 예측합니다. 
이렇게 전 분기 결과가 다음 분기 결과에 영향을 미치는 것을 one - bit - prediction이라고 합니다. 정확히 말하자면 “전 분기 결과값만 다음 분기 결과값에 영향을 미친다” 라는 표현이 더 맞는 표현이라고 생각합니다. 
물론 이것 또한 만약 예측값이 실제랑 다르다면 flush, 맞는 pc로 업데이트 등의 과정이 필요합니다. 이때, 정적 분기 예측때와 동일하게 하되, checkBit값을 바꿔주므로써 Always / AlwaysNot 방법을 해당 값에 따라 자유자재로 사용하게끔 하면 전 결과값에 영향을 받는 분기 예측 방법이 만들어집니다.


<br>

## Branch Prediction :: two - Bit - Prediction

<br>

![image](https://github.com/YGwan/computer-architecture/assets/50222603/c47791d6-18f4-458b-9f6e-4837929244e7)

<br>

제가 구현한 두번째 동적 분기 예측 방법은 two - bit - prediction입니다. Two - bit - prediction이란 분기 예측을 할때 투 비트의 값만 가지고 예측을 하는 것입니다. 따라서 단순히 true, false가 아니라 4단계로 나눠집니다. 
강한 긍정(2), 약한 긍정(1), 약한 부정(0), 강한 부정(-1) 이런 식으로 나눠집니다. 예를 들어, 저같은 경우 초기값을 1로 두었는데, 이럴 경우 다음 예측을 참으로 합니다. 그 후, 다음 결과값이 true면 chance값이 2인 강한 긍정 상태로 돌입합니다. 
이 경우 다음 예측이 참이면 그 상태를 그대로 유지하돼, 만약 false면 one - bit- prediction처럼 바로 false로 넘어가는 것이 아닌 약한 긍정 상태인 1값으로 상태가 변합니다. 즉, “두번이나 참이였으면 다음번이 false라도 한번 봐준다” 라는 의미입니다. 
따라서 바로 전 단계의 결과값 뿐만 아니라 전전 단계의 결과값까지 분기 예측에 영향을 줍니다. 그래서 one - bit 보다 더 신중한 방법이라고 할 수 있습니다. 그 결과 분기 예측 변화 횟수가 one - bit - prediction보다 더 적다는 장점이 있습니다. 
반대 상황 또한 마찬가지로 진행됩니다. 이때, 정적 분기 예측때와 동일하게 하되, chance값에 따라 checkBit 값을 바꿔주므로써 Always / AlwaysNot 방법을 해당 값에 따라 자유자재로 사용하게끔 하면 전 결과값에 영향을 받는 분기 예측 방법이 만들어집니다.

<br>

## Logging 기법 적용

<br>

<img src = "https://github.com/YGwan/computer-architecture/assets/50222603/a073198d-bed0-4889-b741-446996035068" width = "800" height = "500"/>

<br>


<br>

저는 모든 print문을 자바에서 사용하는 System.out.print 형식을 사용한 것이 아니라 Logger에서 정의된 출력형식으로 사용했습니다. 이 출력 형식은 LOGGING_SIGNAL이라는 boolean타입이 true때 출력을 하고 false면 출력을 하지 않습니다. 
또한 min하고 max값을 통해 cycle 수를 기준으로 값을 조정할수도 있습니다. 즉, 특정 cycle 범위만 출력하기도 편합니다. 이것이 중요한 이유는 다른 파일 같은 경우에는 cycle수가 많지 않기 때문에 문제가 되지 않지만 input4.bin 파일 같은 경우,  
로그를 출력하면 너무 많기 때문에 시간이 오래걸립니다. 하지만 로그를 확인해야 디버깅이 가능하기때문에 로그를 출력하지 않을 수도 없습니다. 따라서 제가 사용한 방법은 로그를 확인하는 부분을 Logger class에 있는 출력함수로 사용해 SIGNAL을 통해 제어한다면, 
제가 원하는대로, 원하는 만큼, 원하는 부분만 출력이 가능하기 때문에 이러한 형식을 사용했습니다. 실제로 위에서 말한 바와 같이 이 방법을 사용해 로그 출력의 많은 도움을 받았습니다.

<br>

---

# 최종 정리 : 구조도

<br>

![image](https://github.com/YGwan/computer-architecture/assets/50222603/69f1a20f-019d-4a96-97b7-d42e13df641a)

<br>

따라서 최종적으로 정리하자면 저의 파이프라인 구조는 위의 사진을 기반으로 작성하였고 이 부분에서 ID/EXE 래치 직전에 Data Forwarding MUX를 추가한 것이라고 생각하면 됩니다. 이것 외에도 valid bit추가, next pc 변수 추가, 
각각의 명령어들의 필요한 변수들 추가 등의 추가적인 작업이 많지만 이 부분까지 다 추가한 Data Path를 그린다면 너무 복잡해질 것 같아 전체적인 Data Path만 간략하게 추가했습니다.

<br>

# 최종 정리 : 결과 비교

Data Dependence 방법은 다 Data Forwarding방법을 사용했으므로, Control Dependence 처리 방식을 기준으로 나눠보도록 하겠습니다. 기준은 input 파일로 비교적 규모가 있는 simple4, gcd, fib, input4를 기준으로 비교분석 하도록 하겠습니다.

<br>

## 사이클 수
<img src="https://github.com/YGwan/computer-architecture/assets/50222603/05a11d3f-b946-4f5e-b204-58ad68e77ff5" width = "1000" height = "300"/>

<br>

<br>

<br>

## 정확도 (예측 성공률)
<img src="https://github.com/YGwan/computer-architecture/assets/50222603/9eaa66fa-74c8-4c23-8180-f1b91e50bf46" width = "1000" height = "300"/>

<br>

<br>

<br>

<br>










