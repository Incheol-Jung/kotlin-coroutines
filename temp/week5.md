# 📌 인상 깊었던 내용

## **📚 @JvmField**

> MainCoroutineExtension 사용법은 MainCoroutineRule 룰과 거의 동일하다. @get:Rule 어노테이션 대신 @JvmField와 @RegisterExtension을 사용해야 하는 것이다
 
📕 220p 6번째 (15장)
> 

### **🧐 : jvmField는 kotlin에서는 일반적으로 자바로 디컴파일하면 getter/setter 메서드를 만들어준다. 그런데 @JvmField를 사용하면 getter/setter 메서드를 생성해주지 않고 필드를 직접 접근할수 있도록 도와준다. @get:Rule은 Junit에서 @Before대신 사용할 수 있도록 초기화를 대신해주어 재사용할 때 유용하다. @**RegisterExtension은 Junit5의 테스트 확장은 테스트 실행 전후에 추가적인 기능을 제공한다

# 📌 이해가 가지 않았던 내용

## **📚 StandardTestDispatcher vs UnconfinedTestDispatcher**

> StandardTestDispatcher는 스케줄러를 사용하기 전까지 어떤 연산도 수행하지 않는다는 것이 가장 큰 차이점이다. UnconfinedTestDispatcher는 코루틴을 시작했을 때 첫 번째 지연이 일어나기 전까지 모든 연산을 즉시 수행하기 때문에 다음 코드에서 ‘C’가 출력되는 걸 확인할 수 있다
 
📕 205p 9번째 (15장)
> 

### **🧐 : 내가 이해한건 StandardTestDispatcher는 실행순서를 사용자가 제어한다. 만약 새로운 코루틴을 바로 실행하려면** UnconfinedTestDispatcher를 사용하면 된다. 그런데 왜 B는 출력이 되지 않는걸까??

```jsx
fun main() {
    val scheduler = TestCoroutineScheduler()
    val testDispatcher = StandardTestDispatcher(scheduler)

    CoroutineScope(testDispatcher).launch {
        print("A")
        delay(1)
        print("B")
    }
    scheduler.advanceUntilIdle()

    CoroutineScope(UnconfinedTestDispatcher()).launch {
        print("C")
        delay(1)
        print("D")
    }
}
```

# 📌 논의해보고 싶었던 내용

## **📚 Dispatcher 갯수 뿌시기**

> Dispatcher.Default : CPU의 Core 갯수 만큼 사이즈를 지정한다(단, 원래는 하나의 Core에서 하나의 Thread를 처리했지만 최근엔 2개의 Thread를 처리할 수 있다고 하여 8코어 기준으로 16개의 쓰레드가 생성된다)
[Dispatcher.IO](http://Dispatcher.IO) : 최대 64개의 Tread가 생성된다. 
Dispatcher.Default + Dispatcher.IO = 16 + 64개 일까? 아니면 64개 일까? → 결론은 16+64개 이다. 
테스트해본 결과 혼합할 경우 65이상으로 쓰레드가 생성되는것을 확인할 수 있다
 
📕 -p -번째 (-장)
> 

### **🧐 : 완료!!**

```jsx
suspend fun main(): Unit =
    coroutineScope {
        repeat(4000) {
//            CoroutineScope(Dispatchers.Default).launch {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2000)
                    println(Thread.currentThread().name)
                }
//            }
        }
        delay(3000)
    }
```
