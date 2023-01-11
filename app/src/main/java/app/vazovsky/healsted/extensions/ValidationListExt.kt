package app.vazovsky.healsted.extensions

import app.vazovsky.healsted.presentation.view.ValidationTextInputLayout

fun List<ValidationTextInputLayout>.checkInputs(): Boolean {
    var validated = true
    this.forEach {
        validated = validated.and(it.validate())
    }
    return validated
}