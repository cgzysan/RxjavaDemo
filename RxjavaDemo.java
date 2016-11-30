package demo.ysan.okhttpdemo.Demo;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import demo.ysan.okhttpdemo.Demo.Bean.Father;
import demo.ysan.okhttpdemo.Demo.Bean.School;
import demo.ysan.okhttpdemo.Demo.Bean.Son;
import demo.ysan.okhttpdemo.Demo.Bean.Student;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * 创建者     YSAN
 * 创建时间   2016/11/4 15:11
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class RxjavaDemo {
    private static final String TAG = "RxjavaDemo";

    public static void main(String... args) {
        Observable.just(1,2,3)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "i = " + integer;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }

    private static void demo35() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("Subscriber CurrentThread = " + Thread.currentThread().getName());
                subscriber.onNext(1);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Observer CurrentThread = " + Thread.currentThread().getName());
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo34() {
        Observable.just(1, 2, 3)
                .startWith(7, 8, 9)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo33() {
        final String[] s = new String[]{"A", "B", "C", "D", "E", "F"};
        Observable<String> observable1 = Observable.interval(1500, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return s[aLong.intValue()];
                    }
                }).take(6);
        Observable<Long> observable2 = Observable.interval(1000, TimeUnit.MILLISECONDS).take(5);

        Observable.combineLatest(observable1, observable2, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("s = " + s);
                    }
                });
    }

    private static void demo32() {
        final String[] s = new String[]{"A", "B", "C", "D", "E", "F"};
        Observable<String> observable1 = Observable.interval(1500, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return s[aLong.intValue()];
                    }
                }).take(5);
        Observable<Long> observable2 = Observable.interval(1500, TimeUnit.MILLISECONDS);

        observable1.join(observable2, new Func1<String, Observable<Long>>() {
            @Override
            public Observable<Long> call(String s) {
                return Observable.timer(1, TimeUnit.SECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                return Observable.timer(1, TimeUnit.SECONDS);
            }
        }, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("result = " + s);
                    }
                });
    }

    private static void demo31() {
        final String[] str = {"A", "B", "C", "D", "E"};
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return str[aLong.intValue()];
                    }
                })
                .take(5)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("s = " + s);
                    }
                });
    }

    private static void demo30() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(11, 22, 33);

        Observable.zip(observable1, observable2, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer * integer2;
            }
        })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo29() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(11, 22, 33);

        Observable.merge(observable1, observable2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo28() {
        Observable.just(new Son())
                .cast(Father.class)
                .subscribe(new Action1<Father>() {
                    @Override
                    public void call(Father f) {
                        f.eat();
                    }
                });
    }

    private static void demo27() {
        Observable.just(1, 2, 3, 4, 5)
                .window(3)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("window onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onCompleted() {
                                System.out.println("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                System.out.println("i = " + integer);
                            }
                        });
                    }
                });
    }

    private static void demo26() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(1000, TimeUnit.SECONDS, 2)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        for (Integer i : integers) {
                            System.out.println("i = " + i);
                        }
                        System.out.println("-------------------");
                    }
                });
    }

    private static void demo25() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(2, 3)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        for (Integer i : integers) {
                            System.out.println("i = " + i);
                        }
                        System.out.println("-------------------");
                    }
                });
    }

    private static void demo24() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(2)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        for (Integer i : integers) {
                            System.out.println("i = " + i);
                        }
                        System.out.println("-------------------");
                    }
                });
    }

    private static void demo23() {
        Student lisi = new Student("李四", 24);
        Student wangwu = new Student("王五", 25);
        Student zhangsan = new Student("张三", 23);
        Student jianjian = new Student("jianjian", 24);
        Student wenwen = new Student("wenwen", 25);

        Observable<GroupedObservable<Integer, Student>> observable = Observable.just(lisi, wangwu, zhangsan, jianjian, wenwen)
                .groupBy(new Func1<Student, Integer>() {
                    @Override
                    public Integer call(Student student) {
                        return student.getAge();
                    }
                });
        Observable.concat(observable)
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        System.out.println("student = " + student.getName() + ", age = " + student.getAge());
                    }
                });
    }

    private static void demo22() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(10, new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("rxjava onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("rxjava onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("result = " + integer);
                    }
                });
    }

    private static void demo21() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("rxjava onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("rxjava onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("result = " + integer);
                    }
                });
    }

    private static void demo20() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        observable.flatMap(new Func1<Integer, Observable<?>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.just(integer + "s");
            }
        });
        observable.switchMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                if (integer == 1) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return Observable.just(integer + "d");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("String s = " + s);
            }
        });
    }

    private static void demo19() {
        Observable.just(1, 2, 3, 4)
                .flatMapIterable(new Func1<Integer, Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> call(Integer integer) {
                        List<Integer> list = new ArrayList();
                        list.add(integer);
                        list.add(3);
                        return list;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
    }

    private static void demo18() {
        School school = new School();
        school.setAddress("China");
        school.getStudents().add(new Student("张三", 23));
        school.getStudents().add(new Student("李四", 24));
        school.getStudents().add(new Student("王五", 25));

        Observable<School> observable = Observable.just(school);
        observable.subscribe(new Action1<School>() {
            @Override
            public void call(School school) {
                System.out.println("school address = " + school.getAddress());
            }
        });
        observable.concatMap(new Func1<School, Observable<Student>>() {
            @Override
            public Observable<Student> call(School school) {
                return Observable.from(school.getStudents());
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                System.out.println("student name = " + student.getName());
            }
        });
    }

    private static void demo17() {
        School school = new School();
        school.getStudents().add(new Student("张三", 23));
        school.getStudents().add(new Student("李四", 24));
        school.getStudents().add(new Student("王五", 25));
        school.setAddress("China");

        Observable<School> observable = Observable.just(school);
        observable.flatMap(new Func1<School, Observable<Student>>() {
            @Override
            public Observable<Student> call(School school) {
                return Observable.from(school.getStudents());
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                System.out.println("name = " + student.getName());
            }
        });
        observable.subscribe(new Observer<School>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(School school) {
                System.out.println("school address = " + school.getAddress());
            }
        });
    }

    private static void demo16() {
        School school = new School();
        school.getStudents().add(new Student("张三", 23));
        school.getStudents().add(new Student("李四", 24));
        school.getStudents().add(new Student("王五", 25));

        Observable.just(school)
                .map(new Func1<School, List<Student>>() {
                    @Override
                    public List<Student> call(School school) {
                        return school.getStudents();
                    }
                })
                .subscribe(new Observer<List<Student>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Student> students) {
                        for (Student student : students) {
                            System.out.println("name = " + student.getName());
                        }
                    }
                });
    }

    private static void demo15() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
            }
        }).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * 5;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("something is error");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
    }

    private static void demo14() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 50; i++) {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    subscriber.onNext(i);
                }
            }
        })
                .debounce(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo13() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 50; i++) {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    subscriber.onNext(i);
                }
            }
        })
                .timeout(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Timeout error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo12() {
    }

    private static void demo11() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 50; i++) {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    subscriber.onNext(i);
                }
            }
        });
        observable.sample(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo10() {
        Observable.just(1, 2, 3)
                .elementAtOrDefault(5, 1)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }

    private static void demo9() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("张三");
                subscriber.onNext("李四");
                subscriber.onNext("王五");
                subscriber.onCompleted();
            }
        })
                .takeLast(1)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("我是" + s);
                    }
                });
    }

    private static void demo8() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
        asyncSubject.onNext(333);
        asyncSubject.onNext(444);
        asyncSubject.onNext(555);
        asyncSubject.onCompleted();
    }

    private static void demo7() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.onNext(333);
        replaySubject.onNext(444);
        replaySubject.onNext(555);
        replaySubject.onCompleted();

        replaySubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
    }

    private static void demo6() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(1);
        behaviorSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
        behaviorSubject.onNext(999);
    }

    private static void demo5() {
        final PublishSubject<Boolean> subject = PublishSubject.create();
        subject.subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean)
                    System.out.println("Observable Completed");
            }
        });

        PublishSubject.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                subject.onNext(true);
            }
        }).subscribe();
    }

    private static void demo4() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.onNext("HelloWorld");
        publishSubject.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onNext(Object o) {
                System.out.println(o.toString());
            }
        });
        publishSubject.onNext("HelloWorld");
    }

    private static void demo2() {
        Observable.just(reInt())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });

    }

    private static void demo3() {
        Observable.create(new Observable.OnSubscribe<Integer>() {//创建

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }

        }).subscribe(new Observer<Integer>() {

            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("i = " + integer);
            }
        });
    }

    public static int reInt() {
        return 12;
    }

    private static void demo1() {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(11);
        items.add(111);
        items.add(1111);

        Observable.from(items)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("i = " + integer);
                    }
                });
    }
}
