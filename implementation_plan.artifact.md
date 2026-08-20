# Plan de Implementación: Modelos de Datos y Guardado de Imágenes

Este plan detalla la creación de modelos de datos simples para la gestión local de información y la implementación de la funcionalidad para guardar fotos capturadas en la galería del dispositivo.

## Proposed Changes

### 1. Modelos de Datos (Package: `com.example.marco_todo.model`)

Crearemos modelos simples para estructurar la información en las pantallas correspondientes.

#### [NEW] [Contact.kt](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/java/com/example/marco_todo/model/Contact.kt)
- Definir la data class `Contact(val id: Int, val name: String, val email: String)`.

#### [MODIFY] [UserProfile.kt](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/java/com/example/marco_todo/model/UserProfile.kt)
- Asegurar que contenga los campos: `name`, `email`, e `interests`.

### 2. Refactorización de Pantallas para usar Modelos

#### [MODIFY] [MaterialDesingscreen.kt](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/java/com/example/marco_todo/ui/screens/MaterialDesingscreen.kt)
- Utilizar el modelo `UserProfile` para manejar el estado de los datos del perfil.

#### [MODIFY] [CollectionListscreen.kt](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/java/com/example/marco_todo/ui/screens/CollectionListscreen.kt)
- Cambiar las listas de `String` o `Triple` por listas de objetos `Contact`.

### 3. Guardado de Imágenes en Dispositivo

#### [MODIFY] [MultimediaImagesscreen.kt](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/java/com/example/marco_todo/ui/screens/MultimediaImagesscreen.kt)
- Implementar una función de utilidad `saveBitmapToGallery` que utilice `MediaStore` para guardar el Bitmap capturado por la cámara.
- Agregar un botón "Guardar en Galería" en la sección de Picker cuando haya una imagen capturada.

## Verification Plan

### Manual Verification
- **Modelos:** Verificar que al guardar el perfil en la pantalla Material, los datos se estructuren correctamente en el objeto `UserProfile`.
- **Listas:** Comprobar que la lista de contactos se cargue correctamente usando el nuevo modelo `Contact`.
- **Cámara:**
    1. Abrir la sección Multimedia -> Picker.
    2. Capturar una foto con la cámara.
    3. Presionar el nuevo botón "Guardar en Galería".
    4. Verificar en la aplicación de Galería/Fotos del dispositivo que la imagen aparezca allí.
