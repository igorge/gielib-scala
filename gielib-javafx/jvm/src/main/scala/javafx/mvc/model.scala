package gie.javafx

import gie.validator.{failure, ValidationResult}

import scalafx.beans.property.ReadOnlyObjectWrapper


trait ModelValue[T]  {
    type ValidatedT = ValidationResult[T, Any]
    protected val m_model = new ReadOnlyObjectWrapper[ValidatedT](this, "model", failure(new NullPointerException("non initialized")) )

    def model = m_model.readOnlyProperty
    def model_=(v:T): Unit
}



