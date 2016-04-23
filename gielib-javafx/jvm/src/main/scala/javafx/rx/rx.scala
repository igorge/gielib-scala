package gie.javafx.rx

import javafx.beans.property.{ReadOnlyProperty, SimpleObjectProperty}
import javafx.beans.value.{ChangeListener, ObservableValue}

import gie.validator.ValidationResult


object `package` {

    def zipMap[T1, T2, R](p1: ReadOnlyProperty[T1], p2: ReadOnlyProperty[T2])(f: (T1,T2)=>R): ReadOnlyProperty[R] = {
        val newProp = new SimpleObjectProperty[R](f(p1.getValue, p2.getValue))

        val listener1 = new ChangeListener[T1] {
            def changed(observable: ObservableValue[_ <: T1], oldValue: T1, newValue: T1) {
                newProp.set(f(p1.getValue, p2.getValue))
            }
        }

        val listener2 = new ChangeListener[T2] {
            def changed(observable: ObservableValue[_ <: T2], oldValue: T2, newValue: T2) {
                newProp.set(f(p1.getValue, p2.getValue))
            }
        }


        p1.addListener(listener1)
        p2.addListener(listener2)

        newProp

    }

    def zipMap[T1, T2, T3, R](p1: ReadOnlyProperty[T1], p2: ReadOnlyProperty[T2], p3: ReadOnlyProperty[T3])(f: (T1,T2,T3)=>R): ReadOnlyProperty[R] = {

        def recalculate() = f(p1.getValue, p2.getValue, p3.getValue)

        val newProp = new SimpleObjectProperty[R](recalculate())

        val listener1 = new ChangeListener[T1] {
            def changed(observable: ObservableValue[_ <: T1], oldValue: T1, newValue: T1) {
                newProp.set(recalculate())
            }
        }

        val listener2 = new ChangeListener[T2] {
            def changed(observable: ObservableValue[_ <: T2], oldValue: T2, newValue: T2) {
                newProp.set(recalculate())
            }
        }

        val listener3 = new ChangeListener[T3] {
            def changed(observable: ObservableValue[_ <: T3], oldValue: T3, newValue: T3) {
                newProp.set(recalculate())
            }
        }

        p1.addListener(listener1)
        p2.addListener(listener2)
        p3.addListener(listener3)

        newProp

    }

    def zipMap[T1, T2, T3, T4, R](p1: ReadOnlyProperty[T1], p2: ReadOnlyProperty[T2], p3: ReadOnlyProperty[T3], p4: ReadOnlyProperty[T4])(f: (T1,T2,T3,T4)=>R): ReadOnlyProperty[R] = {

        def recalculate() = f(p1.getValue, p2.getValue, p3.getValue, p4.getValue)

        val newProp = new SimpleObjectProperty[R](recalculate())

        val listener1 = new ChangeListener[T1] {
            def changed(observable: ObservableValue[_ <: T1], oldValue: T1, newValue: T1) {
                newProp.set(recalculate())
            }
        }

        val listener2 = new ChangeListener[T2] {
            def changed(observable: ObservableValue[_ <: T2], oldValue: T2, newValue: T2) {
                newProp.set(recalculate())
            }
        }

        val listener3 = new ChangeListener[T3] {
            def changed(observable: ObservableValue[_ <: T3], oldValue: T3, newValue: T3) {
                newProp.set(recalculate())
            }
        }

        val listener4 = new ChangeListener[T4] {
            def changed(observable: ObservableValue[_ <: T4], oldValue: T4, newValue: T4) {
                newProp.set(recalculate())
            }
        }

        p1.addListener(listener1)
        p2.addListener(listener2)
        p3.addListener(listener3)
        p4.addListener(listener4)

        newProp

    }

    def zipMapNel[E, T1, T2, E1 <: E, E2 <: E, R](p1: ReadOnlyProperty[ValidationResult[T1, E1]],
                                                  p2: ReadOnlyProperty[ValidationResult[T2, E2]])
                                                 (f: (T1,T2)=>R): ReadOnlyProperty[ValidationResult[R,E]] = {

        import gie.validator.Validation

        zipMap(p1,p2){ (pp1, pp2) =>
            Validation.zipMap(pp1, pp2){f(_,_)}
        }
    }

    def zipMapNel[E, T1, T2, T3, E1 <: E, E2 <: E, E3 <: E, R](p1: ReadOnlyProperty[ValidationResult[T1, E1]],
                                                               p2: ReadOnlyProperty[ValidationResult[T2, E2]],
                                                               p3: ReadOnlyProperty[ValidationResult[T3, E3]])
                                                              (f: (T1,T2,T3)=>R): ReadOnlyProperty[ValidationResult[R,E]] = {

        import gie.validator.Validation

        zipMap(p1,p2,p3){ (pp1, pp2, pp3) =>
            Validation.zipMap(pp1, pp2, pp3){f(_,_,_)}
        }
    }

    def zipMapNel[E, T1, T2, T3, T4, E1 <: E, E2 <: E, E3 <: E, E4 <: E, R](p1: ReadOnlyProperty[ValidationResult[T1, E1]],
                                                                            p2: ReadOnlyProperty[ValidationResult[T2, E2]],
                                                                            p3: ReadOnlyProperty[ValidationResult[T3, E3]],
                                                                            p4: ReadOnlyProperty[ValidationResult[T4, E4]])
                                                                           (f: (T1,T2,T3,T4)=>R): ReadOnlyProperty[ValidationResult[R,E]] = {

        import gie.validator.Validation

        zipMap(p1,p2,p3,p4){ (pp1, pp2, pp3, pp4) =>
            Validation.zipMap(pp1, pp2, pp3, pp4){f(_,_,_,_)}
        }
    }


    implicit class PropertyOps[T](val property: ReadOnlyProperty[T]) extends AnyVal {

        def tee(f: T=>Any): property.type ={

            this.foreach(f)

            property
        }

        def foreach(f: T=>Any): Unit = {

            val listener = new ChangeListener[T] {
                def changed(observable: ObservableValue[_ <: T], oldValue: T, newValue: T): Unit = {
                    f(property.getValue)
                }
            }

            f(property.getValue) //side effect with current

            property.addListener(listener)

        }

        def map[U](f: T=>U): ReadOnlyProperty[U] = {
            val newProp = new SimpleObjectProperty[U](f(property.getValue))

            val listener = new ChangeListener[T] {
                def changed(observable: ObservableValue[_ <: T], oldValue: T, newValue: T) {
                    newProp.set(f(property.getValue))
                }
            }

            property.addListener(listener)

            newProp
        }

    }

    implicit class PropertySFXOps[U,V](val property:scalafx.beans.property.ReadOnlyProperty[U,V]) extends AnyVal {
        def map[T](f: V=>T): ReadOnlyProperty[T] = property.delegate.map(f)
        def foreach(f: V=>Any): Unit = property.delegate.foreach(f)
    }

}



