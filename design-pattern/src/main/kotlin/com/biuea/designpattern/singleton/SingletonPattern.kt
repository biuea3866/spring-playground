package com.biuea.designpattern.singleton

// 1. Eager Initialization
class Singleton {
    companion object {
        private val INSTANCE: Singleton = Singleton()

        fun getInstance(): Singleton {
            return INSTANCE
        }
    }
}

// 2. Lazy Initialization
class Singleton2 {
    companion object {
        private var INSTANCE: Singleton2? = null

        fun getInstance(): Singleton2 {
            if (INSTANCE == null) {
                INSTANCE = Singleton2()
            }

            return INSTANCE!!
        }
    }
}

// 3. Thread-Safe Initialization
class Singleton3 {
    companion object {
        private var INSTANCE: Singleton3? = null

        @Synchronized
        fun getInstance(): Singleton3 {
            if (INSTANCE == null) {
                INSTANCE = Singleton3()
            }

            return INSTANCE!!
        }
    }
}

// 4. Double-Checked Locking
class Singleton4 {
    companion object {
        @Volatile
        private var INSTANCE: Singleton4? = null

        fun getInstance(): Singleton4 {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Singleton4()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}

// 5. Bill Pugh Singleton
/**
 * 1. 내부 SingletonHolder 클래스를 만들었기 때문에 SingletonHolder는 Signleton5 클래스가 초기화되어도 메모리에 로드되지 않는다.
 * 2. 어떤 쓰레드가 getInstance() 메소드를 호출하더라도 SingletonHolder.INSTANCE는 한 번만 초기화되기 때문에 쓰레드 안전성을 보장한다.
 */
class Singleton5 {
    companion object {
        private object SingletonHolder {
            val INSTANCE = Singleton5()
        }

        fun getInstance(): Singleton5 {
            return SingletonHolder.INSTANCE
        }
    }
}

// 6. Enum Singleton
enum class Singleton6 {
    INSTANCE;

    private var client: Client = Client()

    constructor()

    companion object {
        fun getInstance(): Singleton6 {
            return INSTANCE
        }
    }
}

class Client