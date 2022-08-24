/*
*
*   @author: Montex BJ Eliseo
*/

//Aqui se guarda el archivo de image para el nuevo personaje
let CHARACTER_IMAGE_FILE = [];
//Aqui se guarda el archivo de image para la nueva pelicula
let MOVIE_IMAGE_FILE = [];
//Aqui se guarda el archivo de image para el nuevo genero
let GENRE_IMAGE_FILE = [];

//Asocia las funciones con los eventos, cuando se abre una imagen
//Se encarga de codificarla en base64
function load() {
    try {
        let charFile = document.getElementById("character_image");
        charFile.addEventListener("change", (e) => {
            let reader = new FileReader();
            reader.readAsDataURL(e.target.files[0]);
            reader.onloadend = function (evt) {
                if (evt.target.readyState == FileReader.DONE) {
                    CHARACTER_IMAGE_FILE = evt.target.result;
                }
            };
        });
        let movieFile = document.getElementById("movie_image");
        movieFile.addEventListener("change", (e) => {
            let reader = new FileReader();
            reader.readAsDataURL(e.target.files[0]);
            reader.onloadend = function (evt) {
                if (evt.target.readyState == FileReader.DONE) {
                    MOVIE_IMAGE_FILE = evt.target.result;
                }
            };
        });
        let genreFile = document.getElementById("genre_image");
        genreFile.addEventListener("change", (e) => {
            let reader = new FileReader();
            reader.readAsDataURL(e.target.files[0]);
            reader.onloadend = function (evt) {
                if (evt.target.readyState == FileReader.DONE) {
                    GENRE_IMAGE_FILE = evt.target.result;
                }
            };
        });
    } catch (e) {
        alert(e);
    }
}

//Enviar Solicitud de Eliminacion de un Personaje
function delCharacter(id) {
    sendRequest("DELETE", "/characters/" + id + "/delete", "");
}
//Click Solicitud de Creacion de un Personaje
function createNewCharacterClick() {
    try {
        let name = document.getElementById("character_name").value;
        let age = document.getElementById("character_age").value;
        let weight = document.getElementById("character_weight").value;
        let story = document.getElementById("character_story").value;
        let movies = null;
        sendRequest("POST", "/characters/create", JSON.stringify({ "name": name, "image": CHARACTER_IMAGE_FILE, "age": age, "weight": weight, "story": story, "movies": movies }));
    } catch (e) {
        console.log("create new character error: " + e);
    }
}

function createNewMovieClick() {
    try {
        let title = document.getElementById("movie_title").value;
        let date = document.getElementById("movie_date").value;
        let rate = document.getElementById("movie_rate").value;
        let chars = [];
        let els = document.getElementsByName("movie_character");
        console.log(els);
        for (i = 0; i < els.length; i++) {
            if (els[i].checked) {
                chars.push({ 'id': els[i].value });
            }
        }
        sendRequest("POST", "/movies/create", JSON.stringify({ "title": title, "image": MOVIE_IMAGE_FILE, "date": date, "rate": rate, "characters": chars, "genre": null }));
    } catch (e) {
        console.log(e);
    }
}

//Send Any Request
function sendRequest(method, url, data) {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE) {
            alert(this.status);
        }
    };
    xhr.send(data);
}

//Establecer valores predeterminados
load();