package com.mito.components

import android.icu.util.Calendar
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType


/*
ESTO ES UN EJEMPLO DE COMO PODRÍAS LLAMAR Y CREAR UN TEXTFIELD, USANDO EL PATRON BUILDER.

Primero configuramos el modelo con el tipo que necesite y acabamos con un .Build()
ese .Build() nos va devolver un composable.
Como Build es una funcion del modelo principal, todas las clases dentro del sealed class
por herencia heredan esa funcion. La funcion llama al composable que se encarga de redirigir
al tipo que queremos.



@Composable
fun example(){
    val textFieldModel = MitoTextField.MitoTextFieldBasic(
        title = "Titulo del textField",
        textError = "Texto de error",
        placeholder = "Texto de placeHolder",
        leadingIcon = @Composable { IconButton(onClick = { /*TODO*/ }) { }},
        trailingIcon = @Composable { IconButton(onClick = { /*TODO*/ }) { }},
        onValueChange = { /*TODO*/ },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        enabled = true,
    ).Build()
}
*/





sealed class MitoTextField(
    open val title: String,
    open val textError: String?,
    open val placeholder: String?,
    open val leadingIcon: @Composable (() -> Unit)?,
    open val trailingIcon: @Composable (() -> Unit)?,
    open val onValueChange: (String) -> Unit,
    open val keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    open val enabled: Boolean = true,
    ){

    //He creado 4 tipos de MitoTextFieldModel, si necesitas más que tengan configuraciones preestablecidas
    //puedes ir agregandolos. Pero si vas a agregar uno que sea para el nombre no tiene mucho sentido,
    //ya que solo lo usarias una vez. Incluso el password puede que no sea necesario, si no lo vamos
    //a necesitar mas en la app.
    //Los cuatro tipos son: MitoTextFieldBasic-> el basico para cualquiera, MitoTextFieldPassword,
    //MitoTextFieldDropDown, MitoTextFieldDataPicker.

    data class MitoTextFieldBasic(
        override val title: String,
        override val textError: String? = null,
        override val placeholder: String? = null,
        override val leadingIcon: @Composable (() -> Unit)? = null,
        override val trailingIcon: @Composable (() -> Unit)? = null,
        override val onValueChange: (String) -> Unit,
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        override val enabled: Boolean = true,
    ) : MitoTextField(
                title = title,
                textError = textError,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                enabled = enabled)

    data class MitoTextFieldPassword(
        override val onValueChange: (String) -> Unit,
        override val enabled: Boolean = true
    ) : MitoTextField(
                title = ("Aqui podrías poner el recurso para el titulo de un password"),
                textError = ("Aqui podrías poner el recurso para el error de un password"),
                placeholder = ("Aqui podrías poner el recurso para el placeholder de un password"),
                trailingIcon = @Composable { IconButton(onClick = { /*TODO*/ }) { }},
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = enabled,
                leadingIcon = null,
            )

    data class MitoTextFieldDropDown(
        override val onValueChange: (String) -> Unit,
        override val enabled: Boolean = true,
        val options: List<String>,
    ) : MitoTextField(
                title = ("Aqui podrías poner el recurso para el titulo de un dropdown"),
                textError = ("Aqui podrías poner el recurso para el error de un dropdown"),
                placeholder = ("Aqui podrías poner el recurso para el placeholder de un dropdown"),
                onValueChange = onValueChange,
                enabled = enabled,
                leadingIcon = null,
                trailingIcon = @Composable { IconButton(onClick = { /*TODO*/ }) { }},
        )

    data class MitoTextFieldDataPicker(
        val startDate: Calendar,
        val endDate: Calendar,
        override val onValueChange: (String) -> Unit,
        override val enabled: Boolean = true,
    ) : MitoTextField(
                title = ("Aqui podrías poner el recurso para el titulo de un datepicker"),
                placeholder = ("Aqui podrías poner el recurso para el placeholder de un datepicker"),
                textError = null,
                onValueChange = onValueChange,
                enabled = enabled,
                leadingIcon = @Composable { IconButton(onClick = { /*TODO*/ }) { }},
                trailingIcon = null
        )

    @Composable
    fun Build() = MitoTextField(this)

}



@Composable
private fun MitoTextField(textFieldModel: MitoTextField){

    when(textFieldModel){
        is MitoTextField.MitoTextFieldBasic -> MitoTextFieldCustom(textFieldModel)
        is MitoTextField.MitoTextFieldDataPicker -> TODO()
        is MitoTextField.MitoTextFieldDropDown -> MitoTextFieldDropDown(textFieldModel)
        is MitoTextField.MitoTextFieldPassword -> TODO()
    }
}

@Composable
private fun MitoTextFieldCustom(textFieldModel: MitoTextField) {
    //Aqui tienes que crear el composable de tu textField personalizado.
    //De forma muy generica, con las caracteristicas que compartan todos los tipos de textField.
}


@Composable
private fun MitoTextFieldDropDown(textFieldModel: MitoTextField.MitoTextFieldDropDown) {
    //Creamos un Dropdown y al textfield del DropDown le ponemos las caracteristicas del MitoTextFieldCustom

    //NO SE EXACTAMENTE COMO SE MONTA EL DROPDOWN, PERO SI TIENES QUE USAR UN TextField, que sea
    //el MitoTextFieldCustom el que te lo monte, para que repita las caracteristicas.
    MitoTextFieldCustom(textFieldModel)
}

