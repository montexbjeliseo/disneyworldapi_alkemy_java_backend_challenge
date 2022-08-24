function openNewCharactherForm(){
    let form = document.getElementById("newCharacterForm");
    form.classList.remove("hide");  
}

/*function openNewCharactherForm() {

    //Crea un solicitud
    let mcmr = new XMLHttpRequest();
    //Solicita un modelo
    ncmr.open("get", "/characters/new");
    //Cuando obtiene una respuesta
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            mostrarFormulario();
        } else if (this.readyState === XMLHttpRequest.DONE) {
            alert("Error: " + this.status);
        }
    };
}*/